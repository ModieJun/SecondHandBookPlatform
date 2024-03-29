package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.DB.RoleRepository;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Security.SecurityService;
import com.junjie.bookplatform.Services.FilterService;
import com.junjie.bookplatform.Services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Controller
public class HomeController {

    private final UserServiceImpl userService;
    private final SecurityService securityService;
    private final Validator uservalidator;
    private final FilterService filterService;
    private final RoleRepository roleRepository;

    public HomeController(UserServiceImpl userService, SecurityService securityService, @Qualifier("userValidator") Validator userValidator, FilterService filterService, RoleRepository roleRepository) {
        this.userService = userService;
        this.securityService = securityService;
        this.uservalidator = userValidator;
        this.filterService = filterService;
        this.roleRepository = roleRepository;
    }


    @RequestMapping(value = {"/"})
    public String Home(Model model) {
        model.addAttribute("recentBooks",filterService.getAllRecent() );
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (!authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping(value = "/register")
    public String register(Model m) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (!authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return "redirect:/";
        }
        m.addAttribute("newUser", new User());
        return "registration";
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute("newUser") User u, HttpServletRequest request, BindingResult result) {
        //Validation
        this.uservalidator.validate(u, result);
        if (result.hasErrors()) {
            return "registration";
        }

        u.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));

        userService.addUser(u);
        //autoLogin
        securityService.autoLogin(u.getUsername(), u.getPassword(), request);
        return "redirect:/profile/addcontact";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @RequestMapping("/deleteUser")
    public String deleteSelf(@AuthenticationPrincipal Principal principal) {
        String u = principal.getName();
        User user = userService.getUser(u);
        userService.removeUser(user);
        return "redirect:/logout";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

}
