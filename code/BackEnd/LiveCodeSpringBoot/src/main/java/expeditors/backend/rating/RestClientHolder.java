package expeditors.backend.rating;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientHolder {
   private RestClient restClient;

   private String ratingUrl;

   public RestClientHolder() {
      var baseUrl = "http://localhost:10001";
      var rootUrl = "/courseRating";
      ratingUrl = rootUrl + "/{id}";

      this.restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
   }

   public int getRating(int id) {
      ResponseEntity<Integer> response = restClient.get()
            .uri(ratingUrl, id)
            .retrieve()
            .toEntity(Integer.class);


      Integer result = response.getBody();
      return result != null ? result : 0;
   }
}
