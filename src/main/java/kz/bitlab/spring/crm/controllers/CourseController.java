package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.models.Course;
import kz.bitlab.spring.crm.services.CourseService;
import kz.bitlab.spring.crm.services.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping(Constants.API_COURSES)
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ApplicationRequest applicationRequest;

    @Autowired
    private UsersService usersService;

    @Autowired
    private Course course;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String getAllCourses(Model model) {
        log.debug("CourseController getAllCourses");
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("newCourse", course);
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("currentUser", usersService.getUserData());
        return "courses";
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String addCourse(@ModelAttribute(name = "newCourse") Course newCourse) {
        log.info("CourseController addCourse");
        courseService.addCourse(newCourse);
        return "redirect:" + Constants.API_COURSES;
    }

    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String getEditCourseForm(Model model,
                      @PathVariable(name = "id") Long id) {
        log.info("CourseController getEditCourseForm, courseId:" + id);
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("editCourse", courseService.getCourseById(id));
        model.addAttribute("currentUser", usersService.getUserData());
        return "editCourse";
    }

    @PostMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String editCourse(@ModelAttribute(name = "editCourse") Course editCourse,
                      @PathVariable(name = "id") Long id) {
        log.info("CourseController editCourse, courseId:" + id);
        editCourse.setId(id);
        courseService.addCourse(editCourse);
        return "redirect:" + Constants.API_COURSES;
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR')")
    String deleteCourse(@PathVariable(name = "id") Long id) {
        log.info("CourseController deleteCourse, courseId:" + id);
        try {
            courseService.deleteCourse(id);
            return "redirect:" + Constants.API_COURSES;
        } catch (DataIntegrityViolationException e) {
            return "redirect:" + Constants.API_COURSES + "?error";
        }
    }
}
