package org.example.email_demo.controller;

import org.example.email_demo.dto.SubscriberDTO;
import org.example.email_demo.model.Subscriber;
import org.example.email_demo.repository.SubscriberRepository;
import org.example.email_demo.service.SubscriberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubscriberApiControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api";
    }

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

    @Test
    void testAddSubscriberAndGetSubscribers() {
        // Create a new subscriber
        SubscriberDTO subscriberDTO = new SubscriberDTO("test" + System.currentTimeMillis() + "@example.com", "Test User");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SubscriberDTO> request = new HttpEntity<>(subscriberDTO, headers);

        ResponseEntity<Subscriber> response = testRestTemplate.postForEntity(getBaseUrl() + "/subscribe", request, Subscriber.class);

        // Verify subscriber was created
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Subscriber subscriber = response.getBody();

        assertEquals(subscriber.getEmail(), subscriberDTO.getEmail());
        assertEquals(subscriber.getName(), subscriberDTO.getName());

        // Get all subscribers
        ResponseEntity<Subscriber[]> subscribersResponse = testRestTemplate.getForEntity(
                getBaseUrl() + "/subscribers", Subscriber[].class);

        List<Subscriber> subscribers = Arrays.asList(subscribersResponse.getBody());

        assertThat(subscribers).isNotEmpty();
        assertThat(subscribers).contains(subscriber);
    }

}
