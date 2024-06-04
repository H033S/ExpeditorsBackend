package ttl.larku.jconfig.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
public class SecurityConfigRest {

   @Bean
   public SecurityFilterChain restFilterChain(HttpSecurity http,
                                              AuthExceptionHandler authExceptionHandler,
                                              AccessExceptionHandler accessExceptionHandler) throws Exception {
      //Like a master filter
      http.securityMatcher("/adminrest/**");

      http.authorizeHttpRequests(auth -> {
         auth.requestMatchers(HttpMethod.GET, "/adminrest/**").authenticated();

         //auth.requestMatchers(HttpMethod.GET, "/adminrest/**").hasRole("USER");

         auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.POST).hasRole("ADMIN");
         auth.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");

      });

      //http.httpBasic(Customizer.withDefaults());
      http.httpBasic(customizer -> customizer.authenticationEntryPoint(authExceptionHandler));

      http.exceptionHandling(cust -> cust.accessDeniedHandler(accessExceptionHandler));

      http.csrf(AbstractHttpConfigurer::disable);

      var chain = http.build();
      return chain;
   }
}
