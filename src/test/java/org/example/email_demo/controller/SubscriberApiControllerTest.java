package org.example.email_demo.controller;

import org.example.email_demo.dto.SubscriberDTO;
import org.example.email_demo.model.Subscriber;
import org.example.email_demo.service.SubscriberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubscriberApiControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void addSubscriber_ReturnsOk_WhenSubscriberIsAdded() {

        SubscriberService subscriberService = Mockito.mock(SubscriberService.class);
        SubscriberDTO dto = new SubscriberDTO("test@example.com", "Test");
        Subscriber subscriber = new Subscriber("test@example.com", "Test", "127.0.0.1", null);

        Mockito.when(subscriberService.addSubscriber(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(subscriber));

        SubscriberApiController controller = new SubscriberApiController(subscriberService);

        ResponseEntity<Subscriber> response = testRestTemplate.postForEntity("/api/subscribe", dto, Subscriber.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test@example.com", response.getBody().getEmail());
    }

    @Test
    void addSubscriber_ReturnsBadRequest_WhenSubscriberIsNotAdded() {
        SubscriberService subscriberService = Mockito.mock(SubscriberService.class);
        SubscriberDTO dto = new SubscriberDTO("invalid-email", "Test");

        Mockito.when(subscriberService.addSubscriber(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());

        SubscriberApiController controller = new SubscriberApiController(subscriberService);

        ResponseEntity<Subscriber> response = testRestTemplate.postForEntity("/api/subscribe", dto, Subscriber.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
