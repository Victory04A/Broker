package com.brokerfinder.controller;

import com.brokerfinder.model.ClientRequest;
import com.brokerfinder.service.BrokerService;
import com.brokerfinder.service.ClientRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for admin dashboard views.
 * Handles broker approval/rejection and viewing client requests.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BrokerService brokerService;
    private final ClientRequestService clientRequestService;

    public AdminController(BrokerService brokerService, ClientRequestService clientRequestService) {
        this.brokerService = brokerService;
        this.clientRequestService = clientRequestService;
    }

    /**
     * Display admin dashboard with pending brokers and client requests.
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pendingBrokers", brokerService.getBrokersByStatus("PENDING"));
        model.addAttribute("approvedBrokers", brokerService.getBrokersByStatus("APPROVED"));
        model.addAttribute("rejectedBrokers", brokerService.getBrokersByStatus("REJECTED"));
        model.addAttribute("clientRequests", clientRequestService.getAllRequests());
        return "admin-dashboard";
    }

    /**
     * Approve a broker from the admin dashboard.
     */
    @PostMapping("/approve/{id}")
    public String approveBroker(@PathVariable Long id) {
        brokerService.approveBroker(id);
        return "redirect:/admin/dashboard";
    }

    /**
     * Reject a broker from the admin dashboard.
     */
    @PostMapping("/reject/{id}")
    public String rejectBroker(@PathVariable Long id) {
        brokerService.rejectBroker(id);
        return "redirect:/admin/dashboard";
    }
}
