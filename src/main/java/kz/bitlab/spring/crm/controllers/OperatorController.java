package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.models.Operator;
import kz.bitlab.spring.crm.repository.DepartmentRepository;
import kz.bitlab.spring.crm.repository.OperatorRepository;
import kz.bitlab.spring.crm.repository.RequestRepository;
import kz.bitlab.spring.crm.services.DepartmentService;
import kz.bitlab.spring.crm.services.OperatorService;
import kz.bitlab.spring.crm.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = Constants.API_OPERATORS)
public class OperatorController {

    @Autowired
    private OperatorService operatorService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ApplicationRequest applicationRequest;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private Operator operator;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    String getAllOperators(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("newOperator", operator);
        model.addAttribute("operatorList", operatorService.getAllOperators());
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        model.addAttribute("currentUser", usersService.getUserData());
        return "operators";
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    String addOperator(@ModelAttribute(name = "newOperator") Operator newOperator) {
        operatorService.addOperator(newOperator);
        return "redirect:" + Constants.API_OPERATORS;
    }

    @GetMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    String getEditCourseForm(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("departmentList", departmentService.getAllDepartments());
        model.addAttribute("editOperator", operatorService.getOperatorById(id));
        model.addAttribute("currentUser", usersService.getUserData());
        return "editOperator";
    }

    @PostMapping(value = "/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    String editCourse(@ModelAttribute(name = "editOperator") Operator editOperator,
                      @PathVariable(name = "id") Long id) {
        editOperator.setId(id);
        operatorService.addOperator(editOperator);
        return "redirect:" + Constants.API_OPERATORS;
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    String deleteCourse(@PathVariable(name = "id") Long id) {
        operatorService.deleteOperator(id);
        return "redirect:" + Constants.API_OPERATORS;
    }

    @PostMapping(value = "/assignee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    String assigneeOperator(@RequestParam(name = "request_id") Long requestId,
                            @RequestParam(name = "operator_id") Long operatorId) {
        ApplicationRequest request = requestRepository.findById(requestId).orElseThrow();
        if (request != null) {
            Operator assigneeOperator = operatorService.getOperatorById(operatorId);
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

    @PostMapping(value = "/unassignee")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERVISOR')")
    String unassigneeOperator(@RequestParam(name = "request_id") Long requestId,
                            @RequestParam(name = "operator_id") Long operatorId) {
        ApplicationRequest request = requestRepository.findById(requestId).orElseThrow();
        if (request != null) {
            Operator assigneeOperator = operatorService.getOperatorById(operatorId);
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
