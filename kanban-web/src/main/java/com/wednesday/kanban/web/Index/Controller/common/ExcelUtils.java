package com.wednesday.kanban.web.Index.Controller.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: wylipengming
 * Date: 14-3-11
 * Time: 上午11:44
 *
 * POI----excel工具类
 *
 */
public class ExcelUtils {
    private static HSSFWorkbook wb;

    private static CellStyle titleStyle;        // 标题行样式
    private static Font titleFont;                // 标题行字体
    private static CellStyle dateStyle;            // 日期行样式
    private static Font dateFont;                // 日期行字体
    private static CellStyle headStyle;            // 表头行样式
    private static Font headFont;                // 表头行字体
    private static CellStyle contentStyle;        // 内容行样式
    private static Font contentFont;            // 内容行字体
    private static CellStyle staticStyle;            // 统计信息行样式
    private static Font staticFont;            // 统计信息字体

    /**
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * 将Map里的集合对象数据输出Excel数据流
     *
     *
     */
    public static void export2Excel(ExportSetInfo setInfo) throws
            IOException, IllegalArgumentException, IllegalAccessException {
        init();
        Set<Map.Entry<String, Collection>> set = setInfo.getObjsMap().entrySet();
        String[] sheetNames = new String[setInfo.getObjsMap().size()];
        int sheetNameNum = 0;
        for (Map.Entry<String, Collection> entry : set) {
            sheetNames[sheetNameNum] = entry.getKey();
            sheetNameNum++;
        }
        HSSFSheet[] sheets = getSheets(setInfo.getObjsMap().size(), sheetNames);
        int sheetNum = 0;
        for (Map.Entry<String, Collection> entry : set) {
            // Sheet
            Collection objs = entry.getValue();
            // 标题行
            createTableTitleRow(setInfo, sheets, sheetNum);
            // 日期行
            createTableDateRow(setInfo, sheets, sheetNum);
            // 表头
            creatTableHeadRow(setInfo, sheets, sheetNum);
            // 表体
            String[] fieldNames = setInfo.getFieldNames().get(sheetNum);
            int rowNum = 3;
            for (Object obj : objs) {
                HSSFRow contentRow = sheets[sheetNum].createRow(rowNum);
                contentRow.setHeight((short) 300);
                HSSFCell[] cells = getCells(contentRow, setInfo.getFieldNames().get(sheetNum).length);
                int cellNum = 1;                    // 去掉一列序号，因此从1开始
                if (fieldNames != null) {
                    for (int num = 0; num < fieldNames.length; num++) {
                        Object value = ReflectionUtils.invokeGetterMethod(obj, fieldNames[num]);
                        cells[cellNum].setCellValue(value == null ? "" : value.toString());
                        cellNum++;
                    }
                }
                rowNum++;
            }
            adjustColumnSize(sheets, sheetNum, fieldNames);    // 自动调整列宽
            //在末尾添加统计信息
            createTableStatic(setInfo,sheets,sheetNum,rowNum);
            //下一个sheet处理
            sheetNum++;
        }
        wb.write(setInfo.getOut());
    }


    /**
     * 初始化
     */
    private static void init() {
        wb = new HSSFWorkbook();

        titleFont = wb.createFont();
        titleStyle = wb.createCellStyle();
        dateStyle = wb.createCellStyle();
        dateFont = wb.createFont();
        headStyle = wb.createCellStyle();
        headFont = wb.createFont();
        contentStyle = wb.createCellStyle();
        contentFont = wb.createFont();
        staticStyle = wb.createCellStyle();
        staticFont = wb.createFont();


        initTitleCellStyle();
        initTitleFont();
        initDateCellStyle();
        initDateFont();
        initHeadCellStyle();
        initHeadFont();
        initContentCellStyle();
        initContentFont();
        initStaticCellStyle();
        initStaticFont();

    }

    /**
     * 自动调整列宽
     */
    @SuppressWarnings("unused")
    private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum, String[] fieldNames) {
        for (int i = 0; i < fieldNames.length + 1; i++) {
            sheets[sheetNum].autoSizeColumn(i, true);
        }
    }


    /**
     * 创建标题行(需合并单元格)
     */
    private static void createTableTitleRow(ExportSetInfo setInfo,
                                            HSSFSheet[] sheets, int sheetNum) {
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0,
                setInfo.getFieldNames().get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(titleRange);
        HSSFRow titleRow = sheets[sheetNum].createRow(0);
        titleRow.setHeight((short) 800);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(setInfo.getTitles()[sheetNum]);
    }

    /**
     * 创建日期行(需合并单元格)
     */
    private static void createTableDateRow(ExportSetInfo setInfo,
                                           HSSFSheet[] sheets, int sheetNum) {
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0,
                setInfo.getFieldNames().get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(dateRange);
        HSSFRow dateRow = sheets[sheetNum].createRow(1);
        dateRow.setHeight((short) 350);
        HSSFCell dateCell = dateRow.createCell(0);
        dateCell.setCellStyle(dateStyle);
        dateCell.setCellValue("导出时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    /**
     * 创建统计信息行
     * @param setInfo
     * @param sheets
     * @param sheetNum
     * @param rowNum
     */
    private static void createTableStatic(ExportSetInfo setInfo,
                                           HSSFSheet[] sheets, int sheetNum,int rowNum) {

        if(setInfo.getStaticHeaders() == null || setInfo.getStaticFields() == null
                || setInfo.getStaticHeaders().size() < 1
                || setInfo.getStaticFields().size() < 1
                || setInfo.getStaticHeaders().size() != setInfo.getStaticFields().size()){
            //没有配置相关的统计信息
            return;
        }
        HSSFRow[] rows = getRows(sheets,sheetNum,rowNum,setInfo.getStaticFields().get(sheetNum).length);
        Object obj = setInfo.getObjs()[sheetNum];
        if(rows == null || rows.length < 1 || obj == null){
            return ;
        }

        for(int i = 0;i<rows.length;i++){
            HSSFRow row = rows[i];

            //统计字段描述
            HSSFCell descCell = row.createCell(0);
            descCell.setCellStyle(staticStyle);
            descCell.setCellValue(setInfo.getStaticHeaders().get(sheetNum)[i]);

            HSSFCell blankCell = row.createCell(1);
            blankCell.setCellStyle(staticStyle);

            //统计字段值
            HSSFCell valueCell = row.createCell(2);
            valueCell.setCellStyle(staticStyle);
            valueCell.setCellValue(setInfo.getStaticFields().get(sheetNum)[i]);
            Object value = ReflectionUtils.invokeGetterMethod(obj, setInfo.getStaticFields().get(sheetNum)[i]);
            valueCell.setCellValue(value == null ? "" : value.toString());
        }
    }


    /**
     * 创建表头行(需合并单元格)
     */
    private static void creatTableHeadRow(ExportSetInfo setInfo,
                                          HSSFSheet[] sheets, int sheetNum) {
        // 表头
        HSSFRow headRow = sheets[sheetNum].createRow(2);
        headRow.setHeight((short) 350);
        // 序号列
        HSSFCell snCell = headRow.createCell(0);
        snCell.setCellStyle(headStyle);
        snCell.setCellValue("序号");
        // 列头名称
        for (int num = 1, len = setInfo.getHeadNames().get(sheetNum).length; num <= len; num++) {
            HSSFCell headCell = headRow.createCell(num);
            headCell.setCellStyle(headStyle);
            headCell.setCellValue(setInfo.getHeadNames().get(sheetNum)[num - 1]);
        }
    }

    /**
     * 创建所有的Sheet
     */
    private static HSSFSheet[] getSheets(int num, String[] names) {
        HSSFSheet[] sheets = new HSSFSheet[num];
        for (int i = 0; i < num; i++) {
            sheets[i] = wb.createSheet(names[i]);
        }
        return sheets;
    }

    /**
     * 创建内容行的每一列(附加一列序号)
     */
    private static HSSFCell[] getCells(HSSFRow contentRow, int num) {
        HSSFCell[] cells = new HSSFCell[num + 1];

        for (int i = 0, len = cells.length; i < len; i++) {
            cells[i] = contentRow.createCell(i);
            cells[i].setCellStyle(contentStyle);
        }
        // 设置序号列值，因为出去标题行和日期行，所有-2
        cells[0].setCellValue(contentRow.getRowNum() - 2);

        return cells;
    }

    private static HSSFRow[] getRows(HSSFSheet[] sheets, int sheetNum,int startNum,int length){
        HSSFRow[] rows =  new HSSFRow[length];
        //统计信息隔一行

        CellRangeAddress range = new CellRangeAddress(startNum, startNum, 0,3);
        sheets[sheetNum].addMergedRegion(range);
        startNum++;

        for(int i = 0 ; i< length ; i++){
            CellRangeAddress titleRange = new CellRangeAddress(startNum + i, startNum + i, 0,1);
            sheets[sheetNum].addMergedRegion(titleRange);
            rows[i] = sheets[sheetNum].createRow(startNum + i);
            rows[i].setHeight((short) 400);
        }
        return rows;
    }


    /**
     * 初始化标题行样式
     */
    private static void initTitleCellStyle() {
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFont(titleFont);
        titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
    }

    /**
     * 初始化日期行样式
     */
    private static void initDateCellStyle() {
        dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateStyle.setFont(dateFont);
        dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
    }

    /**
     * 初始化表头行样式
     */
    private static void initHeadCellStyle() {
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headStyle.setFont(headFont);
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
        headStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        headStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        headStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
    }

    /**
     * 初始化内容行样式
     */
    private static void initContentCellStyle() {
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        contentStyle.setFont(contentFont);
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
        contentStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        contentStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        contentStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
        contentStyle.setWrapText(true);    // 字段换行
    }

    /**
     * 统计信息cell样式
     */
    private static void initStaticCellStyle() {
        staticStyle.setAlignment(CellStyle.ALIGN_CENTER);
        staticStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        staticStyle.setFont(staticFont);
        staticStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        staticStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        staticStyle.setBorderBottom(CellStyle.BORDER_THIN);
        staticStyle.setBorderLeft(CellStyle.BORDER_THIN);
        staticStyle.setBorderRight(CellStyle.BORDER_THIN);
        staticStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
        staticStyle.setBottomBorderColor(IndexedColors.BLUE_GREY.getIndex());
        staticStyle.setLeftBorderColor(IndexedColors.BLUE_GREY.getIndex());
        staticStyle.setRightBorderColor(IndexedColors.BLUE_GREY.getIndex());
    }

    /**
     * 初始化标题行字体
     */
    private static void initTitleFont() {
        titleFont.setFontName("华文楷体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setCharSet(Font.DEFAULT_CHARSET);
        titleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
    }

    /**
     * 初始化日期行字体
     */
    private static void initDateFont() {
        dateFont.setFontName("隶书");
        dateFont.setFontHeightInPoints((short) 10);
        dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        dateFont.setCharSet(Font.DEFAULT_CHARSET);
        dateFont.setColor(IndexedColors.BLUE_GREY.getIndex());
    }

    /**
     * 初始化表头行字体
     */
    private static void initHeadFont() {
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 10);
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setCharSet(Font.DEFAULT_CHARSET);
        headFont.setColor(IndexedColors.BLUE_GREY.getIndex());
    }

    /**
     * 初始化内容行字体
     */
    private static void initContentFont() {
        contentFont.setFontName("宋体");
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contentFont.setCharSet(Font.DEFAULT_CHARSET);
        contentFont.setColor(IndexedColors.BLUE_GREY.getIndex());
    }

    /**
     * 统计信息字体样式
     */
    private static void initStaticFont() {
        contentFont.setFontName("宋体");
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contentFont.setCharSet(Font.DEFAULT_CHARSET);
        contentFont.setColor(IndexedColors.BLUE_GREY.getIndex());

    }


    /**
     * 封装Excel导出的设置信息，对应的excel结构如下：
     *
     * sheet1--------------sheet2---------------sheet3
     * |                    |                     |
     * |                    |                     |
     * |                    |                     |
     * titles[0]            titles[1]             titles[2]
     * |                    |                     |
     * |                    |                     |
     * headNames.get(0)     headNames.get(1)      headNames.get(2)
     * |                    |                     |
     * |                    |                     |
     * fieldNames.get(0)    fieldNames.get(1)     fieldNames.get(2)
     *
     */
    public static class ExportSetInfo {
        @SuppressWarnings("unchecked")
        private LinkedHashMap<String, Collection> objsMap;

        private String[] titles;  // 标题名称集合---长度与objsMap的entry长度一致

        private List<String[]> headNames; //标题名集合

        private List<String[]> fieldNames; //字段名集合

        private OutputStream out;

        private Object[] objs;//统计数据的对象传入
        private List<String[]> staticFields;//统计数据的字段
        private List<String[]> staticHeaders;//统计数据的字段

        public List<String[]> getStaticHeaders() {
            return staticHeaders;
        }

        public void setStaticHeaders(List<String[]> staticHeaders) {
            this.staticHeaders = staticHeaders;
        }

        public List<String[]> getStaticFields() {
            return staticFields;
        }

        public void setStaticFields(List<String[]> staticFields) {
            this.staticFields = staticFields;
        }

        public Object[] getObjs() {
            return objs;
        }

        public void setObjs(Object[] objs) {
            this.objs = objs;
        }

        @SuppressWarnings("unchecked")
        public LinkedHashMap<String, Collection> getObjsMap() {
            return objsMap;
        }

        /**
         * @param objsMap 导出数据
         *                <p/>
         *                泛型
         *                String : 代表sheet名称
         *                List : 代表单个sheet里的所有行数据
         */
        @SuppressWarnings("unchecked")
        public void setObjsMap(LinkedHashMap<String, Collection> objsMap) {
            this.objsMap = objsMap;
        }

        public List<String[]> getFieldNames() {
            return fieldNames;
        }

        /**
         * @param fieldNames 对应每个sheet里的每行数据的对象的属性名称
         */
        public void setFieldNames(List<String[]> fieldNames) {
            this.fieldNames = fieldNames;
        }

        public String[] getTitles() {
            return titles;
        }

        /**
         * @param titles 对应每个sheet里的标题，即顶部大字
         */
        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        public List<String[]> getHeadNames() {
            return headNames;
        }

        /**
         * @param headNames 对应每个页签的表头的每一列的名称
         */
        public void setHeadNames(List<String[]> headNames) {
            this.headNames = headNames;
        }

        public OutputStream getOut() {
            return out;
        }

        /**
         * @param out Excel数据将输出到该输出流
         */
        public void setOut(OutputStream out) {
            this.out = out;
        }
    }

}
