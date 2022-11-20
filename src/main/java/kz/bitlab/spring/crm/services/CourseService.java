package kz.bitlab.spring.crm.services;

import kz.bitlab.spring.crm.models.Course;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();
    Course getCourseById(Long id);
    void addCourse(Course course);
    void deleteCourse(Long id) throws DataIntegrityViolationException;
}
