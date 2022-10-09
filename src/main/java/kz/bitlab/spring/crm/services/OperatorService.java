package kz.bitlab.spring.crm.services;

import kz.bitlab.spring.crm.models.Operator;

import java.util.List;

public interface OperatorService {
    List<Operator> getAllOperators();
    Operator getOperatorById(Long id);
    void addOperator(Operator operator);
    void deleteOperator(Long id);
}
