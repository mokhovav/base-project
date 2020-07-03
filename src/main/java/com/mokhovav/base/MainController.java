package com.mokhovav.base;

import com.mokhovav.base.exceptions.BaseRestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public String mainMapping() throws BaseRestException {
        throw new BaseRestException("1","2","3");
    }
}
