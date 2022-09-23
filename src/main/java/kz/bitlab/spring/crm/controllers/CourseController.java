package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.models.Course;
import kz.bitlab.spring.crm.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ApplicationRequest applicationRequest;

    @GetMapping(value = "/courses")
    String getAllCourses(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("courseList", courseRepository.findAll());
        return "courses";
    }

    @PostMapping(value = "/add-course")
    String addCourse(@ModelAttribute(name = "newCourse") Course newCourse) {
        courseRepository.save(newCourse);
        return "redirect: /courses";
    }

    @GetMapping(value = "/edit-course/{id}")
    String getEditCourseForm(Model model,
                      @PathVariable(name = "id") Long id) {
        model.addAttribute("editCourse", courseRepository.findById(id).orElseThrow());
        return "editCourse";
    }

    @PostMapping(value = "/edit-course/{id}")
    String editCourse(@ModelAttribute(name = "editCourse") Course editCourse,
                      @PathVariable(name = "id") Long id) {
        editCourse.setId(id);
        courseRepository.save(editCourse);
        return "courses";
    }

    @GetMapping(value = "/delete-course/{id}")
    String deleteCourse(@PathVariable(name = "id") Long id) {
        courseRepository.deleteById(id);
        return "courses";
    }
}
