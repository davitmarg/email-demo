package org.example.email_demo.repository;

import org.example.email_demo.model.Subscriber;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SubscriberRepository {
    private final Map<String, Subscriber> subscribers = new HashMap<>();

    public void addSubscriber(Subscriber subscriber) {
        subscribers.put(subscriber.getEmail(), subscriber);
    }

    public List<Subscriber> getSubscribers() {
        return List.copyOf(subscribers.values());
    }

    public boolean hasSubscriber(String email) {
        return subscribers.containsKey(email);
    }

    public Subscriber getSubscriber(String email) {
        return subscribers.get(email);
    }

    public void removeSubscriber(String email) {
        subscribers.remove(email);
    }
}
