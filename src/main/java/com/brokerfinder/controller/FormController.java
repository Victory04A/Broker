package com.brokerfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * display form pages.
 */
@Controller
public class FormController {

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register-broker";
    }

    @GetMapping("/request")
    public String showRequestForm() {
        return "request-form";
    }
}
