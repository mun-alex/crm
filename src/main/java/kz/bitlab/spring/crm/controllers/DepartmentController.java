package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.models.Department;
import kz.bitlab.spring.crm.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentController {

    @Autowired
    private ApplicationRequest applicationRequest;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private Department department;

    @GetMapping(value = "/departments")
    String getAllDepartments(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("newDepartment", department);
        model.addAttribute("departmentList", departmentRepository.findAll());
        return "departments";
    }

    @PostMapping(value = "/add-department")
    String addDepartment(Model model,
                         @ModelAttribute(name = "newDepartment") Department newDepartment) {
        model.addAttribute("newRequest", applicationRequest);
        departmentRepository.save(newDepartment);
        return "redirect:/departments";
    }

    @GetMapping(value = "/edit-department/{id}")
    String getEditDepartmentForm(Model model,
                                 @PathVariable(name = "id") Long id,
                                 @ModelAttribute(name = "editDepartment") Department editDepartment) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("editDepartment", departmentRepository.findById(id).orElseThrow());
        return "editDepartment";
    }

    @PostMapping(value = "/edit-department/{id}")
    String editDepartment(@PathVariable(name = "id") Long id,
                          @ModelAttribute(name = "editDepartment") Department editDepartment) {
        editDepartment.setId(id);
        departmentRepository.save(editDepartment);
        return "redirect:/departments";
    }

    @GetMapping(value = "/delete-department/{id}")
    String deleteDepartment(@PathVariable(name = "id") Long id) {
        departmentRepository.deleteById(id);
        return "redirect:/departments";
    }
}
