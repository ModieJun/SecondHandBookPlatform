package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.DB.BookRepository;
import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Security.SecurityService;
import com.junjie.bookplatform.Services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class HomeController {

    private final UserServiceImpl userService;
    private final SecurityService securityService;
    @Autowired
    private BookRepository bookRepository;

    public HomeController(UserServiceImpl userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }


    @RequestMapping(value = {"/"})
    public String Home() {
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
    public String registerUser(@ModelAttribute("newUser") User u, HttpServletRequest request) {
        if (userService.addUser(u)) {
            //autoLogin
            securityService.autoLogin(u.getUsername(), u.getPassword(), request);
            return "redirect:/";
        }
        return "registration";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @RequestMapping("/deleteUser")
    public String deleteSelf() {
        String u = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(u);
        userService.removeUser(user);
        return "redirect:/logout";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
