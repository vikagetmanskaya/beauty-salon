package com.example.diploma.config;

import com.example.diploma.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET,"/home").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET,"/services/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/services").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/services/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/masters/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/masters/update/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH,"/masters/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/masters").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/masters/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/records/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/records").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/records/delete/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/files/{id}/new").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/files/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/files/delete/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/registration/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/registration/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH,"/users/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/records/{id}").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/comments").hasRole("USER")
                .antMatchers(HttpMethod.DELETE,"/comments/delete/{id}").hasRole("USER")
                .antMatchers("/cart/**").hasRole("USER")
                .and().formLogin().failureHandler(getAuthenticationFailureHandler()).loginPage("/login").defaultSuccessUrl("/home").
                usernameParameter("userName").passwordParameter("password").permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }
    @Bean
    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return new MyAuthenticationFailureHandler();
    }

}
