package com.mokhovav.base.exceptions;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionController {
    @Autowired
    private Logger logger;

    @ExceptionHandler(RestException.class)
    private ResponseEntity<?> responseEntity(RestException exception, HttpServletRequest request){
        logger.info("RestException: "+exception.getMsg());
        return new ResponseEntity<>(exception.getResponse(), exception.getStatus());
    }
}
