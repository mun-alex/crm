package kz.bitlab.spring.crm.repository;

import kz.bitlab.spring.crm.models.Roles;
import kz.bitlab.spring.crm.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}
