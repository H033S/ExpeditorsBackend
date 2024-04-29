package expeditors.backend.service;

import expeditors.backend.domain.Course;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseServiceWithRatingTest {

    @Autowired
    private CourseServiceWithRating courseService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetAddsRatingToCourse() {
        Course course = courseService.createCourse("Math-101", "Intro to Math");

        Course result = courseService.getCourse(course.getId());

        System.out.println("result: " + result);

        assertTrue(result.getCode().contains("Math-101"));
        assertTrue(result.getRating() > 0);
    }
}
