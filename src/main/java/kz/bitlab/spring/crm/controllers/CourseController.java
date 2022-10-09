package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.models.Course;
import kz.bitlab.spring.crm.repository.CourseRepository;
import kz.bitlab.spring.crm.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(Constants.API_COURSES)
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ApplicationRequest applicationRequest;

    @Autowired
    private UsersService usersService;

    @Autowired
    private Course course;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String getAllCourses(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("newCourse", course);
        model.addAttribute("courseList", courseRepository.findAll());
        model.addAttribute("currentUser", usersService.getUserData());
        return "courses";
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String addCourse(@ModelAttribute(name = "newCourse") Course newCourse) {
        courseRepository.save(newCourse);
        return "redirect:" + Constants.API_COURSES;
    }

    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String getEditCourseForm(Model model,
                      @PathVariable(name = "id") Long id) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("editCourse", courseRepository.findById(id).orElseThrow());
        model.addAttribute("currentUser", usersService.getUserData());
        return "editCourse";
    }

    @PostMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String editCourse(@ModelAttribute(name = "editCourse") Course editCourse,
                      @PathVariable(name = "id") Long id) {
        editCourse.setId(id);
        courseRepository.save(editCourse);
        return "redirect:" + Constants.API_COURSES;
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String deleteCourse(@PathVariable(name = "id") Long id) {
        courseRepository.deleteById(id);
        return "redirect:" + Constants.API_COURSES;
    }
}
