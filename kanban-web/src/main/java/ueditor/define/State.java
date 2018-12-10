package ueditor.define;

/**
 * 项目名称：coach
 * 包名称：  ueditor.define
 * 类名称:   State
 * 类描述:   //类职责详细说明
 * 创建人:    wypengbo
 * 创建时间:  2014/12/12
 * 修改人:    //该类修改人
 * 修改时间:  //该类修改时间
 * 修改备注:  //该类修改备注
 *
 * @version 1.0.0 <br>
 */
public abstract interface State
{
    public abstract boolean isSuccess();

    public abstract void putInfo(String paramString1, String paramString2);

    public abstract void putInfo(String paramString, long paramLong);

    public abstract String toJSONString();
}
