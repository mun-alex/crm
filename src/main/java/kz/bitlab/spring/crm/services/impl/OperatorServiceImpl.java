package kz.bitlab.spring.crm.services.impl;

import kz.bitlab.spring.crm.models.Operator;
import kz.bitlab.spring.crm.repository.OperatorRepository;
import kz.bitlab.spring.crm.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();
    }

    @Override
    public Operator getOperatorById(Long id) {
        return operatorRepository.findById(id).orElseThrow();
    }

    @Override
    public void addOperator(Operator operator) {
        operatorRepository.save(operator);
    }

    @Override
    public void deleteOperator(Long id) {
        operatorRepository.deleteById(id);
    }
}
