package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.models.Operator;
import kz.bitlab.spring.crm.services.CourseService;
import kz.bitlab.spring.crm.services.OperatorService;
import kz.bitlab.spring.crm.services.RequestService;
import kz.bitlab.spring.crm.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private RequestService requestService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ApplicationRequest applicationRequest;

    @GetMapping(value = "/")
    public String getIndex(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("requestList", requestService.getAllRequests());
        model.addAttribute("currentUser", usersService.getUserData());
        return "index";
    }

    @GetMapping(value = "/unhandled-requests")
    public String getUnhandledRequests(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("requestList", requestService.getAllRequestsByHandledFalse());
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("courseList", courseService.getAllCourses());
        return "index";
    }

    @GetMapping(value = "/handled-requests")
    public String getHandledRequests(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("requestList", requestService.getAllRequestsByHandledTrue());
        model.addAttribute("currentUser", usersService.getUserData());
        model.addAttribute("courseList", courseService.getAllCourses());
        return "index";
    }

    @PostMapping(value = "/add-request")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR', 'ROLE_OPERATOR')")
    public String addRequest(@ModelAttribute(name = "newRequest") ApplicationRequest newRequest) {
        newRequest.setHandled(false);
        requestService.addRequest(newRequest);
        return "redirect:/";
    }

    @GetMapping(value = "/handle-request/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR', 'ROLE_OPERATOR')")
    public String getDetails(Model model, @PathVariable(name = "id") Long id) {
        ApplicationRequest request = requestService.getRequestById(id);
        model.addAttribute("editRequest", request);
        model.addAttribute("newRequest", applicationRequest);

        List<Operator> operatorList = operatorService.getAllOperators();
        operatorList.removeAll(request.getOperatorList());
        model.addAttribute("operatorList", operatorList);

        model.addAttribute("operatorListUnassigned", request.getOperatorList());
        model.addAttribute("currentUser", usersService.getUserData());
        return "handleRequest";
    }

    @PostMapping(value = "/handle-request/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR', 'ROLE_OPERATOR')")
    public String handleRequest(@PathVariable(name = "id") Long id) {

        ApplicationRequest request = requestService.getRequestById(id);
        request.setHandled(true);
        requestService.addRequest(request);
        return "redirect:/";
    }

    @GetMapping(value = "/delete-request/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPERVISOR', 'ROLE_OPERATOR')")
    public String deleteRequest(@PathVariable(name = "id") Long id) {
        requestService.deleteRequest(id);
        return "redirect:/";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("currentUser", usersService.getUserData());
        return "/403";
    }
}
