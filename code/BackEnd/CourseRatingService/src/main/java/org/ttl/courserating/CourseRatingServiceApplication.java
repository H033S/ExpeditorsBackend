package org.ttl.courserating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.ttl.courserating.controller.CourseRatingController;

@SpringBootApplication
public class CourseRatingServiceApplication {

   public static void main(String[] args) {
      SpringApplication.run(CourseRatingServiceApplication.class, args);
   }
}