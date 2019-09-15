package com.junjie.bookplatform.Controllers;

import com.junjie.bookplatform.Model.User;
import com.junjie.bookplatform.Security.SecurityService;
import com.junjie.bookplatform.Services.UserServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class HomeController {

    private final UserServiceImpl userService;
    private final SecurityService securityService;

    public HomeController(UserServiceImpl userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }


    @RequestMapping(value = {"/"})
    public String Home() {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if (!authorities.contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return "redirect:/";
        }
        return"login";
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
    public String registerUser(@ModelAttribute("newUser")User u, HttpServletRequest request) {
        if (userService.addUser(u)) {
            //autoLogin
            securityService.autoLogin(u.getUsername(),u.getPassword(),request);
            return "redirect:/";
        }
        return "/register";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
