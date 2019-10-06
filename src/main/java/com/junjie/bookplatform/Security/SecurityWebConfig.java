package com.junjie.bookplatform.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final DataSource dataSource;

    public SecurityWebConfig(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        //service taking to DAO
        provider.setUserDetailsService(userDetailsService());

//       password Encoder
        provider.setPasswordEncoder(getPasswordEncoder());

        return provider;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        final JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource( dataSource );
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    /*
    TODO :
        Still need to Add User authentication for adding and deleting Books
          3. Add Form Validators
//        1.  Need to implement buy Book


     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/resources/**","/public/**", "/h2-console/**", "/console/**").permitAll()
                .and()
                    .authorizeRequests().antMatchers("/","/register","/books/","/books/image/**","/books/search/**").permitAll()
                .and()
                    .authorizeRequests().antMatchers("/books/add", "/books/remove","/profile/**").access("hasRole('ROLE_USER')")
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").permitAll()
                .and()
                    .rememberMe().tokenRepository(persistentTokenRepository())
                .and()
                    .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();

        http.csrf().disable();
//        http.headers().frameOptions().disable();
    }


}
