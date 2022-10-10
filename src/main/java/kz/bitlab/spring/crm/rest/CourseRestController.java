package kz.bitlab.spring.crm.rest;

import kz.bitlab.spring.crm.controllers.Constants;
import kz.bitlab.spring.crm.models.Course;
import kz.bitlab.spring.crm.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.REST_API_BASE + "/courses")
@RequiredArgsConstructor
public class CourseRestController {

    private final CourseService courseService;

    @GetMapping
    List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(value = "/{id}")
    Course getCourseById(@PathVariable(name = "id") Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @PutMapping
    void updateCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @DeleteMapping(value = "/{id}")
    void deleteCourse(@PathVariable(name = "id") Long id) {
        courseService.deleteCourse(id);
    }
}
