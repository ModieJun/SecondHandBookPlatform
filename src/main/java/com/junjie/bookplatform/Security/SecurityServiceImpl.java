package com.junjie.bookplatform.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private final UserDetailsService service;

    public SecurityServiceImpl(@Qualifier("customUserDetailsService") UserDetailsService service) {
        this.service = service;
    }

    @Override
    public void autoLogin(String username, String password , HttpServletRequest request) {
        try {
            UserDetails userDetails = service.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            token.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(token);
            logger.warn("Token: " + token.isAuthenticated());
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());
        } catch (UsernameNotFoundException e) {
            e.getStackTrace();
        }

    }
}
