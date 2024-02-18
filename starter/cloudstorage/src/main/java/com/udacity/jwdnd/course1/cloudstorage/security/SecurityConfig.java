package com.udacity.jwdnd.course1.cloudstorage.security;


import com.udacity.jwdnd.course1.cloudstorage.services.impl.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationService authenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/signup", "/login", "/css/**", "/js/**").permitAll().anyRequest().authenticated();

        http.formLogin().loginPage("/login").permitAll();

        http.formLogin().defaultSuccessUrl("/home", true);

        http.logout().logoutSuccessUrl("/login?logout").permitAll();
    }
}

