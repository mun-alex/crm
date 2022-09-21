package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping(value = "/")
    public String getIndex(Model model) {
        model.addAttribute("requestList", requestRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/add-request")
    public String getRequestForm(Model model) {
        model.addAttribute("newRequest", new ApplicationRequest());
        return "addRequest";
    }

    @PostMapping(value = "/add-request")
    public String addRequest(@ModelAttribute(name = "newRequest") ApplicationRequest newRequest) {
        newRequest.setHandled(false);
        requestRepository.save(newRequest);
        return "redirect:/";
    }

    @GetMapping(value = "/handle-request/{id}")
    public String getDetails(Model model, @PathVariable(name = "id") Long id) {
        ApplicationRequest request = requestRepository.findById(id).orElse(null);
        model.addAttribute("editRequest", request);
        return "handleRequest";
    }

    @PostMapping(value = "/handle-request/{id}")
    public String handleRequest(@PathVariable(name = "id") Long id) {
        ApplicationRequest request = requestRepository.findById(id).orElse(null);
        request.setHandled(true);
        requestRepository.save(request);
        return "redirect:/";
    }
}
