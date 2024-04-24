package org.ttl.courserating.controller;

import java.util.concurrent.ThreadLocalRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ttl.courserating.service.CourseRatingService;

@RestController
@RequestMapping("/courseRating")
public class CourseRatingController {

   private Logger logger = LoggerFactory.getLogger(this.getClass().getPackageName());

   @Autowired
   private CourseRatingService courseRatingService;

   @GetMapping("/{id}")
   public int getCourseRating(@PathVariable("id") int id) {
      int rating = courseRatingService.getCourseRating(id);

      logger.debug("got Rating of " + rating);

      return rating;

   }
}
