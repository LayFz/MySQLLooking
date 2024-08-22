package com.staraid.mysql.Enum;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回前端参数
 */
public enum ResultCode {
    SUCCESS("200", "success.message"),
    BAD_REQUEST("400", "error.bad.request"),
    FORBIDDEN("403", "error.forbidden.access"),

    ERROR("500", "error.bad.server"),

    FAIL("400", "error.bad.operate"),
    DATANOTFIND("404", "error.not.find"),
    UNAUTHORIZED("400", "fail.un.auth"),
    DATA_HAS_BEEN_CONNECTION("400", "data.has.been.connection"),
    ERROR_DONT_KNOW("500", "error.dont.know"),
    NET_WORK_ERROR("500", "error.net.work.error"),
    UN_AUTH_SQL("500", "error.un.auth.sql");

    private static final Map<String, ResultCode> CODE_MAP = new HashMap<>();

    // 快速填充
    static {
        for (ResultCode resultCode : values()) {
            CODE_MAP.put(resultCode.code, resultCode);
        }
    }

    private final String code;
    private final String messageKey;

    ResultCode(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public String getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public static ResultCode getByCode(String code) {
        return CODE_MAP.get(code);
    }
}
