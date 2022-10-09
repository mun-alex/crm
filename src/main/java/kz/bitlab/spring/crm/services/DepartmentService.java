package kz.bitlab.spring.crm.services;

import kz.bitlab.spring.crm.models.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    void addDepartment(Department department);
    void deleteDepartment(Long id);
}
