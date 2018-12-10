package util;

public enum AjaxResultCodeEnum {

    SUCCESS("A00000", "成功"),

    SYSTEM_ERROR("A00001", "系统错误"),

    AUTH_ERROR("A00002", "未登录， 请先登录"),

    REALNAMED_ERROR("A000011", "实名认证中"),

    MISSING_PARAMETER("A00003", "缺少必要参数"),

    PARAMETER_ERROR("A00004", "必要参数错误"),

    TIMEOUT("A00005", "系统超时"),

    LENGTH_OVER_LIMIT("A00006", "请求长度超出限制"),

    METHOD_NOT_SUPPORTED("A00007", "未支持的HTTP请求方式，请选择正确的GET/POST方式"),

    INTERFACE_NOT_FOUND("A00008", "接口不存在"),

    NOT_AUTH_ERROR("A00009", "未授权， 无权限"),

    REQUEST_LIMIT_ERROR("A00010", "请求次数超限"),

    NO_DATA("A00012","无数据"),

    BUSINESS_CHECK_ERROR("A00013", "业务校验失败"),

    WRONG_CAPTCHA("A00015","图片验证码错误"),

    APP_AUTH_INVALID("APP001","登录超时，请从app中重新打开该页面"),
    ;

    private String code;
    private String message;

    private AjaxResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "AjaxResultCodeEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
