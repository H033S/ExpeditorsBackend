package expeditors.backend.rating;

import expeditors.backend.domain.Course;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("inmemrating")
public class InMemoryRatingProvider implements RatingProvider {

   private int ratingLowerLimit = 1;
   private int ratingUpperLimit = 10;

   @Override
   public void addRatingToCourse(Course course) {
      int rating = ThreadLocalRandom.current().nextInt(1, 10);
      course.setRating(rating);
   }
}
