package org.example.email_demo.controller;

import org.example.email_demo.dto.SubscriberDTO;
import org.example.email_demo.model.Subscriber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubscriberControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void subscribe_ReturnsRedirect_WhenSubscriptionIsSuccessful() {
        // Prepare the subscriber DTO for the form submission
        SubscriberDTO subscriberDTO = new SubscriberDTO("test" + System.currentTimeMillis() + "@example.com", "Test User");

        // Create a MultiValueMap to simulate a form submission
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", subscriberDTO.getEmail());
        formData.add("name", subscriberDTO.getName());

        // Set the appropriate Content-Type for form submission
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create an HttpEntity with the form data and headers
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        // Send the POST request to subscribe the user
        ResponseEntity<Void> response = testRestTemplate.exchange(
                getBaseUrl() + "/subscribe", HttpMethod.POST, request, Void.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
