package com.mokhovav.base.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BaseRestException extends RestException {
    private BaseResponse response;

    public BaseRestException() {
        response = new BaseResponse();
    }

    public BaseRestException(String code, String type, String message) {
        super(message);
        response = new BaseResponse(code, type, message);
    }

    public BaseRestException(String message) {
        super(message);
        response = new BaseResponse(null, null, message);
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
        BaseResponse.BaseError error = response.getError();
        return "\033[33mcode\033[0m " + error.getCode() + " \033[33mtype\033[0m " +error.getType() + " \033[33mmessage\033[0m " + error.getMessage();
    }

   private class BaseResponse {
        private BaseError error;
        public BaseResponse() {
            error = new BaseError();
        }

        public BaseResponse(String code, String type, String message) {
            error = new BaseError(code, type, message);
        }

        public BaseError getError() {
            return error;
        }

        public void setError(BaseError error) {
            this.error = error;
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
