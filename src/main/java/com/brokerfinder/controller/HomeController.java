package com.brokerfinder.controller;

import com.brokerfinder.model.Broker;
import com.brokerfinder.service.BrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller for the home page and broker search functionality.
 * Handles Thymeleaf view rendering.
 */
@Controller
public class HomeController {

    private final BrokerService brokerService;

    public HomeController(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    /**
     * Display the home page with a search form.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Handle broker search from the home page form.
     * Returns results to the broker-results page.
     */
    @GetMapping("/search")
    public String searchBrokers(
            @RequestParam String region,
            @RequestParam String specialty,
            Model model) {
        List<Broker> brokers = brokerService.searchBroker(region, specialty);
        model.addAttribute("brokers", brokers);
        model.addAttribute("region", region);
        model.addAttribute("specialty", specialty);
        return "broker-results";
    }

    /**
     * Display broker detail page.
     * Redirects to home with error if broker not found.
     */
    @GetMapping("/broker/{id}")
    public String viewBroker(@PathVariable Long id, Model model) {
        try {
            Broker broker = brokerService.getBrokerById(id);
            model.addAttribute("broker", broker);
            return "broker-detail";
        } catch (RuntimeException e) {
            return "redirect:/?error=Broker not found";
        }
    }
}
