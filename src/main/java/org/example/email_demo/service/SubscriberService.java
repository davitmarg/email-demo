package org.example.email_demo.service;

import org.example.email_demo.model.Subscriber;
import org.example.email_demo.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public Subscriber addSubscriber(String email, String name, String ipAddress) {
        if(subscriberRepository.hasSubscriber(email)) {
            return subscriberRepository.getSubscriber(email);
        }

        LocalDateTime creationTimestamp = LocalDateTime.now();
        Subscriber subscriber = new Subscriber(email, name, ipAddress, creationTimestamp);
        subscriberRepository.addSubscriber(subscriber);
        return subscriber;
    }

    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.getSubscribers();
    }

    public Subscriber getSubscriber(String email) {
        return subscriberRepository.getSubscriber(email);
    }

    public void removeSubscriber(String email) {
        subscriberRepository.removeSubscriber(email);
    }
}
