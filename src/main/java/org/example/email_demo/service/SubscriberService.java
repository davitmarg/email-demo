package org.example.email_demo.service;

import org.example.email_demo.model.Subscriber;
import org.example.email_demo.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> bc8e9ef95f781037f1c64405990f4d5185abd86a

@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

<<<<<<< HEAD
    public Optional<Subscriber> addSubscriber(String email, String name, String ipAddress) {
        if(subscriberRepository.existsByEmail(email)) {
            return subscriberRepository.findById(email);
=======
    public Subscriber addSubscriber(String email, String name, String ipAddress) {
        if(subscriberRepository.hasSubscriber(email)) {
            return subscriberRepository.getSubscriber(email);
>>>>>>> bc8e9ef95f781037f1c64405990f4d5185abd86a
        }

        LocalDateTime creationTimestamp = LocalDateTime.now();
        Subscriber subscriber = new Subscriber(email, name, ipAddress, creationTimestamp);
<<<<<<< HEAD
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
=======
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
>>>>>>> bc8e9ef95f781037f1c64405990f4d5185abd86a
    }

}
