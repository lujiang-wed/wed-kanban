package com.wednesday.kanban.web.Index.Controller;

import com.wednesday.kanban.web.Index.Controller.common.ConfigReader;
import com.wednesday.kanban.web.Index.Controller.common.ExcelHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: <a href='mailto:wylipengming@chinabak.com.cn'>wylipengming</a>
 * Date: 14-4-14
 * Time: 上午11:17
 * <p>
 *     <h>需要excel导出功能的controller继承此方法，并且调用outputExcel方法</h>
 *     <b>注：T表示列表的对象，D表示统计对象</b>
 *     <p>
 *         如：
 *           @RequestMapping(value = {"/outputExcel.htm"},method = {RequestMethod.GET})
 *           @Override
 *           public void exportExcel(HttpServletResponse response, HttpServletRequest request) {
 *               ConsumeInfoQueryParam param = new ConsumeInfoQueryParam();
 *               super.bindRequestParam(request, param);
 *               ParamWrapper<ConsumeInfoQueryParam> paramWrapper = super.genParam(param,request);
 *               outputExcel(request,response,paramWrapper,false);
 *           }
 *     </p>
 * </p>
 *
 * 使用场景：
 * 1、需要excel导出功能的controller
 * 2、单一的入参对象以及返回结果对象
 * 3、入参集成自 BasePageParam，结果继承自 Page
 *
 */
public abstract class ExcelController<T,D,P> extends ExcelHelper {
    private String configPath;
    private String title;

    protected ExcelController() {
    }

    public ExcelController(String configPath, String title) {
        this.configPath = configPath;
        this.title = title;
    }

    protected void outputExcel(HttpServletRequest request,HttpServletResponse response,Boolean addStatic,P param){
        //查询所有记录
        List<T> datas = getResults(request, param);
        //导出excel操作
        if(!datas.isEmpty()){
            //excel export
            LinkedHashMap<String,Collection> objsMap = new LinkedHashMap<String,Collection>();
            objsMap.put(title,datas);

            if(Boolean.FALSE.equals(addStatic)){
                //不需要统计信息的方式
                exportExcel(objsMap, null, configPath, title, request, response);
            }else{
                //查询统计信息
                D[] statics = getStaticResult(request, param);
                exportExcel(objsMap, statics, configPath, title, request, response);
            }
        }
    }

    protected abstract  D[] getStaticResult(HttpServletRequest request,P param);

    protected abstract List<T> getResults(HttpServletRequest request,P param) ;


    public List<String[]> adapteeHeaders(String configPath) throws Exception {
        return ConfigReader.getInstall().getHeaders(configPath);
    }

    public List<String[]> adapteeAttributes(String configPath) throws Exception {
        return ConfigReader.getInstall().getAttributes(configPath);
    }

    @Override
    public String[] adapteeTitles(String configPath) throws Exception {
        String[] titles = ConfigReader.getInstall().getTitles(configPath);
        return titles;
    }

    @Override
    public List<String[]> adapteeStaticFields(String configPath) throws Exception {
        return ConfigReader.getInstall().getStaticAttributes(configPath);
    }

    @Override
    public List<String[]> adapteeStaticHeaders(String configPath) throws Exception {
        return  ConfigReader.getInstall().getStaticHeaders(configPath);
    }
}
