package org.ttl.ratingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CourseRatingService {

   @Value("${ttl.courseRating.lowerLimit}")
   private double lowerLimit;

   @Value("${ttl.courseRating.upperLimit}")
   private double upperLimit;

   private int iVar;

   public void foo() {
      iVar = 10;

      upperLimit = 24.0;
   }

   public double getLowerLimit() {
      return lowerLimit;
   }


   public double getUpperLimit() {
      return upperLimit;
   }

   public void setBothLimits(double lowerLimit, double upperLimit) {
      if(lowerLimit < upperLimit) {
         this.lowerLimit   = lowerLimit;
         this.upperLimit   = upperLimit;
      }
   }

//   public void setLowerLimit(double lowerLimit) {
//      this.lowerLimit = lowerLimit;
//   }
//   public void setUpperLimit(double upperLimit) {
//      this.upperLimit = upperLimit;
//   }
}
