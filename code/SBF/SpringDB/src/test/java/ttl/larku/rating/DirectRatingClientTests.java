package ttl.larku.rating;

import java.math.BigDecimal;
import java.util.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DirectRatingClientTests {

   private RestClient restClient;

   String normalUser = "manoj";
   String adminUser = "bobby";
   String pw = System.getenv("CLIENT_PASSWORD");

   @BeforeAll
   public void beforeAll() {
      //var baseUrl = "http://localhost:10001/rating";
      var baseUrl = "https://localhost:10043";

      this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
   }

   @Test
   public void testRestClientToRatingServiceWithoutAuthShouldFailWith401() {
      assertThrows(HttpClientErrorException.Unauthorized.class, () -> {
         ResponseEntity<?> response = restClient.get()
               .uri("/rating/{id}", 1)
               .retrieve()
               .toBodilessEntity();
      });
   }

   @Test
   public void testRestClientToRatingServiceWithAuthAndPermissionShouldSucceed() {
         var encoded =
               Base64.getEncoder().encodeToString((normalUser  + ":" + pw).getBytes());
         String basicAuthHeader = "Basic " + encoded;
         ResponseEntity<BigDecimal> response = restClient.get()
               .uri("/rating/{id}", 1)
               .header("Authorization", basicAuthHeader)
               .retrieve()
               .toEntity(BigDecimal.class);

         assertEquals(HttpStatus.OK, response.getStatusCode());

         System.out.println("Result: " + response.getBody());
   }

   @Test
   public void testRestClientToPrivilegedOperationWithAuthButNoPermissionShouldFailWith403() {
      assertThrows(HttpClientErrorException.Forbidden.class, () -> {
         var encoded =
               Base64.getEncoder().encodeToString((normalUser + ":" + pw).getBytes());
         String basicAuthHeader = "Basic " + encoded;
         ResponseEntity<BigDecimal> response = restClient.put()
               .uri("/admin/upperLimit/{id}", 1)
               .header("Authorization", basicAuthHeader)
               .retrieve()
               .toEntity(BigDecimal.class);

         System.out.println("Result: " + response.getBody());
      });
   }

   @Test
   public void testRestClientToPrivilegedOperationWithAuthAndPermissionShouldSucceed() {
         var encoded =
               Base64.getEncoder().encodeToString((adminUser + ":" + pw).getBytes());
         String basicAuthHeader = "Basic " + encoded;
         ResponseEntity<?> response = restClient.put()
               .uri("/admin/upperLimit/{id}", 1)
               .header("Authorization", basicAuthHeader)
               .retrieve()
               .toBodilessEntity();

         assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
   }
}
