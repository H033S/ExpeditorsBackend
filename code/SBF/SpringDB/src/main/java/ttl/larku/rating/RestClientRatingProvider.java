package ttl.larku.rating;

import java.math.BigDecimal;
import java.util.Base64;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Profile("networkrating")
public class RestClientRatingProvider implements RatingProvider {

   private RestClient restClient;


   public RestClientRatingProvider() {
      var baseUrl = "http://localhost:10001/rating";
      this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
   }

   @Override
   public BigDecimal getRating(int id) {
      return getRatingFromService(id, null);
   }

   @Override
   public BigDecimal getRating(int id, String user, String pw) {
      var encoded = Base64.getEncoder().encodeToString((user + ":" + pw).getBytes());
      String basicAuthHeader = "Basic " + encoded;
      var result = getRatingFromService(id, basicAuthHeader);
      return result;
   }

   private BigDecimal getRatingFromService(int id, String authHeader) {
      var spec = restClient.get()
            .uri("/{id}", id);
      if (authHeader != null) {
         spec = spec.header("Authorization", authHeader);
      }
      var response = spec.retrieve()
            .toEntity(BigDecimal.class);

      if (response.getStatusCode() == HttpStatus.OK) {
         var rating = response.getBody();
         return rating;
      }
      return null;
   }
}
