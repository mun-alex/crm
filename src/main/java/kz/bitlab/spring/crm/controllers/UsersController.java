package kz.bitlab.spring.crm.controllers;

import kz.bitlab.spring.crm.models.ApplicationRequest;

import kz.bitlab.spring.crm.models.Roles;
import kz.bitlab.spring.crm.models.Users;
import kz.bitlab.spring.crm.services.RolesService;
import kz.bitlab.spring.crm.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(Constants.API_USERS)
public class UsersController {

    @Autowired
    private ApplicationRequest applicationRequest;

    @Autowired
    private UsersService usersService;

    @Autowired
    private Users newUser;

    @Autowired
    private RolesService rolesService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("usersList", usersService.getAllUsers());
        model.addAttribute("currentUser",usersService.getUserData());
        model.addAttribute("newUser", newUser);
        return "users";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("currentUser", usersService.getUserData());
        return "/403";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        return "login";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("newUser", newUser);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(Model model,
                           @ModelAttribute(name = "newUser") Users newUser,
                           @RequestParam(name = "userRePassword") String userRePassword) {
        if(newUser.getPassword().equals(userRePassword)) {
            if(usersService.createUser(newUser) != null) {
                model.addAttribute("currentUser", usersService.getUserData());
                return "redirect:/users/login?regSuccess";
            } return "redirect:/users/register?emailError";
        } return "redirect:/users/register?repassError";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("currentUser", usersService.getUserData());
        return "profile";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUser(@PathVariable(name = "id") Long id, Model model) {

        Users editUser = usersService.getUsersById(id);
        List<Roles> rolesList = rolesService.getAllRoles();
        rolesList.removeAll(editUser.getRolesList());

        model.addAttribute("unassignedRoles", rolesList);
        model.addAttribute("newRequest", applicationRequest);
        model.addAttribute("editUser", editUser);
        model.addAttribute("currentUser",usersService.getUserData());

        return "editUser";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        usersService.deleteUsersById(id);
        return "redirect:" + Constants.API_USERS;
    }

    @PostMapping("/role-assignee")
    public String roleAssignee(@RequestParam(name = "user_id") Long userId,
                               @RequestParam(name = "role_id") Long roleId) {
        Users user = usersService.getUsersById(userId);
        List<Roles> roles = user.getRolesList();
        roles.add(rolesService.getRolesById(roleId));
        user.setRolesList(roles);
        usersService.saveUsers(user);
        return "redirect:/users/edit/" + userId;
    }

    @PostMapping("/role-unassignee")
    public String roleUnassignee(@RequestParam(name = "user_id") Long userId,
                                 @RequestParam(name = "role_id") Long roleId) {
        Users user = usersService.getUsersById(userId);
        List<Roles> userRoles = user.getRolesList();
        userRoles.remove(rolesService.getRolesById(roleId));
        user.setRolesList(userRoles);
        usersService.saveUsers(user);
        return "redirect:/users/edit/" + userId;
    }
}
