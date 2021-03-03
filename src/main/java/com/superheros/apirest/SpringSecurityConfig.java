package com.superheros.apirest;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Crear 2 usuarios para la demo.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");

    }

    // Seguridad de las rutas de apirest con autenticación básica.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP autenticación básica.
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/superHeroes/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/superHeroes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/superHeroes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/superHeroes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/superHeroes/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    	
    }

}