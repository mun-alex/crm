package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.models.Course;
import kz.bitlab.spring.crm.models.Department;
import kz.bitlab.spring.crm.models.Operator;
import kz.bitlab.spring.crm.repository.CourseRepository;
import kz.bitlab.spring.crm.repository.DepartmentRepository;
import kz.bitlab.spring.crm.repository.OperatorRepository;
import kz.bitlab.spring.crm.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OperatorController {
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private ApplicationRequest applicationRequest;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private Operator operator;

    @GetMapping(value = "/operators")
    String getAllOperators(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("newOperator", operator);
        model.addAttribute("operatorList", operatorRepository.findAll());
        model.addAttribute("departmentList", departmentRepository.findAll());
        return "operators";
    }

    @PostMapping(value = "/add-operator")
    String addOperator(@ModelAttribute(name = "newOperator") Operator newOperator) {
        operatorRepository.save(newOperator);
        return "redirect:/operators";
    }

    @GetMapping(value = "/edit-operator/{id}")
    String getEditCourseForm(Model model,
                             @PathVariable(name = "id") Long id) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("departmentList", departmentRepository.findAll());
        model.addAttribute("editOperator", operatorRepository.findById(id).orElseThrow());
        return "editOperator";
    }

    @PostMapping(value = "/edit-operator/{id}")
    String editCourse(@ModelAttribute(name = "editOperator") Operator editOperator,
                      @PathVariable(name = "id") Long id) {
        editOperator.setId(id);
        operatorRepository.save(editOperator);
        return "redirect:/operators";
    }

    @GetMapping(value = "/delete-operator/{id}")
    String deleteCourse(@PathVariable(name = "id") Long id) {
        operatorRepository.deleteById(id);
        return "redirect:/operators";
    }

    @PostMapping(value = "/operator-assignee")
    String assigneeOperator(@RequestParam(name = "request_id") Long requestId,
                            @RequestParam(name = "operator_id") Long operatorId) {
        ApplicationRequest request = requestRepository.findById(requestId).orElseThrow();
        if (request != null) {
            Operator assigneeOperator = operatorRepository.findById(operatorId).orElseThrow();
            if (assigneeOperator != null) {
                List<Operator> operatorList = request.getOperatorList();
                if (operatorList == null) operatorList = new ArrayList<>();
                operatorList.add(assigneeOperator);
                request.setOperatorList(operatorList);
                requestRepository.save(request);
                return "redirect:/handle-request/" + requestId;
            }
        }
        return "redirect:/";
    }

    @PostMapping(value = "/operator-unassignee")
    String unassigneeOperator(@RequestParam(name = "request_id") Long requestId,
                            @RequestParam(name = "operator_id") Long operatorId) {
        ApplicationRequest request = requestRepository.findById(requestId).orElseThrow();
        if (request != null) {
            Operator assigneeOperator = operatorRepository.findById(operatorId).orElseThrow();
            if (assigneeOperator != null) {
                List<Operator> operatorList = request.getOperatorList();
                if (operatorList == null) operatorList = new ArrayList<>();
                operatorList.remove(assigneeOperator);
                request.setOperatorList(operatorList);
                requestRepository.save(request);
                return "redirect:/handle-request/" + requestId;
            }
        }
        return "redirect:/";
    }
}
