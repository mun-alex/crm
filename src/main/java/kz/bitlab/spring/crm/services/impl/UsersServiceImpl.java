package kz.bitlab.spring.crm.services.impl;

import kz.bitlab.spring.crm.models.Roles;
import kz.bitlab.spring.crm.models.Users;
import kz.bitlab.spring.crm.repository.RolesRepository;
import kz.bitlab.spring.crm.repository.UsersRepository;
import kz.bitlab.spring.crm.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users myUser = usersRepository.findByEmail(s);
        if (myUser != null) {
            User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRolesList());
            return secUser;
        }
        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public Users getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User secUser = (User) authentication.getPrincipal();
            Users myUser = getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public void saveUsers(Users users) {
        usersRepository.save(users);
    }

    @Override
    public Users getUsersById(Long id) {
        return usersRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteUsersById(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public Users createUser(Users user) {
        Users checkUser = usersRepository.findByEmail(user.getEmail());
        if (checkUser == null) {
            Roles role = rolesRepository.findByRole("ROLE_USER");
            if (role != null) {
                List<Roles> roles = new ArrayList<>();
                roles.add(role);
                user.setRolesList(roles);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return usersRepository.save(user);
            }
        }
        return null;
    }
}
