package com.mokhovav.base.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BaseRestExceptions extends RestException {
    private BaseResponse response = new BaseResponse();

    public BaseRestExceptions() {
    }

    public BaseRestExceptions(String message) {
        super(message);
    }

    public void add(String code, String type, String message){
        response.add(code, type, message);
    }
    @Override
    public BaseResponse getResponse() {
        return response;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
    @Override
    public String getMsg(){
        String message = "\n";
        for (BaseResponse.BaseError error : response.getErrors()) {
            message += "Exception: \033[33mcode\033[0m " + error.getCode() + " \033[33mtype\033[0m " +error.getType() + " \033[33mmessage\033[0m " + error.getMessage() + "\n";
        }
        return message;
    }

    private class BaseResponse {
        private BaseErrors errors = new BaseErrors();

        public BaseResponse() {
        }

        public void add(String code, String type, String message){
            errors.add(new BaseError(code, type, message));
        }

        private class BaseErrors extends ArrayList<BaseError>{
        }

        public BaseErrors getErrors() {
            return errors;
        }

        private class BaseError {
            private  String code;
            private  String type;
            private  String message;
            public BaseError() {
            }
            public BaseError(String code, String type, String message) {
                this.code = code;
                this.type = type;
                this.message = message;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }

    }



}
