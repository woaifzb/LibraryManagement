package com.library.manage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {
    private final Environment env;

    public WebSecurityConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                // 为了简单起见，在本例中禁用CSRF保护
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(request -> {
                                    String path = request.getRequestURI();
                                    // 允许访问GET /BooksAPI而不需要身份验证
                              //      return path.equals("/BooksAPI") || path.equals("/BooksAPI/**") && request.getMethod().equals("GET");
                                return false;
                                }).permitAll()
                                // 要求对所有其他请求进行身份验证
                                .anyRequest().authenticated()
                )
                .httpBasic(); // 使用HTTP基本身份验证
                http.csrf().disable() ;
                http.cors();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        String username = env.getProperty("spring.security.user.name");
        String password = env.getProperty("spring.security.user.password");
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username(username)
                        .password(password)
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}