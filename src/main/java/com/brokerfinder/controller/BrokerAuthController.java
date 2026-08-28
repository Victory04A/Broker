package com.brokerfinder.controller;

import com.brokerfinder.model.Broker;
import com.brokerfinder.model.BrokerNotification;
import com.brokerfinder.service.BrokerNotificationService;
import com.brokerfinder.service.BrokerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//broker authentication and dashboard.
@Controller
@RequestMapping("/broker")
public class BrokerAuthController {

    private final BrokerService brokerService;
    private final BrokerNotificationService notificationService;

    public BrokerAuthController(BrokerService brokerService,
                                BrokerNotificationService notificationService) {
        this.brokerService = brokerService;
        this.notificationService = notificationService;
    }

    //Show broker login form.

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("brokerUser") != null) {
            return "redirect:/broker/dashboard";
        }
        return "broker-login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        Optional<Broker> optional = brokerService.findByEmail(email);
        if (optional.isPresent()) {
            Broker broker = optional.get();
            if (broker.getPassword().equals(password)) {
                if (!"APPROVED".equals(broker.getStatus())) {
                    model.addAttribute("error", "Your account is not yet approved by admin.");
                    return "broker-login";
                }
                session.setAttribute("brokerUser", broker);
                return "redirect:/broker/dashboard";
            }
        }
        model.addAttribute("error", "Invalid email or password.");
        return "broker-login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Broker broker = (Broker) session.getAttribute("brokerUser");
        if (broker == null) {
            return "redirect:/broker/login";
        }
        List<BrokerNotification> notifications = notificationService.getNotificationsForBroker(broker.getId());
        model.addAttribute("notifications", notifications);
        model.addAttribute("broker", broker);
        return "broker-dashboard";
    }

    /**
     * Accept a client request.
     */
    @PostMapping("/accept/{notificationId}")
    public String acceptRequest(@PathVariable Long notificationId, HttpSession session) {
        if (session.getAttribute("brokerUser") == null) {
            return "redirect:/broker/login";
        }
        notificationService.acceptRequest(notificationId);
        return "redirect:/broker/dashboard";
    }

    /**
     * Reject a client request.
     */
    @PostMapping("/reject/{notificationId}")
    public String rejectRequest(@PathVariable Long notificationId, HttpSession session) {
        if (session.getAttribute("brokerUser") == null) {
            return "redirect:/broker/login";
        }
        notificationService.rejectRequest(notificationId);
        return "redirect:/broker/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("brokerUser");
        return "redirect:/broker/login";
    }
}
