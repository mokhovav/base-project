package com.mokhovav.base;

import com.mokhovav.base.databases.SQL.SQLService;
import com.mokhovav.base.exceptions.BaseRestException;
import com.mokhovav.base.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    SQLService service;

    @Autowired
    Logger logger;
    @GetMapping
    public String mainMapping() throws BaseRestException {
        throw new BaseRestException("1","2","3");
    }
}
