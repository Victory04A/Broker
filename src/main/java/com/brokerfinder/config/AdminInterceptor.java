package com.brokerfinder.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor that protects admin routes.
 * Redirects to login page if no admin is logged in.
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // Prevent browser from caching admin pages
        // This ensures the back button shows login page after logout
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        HttpSession session = request.getSession(false);

        // Allow access if admin is logged in
        if (session != null && session.getAttribute("adminUser") != null) {
            return true;
        }

        // Redirect to login page
        response.sendRedirect("/admin/login");
        return false;
    }
}
