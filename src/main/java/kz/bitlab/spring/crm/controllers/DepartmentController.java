package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.models.Department;
import kz.bitlab.spring.crm.services.DepartmentService;
import kz.bitlab.spring.crm.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = Constants.API_DEPARTMENTS)
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ApplicationRequest applicationRequest;
    @Autowired
    private Department department;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    String getAllDepartments(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("newDepartment", department);
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        model.addAttribute("currentUser", usersService.getUserData());
        return "departments";
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    String addDepartment(Model model,
                         @ModelAttribute(name = "newDepartment") Department newDepartment) {
        model.addAttribute("newRequest", applicationRequest);
        departmentService.addDepartment(newDepartment);
        return "redirect:" + Constants.API_DEPARTMENTS;
    }

    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    String getEditDepartmentForm(Model model,
                                 @PathVariable(name = "id") Long id,
                                 @ModelAttribute(name = "editDepartment") Department editDepartment) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("editDepartment", departmentService.getDepartmentById(id));
        model.addAttribute("currentUser", usersService.getUserData());
        return "editDepartment";
    }

    @PostMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    String editDepartment(@PathVariable(name = "id") Long id,
                          @ModelAttribute(name = "editDepartment") Department editDepartment) {
        editDepartment.setId(id);
        departmentService.addDepartment(editDepartment);
        return "redirect:" + Constants.API_DEPARTMENTS;
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    String deleteDepartment(@PathVariable(name = "id") Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:" + Constants.API_DEPARTMENTS;
    }
}
