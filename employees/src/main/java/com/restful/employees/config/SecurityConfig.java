package com.restful.employees.config;

import jdk.jfr.ContentType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

//    @Bean
//    public UserDetailsManager userDetailsManagerInMemory(){
//        UserDetails wajiha = User.builder().username("wajiha").password("{noop}12345")
//                .roles("EMPLOYEE").build();
//        UserDetails usama = User.builder().username("usama").password("{noop}12345")
//                .roles("EMPLOYEE", "MANAGER").build();
//        UserDetails faiza = User.builder().username("faiza").password("{noop}12345")
//                .roles("EMPLOYEE", "MANAGER", "ADMIN").build();
//        return new InMemoryUserDetailsManager(wajiha, usama, faiza);
//    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity httpSecurity){
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer.requestMatchers(HttpMethod.GET,"/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui/**","/swagger-ui.html",
                                                "/v3/api-docs/**","/docs/**").permitAll()
                        .requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/api/employees/getEmployeeById/[0-9]+")).hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST,"/api/employees/addEmployee").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/api/employees/updateEmployee/**" ).hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/deleteEmployee/**").hasRole("ADMIN")
        );

        //basic auth : user and password are base64 encoded
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.exceptionHandling(exceptionHandlingConfigurer ->
        {
            exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint());
        });

        httpSecurity.headers(headersConfigurer ->
                headersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable())
        );

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){

        return ((request, response, authException) ->
        {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.setHeader("WWW-Authenticate", "");
            response.getWriter().write("\"error\" : \"Unauthorized access\"");
        });
    }

}
