package org.example.email_demo.service;

import org.example.email_demo.model.Subscriber;
import org.example.email_demo.repository.SubscriberRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriberServiceTest {

    @Test
    void addSubscriberReturnsSubscriberWhenEmailNotExist() {
        SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
        SubscriberService subscriberService = new SubscriberService(subscriberRepository);
        String email = "test@example.com";
        String name = "Test User";
        String ipAddress = "127.0.0.1";
        Subscriber subscriber = new Subscriber(email, name, ipAddress, null);

        when(subscriberRepository.existsByEmail(email)).thenReturn(false);
        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(subscriber);

        Optional<Subscriber> result = subscriberService.addSubscriber(email, name, ipAddress);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void addSubscriberReturnsExistingSubscriberWhenEmailExists() {
        SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
        SubscriberService subscriberService = new SubscriberService(subscriberRepository);
        String email = "test@example.com";
        String name = "Test User";
        String ipAddress = "127.0.0.1";
        Subscriber existingSubscriber = new Subscriber(email, name, ipAddress, null);

        when(subscriberRepository.existsByEmail(email)).thenReturn(true);
        when(subscriberRepository.findById(email)).thenReturn(Optional.of(existingSubscriber));

        Optional<Subscriber> result = subscriberService.addSubscriber(email, name, ipAddress);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void removeSubscriberDeletesSubscriber() {
        SubscriberRepository subscriberRepository = mock(SubscriberRepository.class);
        SubscriberService subscriberService = new SubscriberService(subscriberRepository);
        String email = "test@example.com";

        doNothing().when(subscriberRepository).deleteById(email);

        subscriberService.removeSubscriber(email);

        verify(subscriberRepository, times(1)).deleteById(email);
    }
}
