package kz.bitlab.spring.crm.repository;

import kz.bitlab.spring.crm.models.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
