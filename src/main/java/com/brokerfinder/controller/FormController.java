package com.brokerfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for rendering static form pages.
 */
@Controller
public class FormController {

    /**
     * Display the broker registration form page.
     */
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register-broker";
    }

    /**
     * Display the client request form page.
     */
    @GetMapping("/request")
    public String showRequestForm() {
        return "request-form";
    }
}
