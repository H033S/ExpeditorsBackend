package org.ttl.ratingservice.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating")
public class CourseRatingServiceController {

   private double lowerLimit = 1.0;
   private double upperLimit  = 10.0;

   @GetMapping("/{id}")
   public BigDecimal getRatingForCourse(@PathVariable("id") int id) {
      var rd = ThreadLocalRandom.current().nextDouble(lowerLimit, upperLimit);
      var rating = new BigDecimal(String.valueOf(rd)).setScale(1, RoundingMode.CEILING);

      return rating;
   }
}
