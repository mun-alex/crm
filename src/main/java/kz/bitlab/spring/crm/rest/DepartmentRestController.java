package kz.bitlab.spring.crm.rest;

import kz.bitlab.spring.crm.controllers.Constants;
import kz.bitlab.spring.crm.models.Department;
import kz.bitlab.spring.crm.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constants.REST_API_BASE + "/departments")
public class DepartmentRestController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping(value = "/{id}")
    public Department getDepartmentById(@PathVariable (name = "id") Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public void saveDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);
    }

    @PutMapping
    public void updateDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteDepartment(@PathVariable (name = "id") Long id) {
        departmentService.deleteDepartment(id);
    }
}
