package org.ttl.ratingservice.controller.jconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails bobby = User.withUsername("bobby")
            .password("{bcrypt}$2a$10$kucTqWAMkoMR64gN7w2x6.ebcGYuSpVAkUa.Km2XtZD/BGhoA6byO")
            .roles("USER", "ADMIN")
            .build();

      UserDetails manoj = User.withUsername("manoj")
            .password("{bcrypt}$2a$10$xfJpC/Nw5HtziXcdpkej8.cIoPoN2cpOv6MyccXO/A4WNKkDn9iFm")
            .roles("USER")
            .build();

      var userDetailsService = new InMemoryUserDetailsManager(bobby, manoj);

      return userDetailsService;
   }

   @Bean
   public SecurityFilterChain courseRatingFilterChain(HttpSecurity http) throws Exception {

      var chain = http.authorizeHttpRequests(auth ->
                  auth.requestMatchers(HttpMethod.GET, "/rating/**", "/admin/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/admin/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
            )
            .httpBasic(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .build();

      return chain;
   }
}
