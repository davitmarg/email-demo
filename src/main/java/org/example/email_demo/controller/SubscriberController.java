package org.example.email_demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.email_demo.dto.SubscriberDTO;
import org.example.email_demo.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("subscriber", new SubscriberDTO());
        model.addAttribute("subscribers", subscriberService.getAllSubscribers());

        System.out.println("Subscriber form loaded!");
        return "landing";
    }

    @PostMapping("/subscribe")
    public String subscribe(
            @Valid @ModelAttribute("subscriber") SubscriberDTO subscriberDTO,
            BindingResult result,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "landing";
        }

        String ipAddress = request.getRemoteAddr();
        subscriberService.addSubscriber(subscriberDTO.getEmail(), subscriberDTO.getName(), ipAddress);
        redirectAttributes.addAttribute("success", "Subscription successful!");

        return "redirect:/";
    }

}
