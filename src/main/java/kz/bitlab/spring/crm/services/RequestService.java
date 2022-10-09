package kz.bitlab.spring.crm.services;

import kz.bitlab.spring.crm.models.ApplicationRequest;

import java.util.List;

public interface RequestService {
    List<ApplicationRequest> getAllRequests();
    List<ApplicationRequest> getAllRequestsByHandledFalse();
    List<ApplicationRequest> getAllRequestsByHandledTrue();
    ApplicationRequest getRequestById(Long id);
    void addRequest(ApplicationRequest request);
    void deleteRequest(Long id);
}
