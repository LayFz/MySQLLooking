package com.staraid.mysql.Enum;


import java.util.HashMap;
import java.util.Map;

public enum ResultCode {
    SUCCESS("200", "success.message"),
    BAD_REQUEST("400", "error.bad.request"),
    FORBIDDEN("403", "error.forbidden.access"),

    ERROR("500", "error.bad.server");

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
