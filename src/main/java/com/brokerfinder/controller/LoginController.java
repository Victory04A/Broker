package com.brokerfinder.controller;

import com.brokerfinder.model.Admin;
import com.brokerfinder.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for admin authentication.
 * Handles login and logout using HttpSession.
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    private final AdminRepository adminRepository;

    public LoginController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Show the login form.
     * If already logged in, redirect to dashboard.
     */
    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("adminUser") != null) {
            return "redirect:/admin/dashboard";
        }
        return "admin-login";
    }

    /**
     * Process login: validate credentials and create session.
     */
    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        // Look up admin by username
        Admin admin = adminRepository.findByUsername(username).orElse(null);

        if (admin != null && admin.getPassword().equals(password)) {
            // Login success - store admin in session
            session.setAttribute("adminUser", admin);
            return "redirect:/admin/dashboard";
        }

        // Login failed
        model.addAttribute("error", "Invalid username or password.");
        return "admin-login";
    }

    /**
     * Logout: invalidate the session and redirect to login.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
