package ttl.larku.jconfig.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author whynot
 */
//@Configuration
@Order(3)
public class SecurityFilterAllElse {

    @Autowired
    private AccessExceptionHandler accessDeniedHandler;
    @Autowired
    private AuthExceptionHandler authExceptionHandler;

    /**
     * This is the method to override to do authorization.
     */
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//    @Bean
    public SecurityFilterChain allElseFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll()
//                .and()
//                .csrf().ignoringAntMatchers("/h2-console/**")
//                .and()
//                .headers().frameOptions().sameOrigin();

        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/resources/**", "/static/**", "/index.html").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults());
////
//        //ExceptionHandling
//        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authExceptionHandler);

        return http.build();
    }

    public SecurityFilterChain oldFilterChain(HttpSecurity http) throws Exception {
        // magic to make the h2-console work, since it doesn't send
        // csrf tokens. Note that since we are disabling csrf completely
        // below, we really don't need the csrf part here, but just as an example.
        //The frameoptions bit is to allow h2-console to use frames.

        http.authorizeRequests()
                .requestMatchers("/resources/**", "/static/**", "/index.html").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(HttpMethod.GET, "/adminrest/student/whoiscalling").hasRole("ADMIN")
                // All GET requests are open
                .requestMatchers(HttpMethod.GET, "/adminrest/**").permitAll()
                //PUT needs to be authenticated and is then further fine-tuned in StudentService.
                .requestMatchers(HttpMethod.PUT, "/adminrest/**").hasAnyRole("USER", "ADMIN")
                // POST, DELETE, etc. requests to /adminrest/** need Role of ADMIN
                .requestMatchers(HttpMethod.POST, "/adminrest/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/adminrest/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/adminrest/**").hasRole("ADMIN")
                // management actuator endpoints
                .requestMatchers(EndpointRequest.toAnyEndpoint().excluding("health", "info")).hasRole("ADMIN")
                .anyRequest().denyAll()
                .and()
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults());
//                .and()
//                .authorizeRequests().mvcMatchers("/index.html", "/otherPage.html").authenticated()
////                .anyRequest().authenticated()
//                .and().formLogin();

//				.csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));


        //ExceptionHandling
//        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authExceptionHandler);

        return http.build();
    }
}
