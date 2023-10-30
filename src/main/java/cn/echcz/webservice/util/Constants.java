package cn.echcz.webservice.util;

/**
 * 全局常量
 */
public class Constants {
    private Constants() {
        // Constant class
    }
    /**
     * 当前请求的事务ID HTTP请求头
     */
    public static final String HTTP_HEADER_TRANSACTION_ID = "X-Transaction-Id";
    /**
     * 当前用户在当前请求中拥有的数据权限 HTTP请求头
     */
    public static final String HTTP_HEADER_DATA_PERMISSION = "X-Data-Permission";

    /**
     * 存放事务ID的SLF4J MDC 键
     */
    public static final String SLF4J_MDC_KEY_TRANSACTION_ID = "transactionId";

    /**
     * 数据权限 - 全部
     */
    public static final String DATA_PERMISSION_ALL = "ALL";
    /**
     * 数据权限 - （当前）租户
     */
    public static final String DATA_PERMISSION_TENANT = "TENANT";
    /**
     * 数据权限 - （当前）用户
     */
    public static final String DATA_PERMISSION_USER = "USER";
}
