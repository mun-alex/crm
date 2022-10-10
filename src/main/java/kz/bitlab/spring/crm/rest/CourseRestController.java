package kz.bitlab.spring.crm.rest;

import kz.bitlab.spring.crm.controllers.Constants;
import kz.bitlab.spring.crm.models.Course;
import kz.bitlab.spring.crm.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.REST_API_BASE)
@RequiredArgsConstructor
public class CourseRestController {

    private final CourseService courseService;

    @GetMapping(value = "/courses")
    List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(value = "/courses/{id}")
    Course getCourseById(@PathVariable(name = "id") Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping(value = "/courses")
    void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @PutMapping(value = "/courses")
    void updateCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @DeleteMapping(value = "/courses/{id}")
    void deleteCourse(@PathVariable(name = "id") Long id) {
        courseService.deleteCourse(id);
    }
}
