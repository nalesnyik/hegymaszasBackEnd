package progmatic.hegymaszas.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

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
                .logoutSuccessUrl("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/loginpage/beforelogin", "/climbingplace", "/map", "/rest/sector", "/rest/route", "/sector", "/home", "/", "/registerUser", "/register").permitAll()
                .antMatchers("/users", "/user/changeRole").hasRole("ADMIN")
                .antMatchers("/areas", "/areas/**").permitAll()
                .anyRequest().authenticated();
    }
}
