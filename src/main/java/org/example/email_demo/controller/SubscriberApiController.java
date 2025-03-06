package org.example.email_demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.email_demo.dto.SubscriberDTO;
import org.example.email_demo.model.Subscriber;
import org.example.email_demo.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SubscriberApiController {

    private final SubscriberService subscriberService;

    @Autowired
    public SubscriberApiController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping("/api/subscribers")
    public ResponseEntity<List<Subscriber>> getSubscribers() {
        return ResponseEntity.ok(subscriberService.getAllSubscribers());
    }

    @PostMapping("/api/subscribe")
    public ResponseEntity<Subscriber> addSubscriber(@RequestBody @Valid SubscriberDTO subscriberDTO, HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        Optional<Subscriber> subscriber = subscriberService.addSubscriber(subscriberDTO.getEmail(), subscriberDTO.getName(), ipAddress);

        return subscriber.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());

    }


}
