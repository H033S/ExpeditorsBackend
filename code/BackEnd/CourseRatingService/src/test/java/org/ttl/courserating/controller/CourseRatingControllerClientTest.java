package org.ttl.courserating.controller;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CourseRatingControllerClientTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    private String ratingUrl;

    @PostConstruct
    public void init() {
        var baseUrl = "http://localhost:" + port;
        var rootUrl = "/courseRating";
        ratingUrl = rootUrl + "/{id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Test
    public void testGetRating() {
       ResponseEntity<Integer> result =  restClient.get()
             .uri(ratingUrl, 1)
             .retrieve()
             .toEntity(Integer.class);

       assertEquals(HttpStatus.OK, result.getStatusCode());

       int rating = result.getBody().intValue();

       assertTrue(rating > 0);

    }

}
