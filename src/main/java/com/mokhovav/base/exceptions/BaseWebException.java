package com.mokhovav.base.exceptions;

import org.springframework.stereotype.Component;

@Component
public class BaseWebException extends WebException {
    public BaseWebException() {
    }

    public BaseWebException(String message) {
        super(message);
    }

    @Override
    public String getMsg() {
        return "\033[34mWebException: \033[0m " + getMessage();
    }
}
