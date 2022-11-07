package kz.bitlab.spring.crm.services.impl;

import kz.bitlab.spring.crm.models.Course;
import kz.bitlab.spring.crm.repository.CourseRepository;
import kz.bitlab.spring.crm.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) throws DataIntegrityViolationException {
        courseRepository.deleteById(id);
    }
}
