package org.example.email_demo.service;

import org.example.email_demo.model.Subscriber;
import org.example.email_demo.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public Optional<Subscriber> addSubscriber(String email, String name, String ipAddress) {
        if(subscriberRepository.existsByEmail(email)) {
            return subscriberRepository.findById(email);
        }

        LocalDateTime creationTimestamp = LocalDateTime.now();
        Subscriber subscriber = new Subscriber(email, name, ipAddress, creationTimestamp);
        subscriberRepository.save(subscriber);
        return Optional.of(subscriber);
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    public Optional<Subscriber> getSubscriber(String email) {
        return subscriberRepository.findById(email);
    }

    public void removeSubscriber(String email) {
        subscriberRepository.deleteById(email);
    }
}
