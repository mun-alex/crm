package kz.bitlab.spring.crm.services;

import kz.bitlab.spring.crm.models.Roles;
import kz.bitlab.spring.crm.models.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {
    Users getUserByEmail(String email);
    Users getUserData();

    List<Users> getAllUsers();
    void saveUsers(Users users);
    Users getUsersById(Long id);
    void deleteUsersById(Long id);
    Users createUser(Users user);
}
