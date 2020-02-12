package progmatic.hegymaszas.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {
    SimpleUrlAuthenticationFailureHandler failureHandler =  new SimpleUrlAuthenticationFailureHandler();
    MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler = new MySavedRequestAwareAuthenticationSuccessHandler();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();
        http.csrf().disable()
                .formLogin()
                .successHandler(mySuccessHandler)
                .failureHandler(failureHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                //.loginPage("/login").permitAll()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(HttpServletResponse.SC_OK))
                .and()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/loginpage/beforelogin", "/climbingplace", "/map", "/rest/sector",
                        "/rest/route", "/sector", "/home", "/", "/registerUser", "/register", "/ws", "/ws/queue",
                        "/queue", "/queue/reply", "/ws.addUser").permitAll()
                .antMatchers("/users", "/user/changeRole").hasRole("ADMIN")
                .antMatchers("/areas", "/areas/**","/route**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","PATCH","HEAD","OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
