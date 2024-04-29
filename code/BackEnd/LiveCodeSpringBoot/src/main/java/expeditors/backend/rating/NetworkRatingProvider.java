package expeditors.backend.rating;

import expeditors.backend.domain.Course;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Profile("networkrating")
public class NetworkRatingProvider implements RatingProvider {

   private int ratingLowerLimit = 1;
   private int ratingUpperLimit = 10;

   private RestClient restClient;

   private String ratingUrl;

   public NetworkRatingProvider(RestTemplateBuilder restTemplateBuilder) {
      var baseUrl = "http://localhost:10001";
      var rootUrl = baseUrl + "/courseRating";
      ratingUrl = rootUrl + "/{id}";

      this.restClient = RestClient.builder(restTemplateBuilder.build())
            .baseUrl(baseUrl)
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
   }

   @Override
   public void addRatingToCourse(Course course) {
      ResponseEntity<Integer> result =  restClient.get()
            .uri(ratingUrl, 1)
            .retrieve()
            .toEntity(Integer.class);


      int rating = result.getBody().intValue();

      course.setRating(rating);
   }
}
