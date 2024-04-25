package org.ttl.courserating.service;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CourseRatingService {

   public int getCourseRating(int id) {
      int rating = ThreadLocalRandom.current().nextInt(10, 20);

      return rating;
   }
}
