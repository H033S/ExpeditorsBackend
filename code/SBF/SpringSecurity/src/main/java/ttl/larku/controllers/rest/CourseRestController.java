package ttl.larku.controllers.rest;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttl.larku.domain.Course;
import ttl.larku.service.CourseService;

@RestController
@RequestMapping("/adminrest/course")
public class CourseRestController {

    private final BeanHelpers uriCreator;
    private CourseService courseService;

    //Constructor injection.
    public CourseRestController(CourseService courseService, BeanHelpers uriCreator) {
        this.courseService = courseService;
        this.uriCreator = uriCreator;
    }

    @GetMapping
    public ResponseEntity<?> getCourses() {
        List<Course> course = courseService.getAllCourses();
        return ResponseEntity.ok(RestResultWrapper.ofValue(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable("id") int id) {
        Course c = courseService.getCourse(id);
        if (c == null) {
            return ResponseEntity.badRequest().body(RestResultWrapper.ofError("Course with id: " + id + " not found"));
        }
        return ResponseEntity.ok(RestResultWrapper.ofValue(c));
    }


    @GetMapping("/code/{courseCode}")
    public ResponseEntity<?> getCourseByCode(@PathVariable("courseCode") String courseCode) {
        Course c = courseService.findByCode(courseCode);
        if (c == null) {
            return ResponseEntity.badRequest().body(RestResultWrapper.ofError("Course with code: " + courseCode + " not found"));
        }
        return ResponseEntity.ok(RestResultWrapper.ofValue(c));
    }

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.createCourse(course);
        URI uri = uriCreator.getUriFor(newCourse.getId());

        return ResponseEntity.created(uri).body(RestResultWrapper.ofValue(newCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
        Course c = courseService.getCourse(id);
        if (c == null) {
            RestResultWrapper<Course> rr = RestResultWrapper.ofError("Course with id " + id + " not found");
            return ResponseEntity.badRequest().body(rr);
        }
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateCourse(@RequestBody Course course) {
        int id = course.getId();
        Course s = courseService.getCourse(id);
        if (s == null) {
            RestResultWrapper<Course> rr = RestResultWrapper.ofError("Course with id " + id + " not found");
            return ResponseEntity.badRequest().body(rr);
        }
        courseService.updateCourse(course);
        return ResponseEntity.noContent().build();
    }
}