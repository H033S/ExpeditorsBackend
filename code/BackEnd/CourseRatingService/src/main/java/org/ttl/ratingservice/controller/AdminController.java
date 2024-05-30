package org.ttl.ratingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.ttl.ratingservice.service.CourseRatingService;

@RestController
@RequestMapping("/admin")
public class AdminController {

   @Autowired
   private CourseRatingService service;

   @GetMapping("/lowerLimit")
   public double getLowerLimit() {
      return service.getLowerLimit();
   }

   @GetMapping("/upperLimit")
   public double getUpperLimit() {
      return service.getUpperLimit();
   }

   @GetMapping("/bothLimits")
   public String getBothLimits() {
      return service.getLowerLimit() + ":" + service.getUpperLimit();
   }

   @PutMapping("/lowerLimit/{ll}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void setLowerLimit(@PathVariable("ll") double lowerLimit) {
      service.setLowerLimit(lowerLimit);
   }


   @PutMapping("/upperLimit/{ul}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void setUpperLimit(@PathVariable("ul") double upperLimit) {
      service.setUpperLimit(upperLimit);
   }
}
