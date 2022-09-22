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
    @Autowired
    private ApplicationRequest applicationRequest;

    @GetMapping(value = "/")
    public String getIndex(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("requestList", requestRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/unhandled-requests")
    public String getUnhandledRequests(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("requestList", requestRepository.findAllByHandledFalse());
        return "index";
    }

    @GetMapping(value = "/handled-requests")
    public String getHandledRequests(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("requestList", requestRepository.findAllByHandledTrue());
        return "index";
    }

    @PostMapping(value = "/add-request")
    public String addRequest(@ModelAttribute(name = "newRequest") ApplicationRequest newRequest) {
        newRequest.setHandled(false);
        requestRepository.save(newRequest);
        return "redirect:/";
    }

    @GetMapping(value = "/handle-request/{id}")
    public String getDetails(Model model, @PathVariable(name = "id") Long id) {
        Optional<ApplicationRequest> optional = requestRepository.findById(id);
        optional.ifPresent(request -> {
            model.addAttribute("editRequest", request);
        });
        model.addAttribute("newRequest", applicationRequest);
        return "handleRequest";
    }

    @PostMapping(value = "/handle-request/{id}")
    public String handleRequest(@PathVariable(name = "id") Long id) {

        Optional<ApplicationRequest> optional = requestRepository.findById(id);
        optional.ifPresent(request -> {
            request.setHandled(true);
            requestRepository.save(request);
        });
        return "redirect:/";
    }

    @GetMapping(value = "/delete-request/{id}")
    public String deleteRequest(Model model,
                                @PathVariable(name = "id") Long id) {
        Optional<ApplicationRequest> optional = requestRepository.findById(id);
        optional.ifPresent(request -> {
            requestRepository.deleteById(id);
        });
        return "redirect:/";
    }
}
