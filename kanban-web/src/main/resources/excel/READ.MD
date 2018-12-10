===
excel组件使用规则及简要说明
===

【注：配置文件应该在utf-8编写，否则或出现导出的excel会乱码】

1、必填项：attribute，header,title
   选填项：staticHeader,staticAttribute

2、分隔符
    sheet数据分割：#
    属性或者条目分割：，
    详见代码：ConfigReader.java

3、对应关系：
    对于每一个excel：有若干sheet组成，每个sheet由sheetName和内容组成

    内容的格式（一）为：
    第一行：title       【标题】
    第二行：time        【时间】
    第三行：static      【统计】
    第四行：header      【属性】
    第五到第N行：data   【数据】

    内容的格式（二）为：
    第一行：title       【标题】
    第二行：time        【时间】
    第三行：header      【属性】
    第四到第N行：data   【数据】




4、数据结构

(一)对应的数据结构为：

    title       --->    Array
    static      --->    Object
    
    header      --->    List<Array>
    data        --->    LinkedHashMap<String  ,  List<   Object   >>
                                         |         |       |
                                         |         |       |
                                      sheetName   Data     |
                                                           |
                                                    ————————
                                                    |             |
                                                  Fields       getFields
                                                    |             |
                                              【attributes】   【Data】
    


(二)对应的数据结构为：

    title       --->    Array
    header      --->    List<Array>
    data        --->    LinkedHashMap<String  ,  List<   Object   >
                                         |         |       |
                                         |         |       |
                                      sheetName   Data     |
                                                           |
                                                    ————————
                                                    |             |
                                                  Fields       getFields
                                                    |             |
                                              【attributes】   【Data】
