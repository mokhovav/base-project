package com.mokhovav.base.validation;

import org.springframework.stereotype.Component;

@Component
public interface ValidationCheck {
    boolean isIP(String address);
}
