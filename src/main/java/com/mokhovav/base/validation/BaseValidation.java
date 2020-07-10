package com.mokhovav.base.validation;

import org.springframework.stereotype.Component;

@Component("BaseValidation")
public class BaseValidation implements ValidationCheck {
    public BaseValidation() {
    }

    @Override
    public boolean isIP(String address){
        return address.matches("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
    }
}
