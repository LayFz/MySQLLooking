package com.staraid.mysql.utils;

import ch.qos.logback.core.joran.action.Action;
import com.staraid.mysql.Enum.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

@Component
public class Result extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static MessageSource messageSource;


    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        Result.messageSource = messageSource;
    }

    // 处理成功结果
    public static Result success() {
        return buildResult(ResultCode.SUCCESS, null);
    }

    public static Result success(Object data) {
        return buildResult(ResultCode.SUCCESS, data);
    }

    // 处理错误结果
    public static Result error(ResultCode resultCode) {
        return buildResult(resultCode, null);
    }

    public static Result error(ResultCode resultCode, Object... args) {
        return buildResult(resultCode, null, args);
    }

    public static Result error(String code) {
        ResultCode resultCode = ResultCode.getByCode(code);
        if (resultCode == null) {
            resultCode = ResultCode.ERROR; // Default error code if not found
        }
        return buildResult(resultCode, null);
    }

    public static Result error(String code, Object... args) {
        ResultCode resultCode = ResultCode.getByCode(code);
        if (resultCode == null) {
            resultCode = ResultCode.ERROR; // Default error code if not found
        }
        return buildResult(resultCode, null, args);
    }

    private static Result buildResult(ResultCode resultCode, Object data, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(resultCode.getMessageKey(), args, locale);
        Result result = new Result();
        result.setCode(resultCode.getCode());
        result.setMessage(message);
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    private void setCode(String code) {
        put("code", code);
    }

    private void setMessage(String message) {
        put("message", message);
    }

    private void setData(Object data) {
        put("data", data);
    }
}
