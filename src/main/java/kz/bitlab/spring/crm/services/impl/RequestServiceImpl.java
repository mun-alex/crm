package kz.bitlab.spring.crm.services.impl;

import kz.bitlab.spring.crm.models.ApplicationRequest;
import kz.bitlab.spring.crm.repository.RequestRepository;
import kz.bitlab.spring.crm.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<ApplicationRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public List<ApplicationRequest> getAllRequestsByHandledFalse() {
        return requestRepository.findAllByHandledFalse();
    }

    @Override
    public List<ApplicationRequest> getAllRequestsByHandledTrue() {
        return requestRepository.findAllByHandledTrue();
    }

    @Override
    public ApplicationRequest getRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow();
    }

    @Override
    public void addRequest(ApplicationRequest request) {
        requestRepository.save(request);
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}
