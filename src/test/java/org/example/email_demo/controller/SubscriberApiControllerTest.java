package org.example.email_demo.controller;

import org.example.email_demo.dto.SubscriberDTO;
import org.example.email_demo.model.Subscriber;
import org.example.email_demo.service.SubscriberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubscriberApiControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private SubscriberService subscriberService;

    @Test
    void addSubscriber_ReturnsOk_WhenSubscriberIsAdded() {
        SubscriberDTO dto = new SubscriberDTO("test@example.com", "Test");
        Subscriber subscriber = new Subscriber("test@example.com", "Test", "127.0.0.1", null);

        when(subscriberService.addSubscriber(any(), any(), any())).thenReturn(Optional.of(subscriber));

        ResponseEntity<Subscriber> response = testRestTemplate.postForEntity("/api/subscribe", dto, Subscriber.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subscriber.getEmail(), response.getBody().getEmail());
    }

    @Test
    void addSubscriber_ReturnsBadRequest_WhenSubscriberIsNotAdded() {
        SubscriberDTO dto = new SubscriberDTO("invalid-email", "Test");

        when(subscriberService.addSubscriber(any(), any(), any())).thenReturn(Optional.empty());

        ResponseEntity<Subscriber> response = testRestTemplate.postForEntity("/api/subscribe", dto, Subscriber.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
