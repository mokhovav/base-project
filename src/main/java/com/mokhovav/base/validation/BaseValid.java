package com.mokhovav.base.validation;

import org.springframework.stereotype.Component;

@Component
public class BaseValid {
    public BaseValid() {
    }

    public static boolean isIP(String address){
        return address.matches("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
    }

    public static boolean nullOrEmpty(String str){
        return str==null || str.isEmpty();
    }
}
