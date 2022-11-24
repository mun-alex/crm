package kz.bitlab.spring.crm.config;

import kz.bitlab.spring.crm.controllers.Constants;
import kz.bitlab.spring.crm.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsersService usersService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/").permitAll()
                )
                .exceptionHandling((exception) -> exception.accessDeniedPage("/403")
                )
                .formLogin((login) -> login.loginPage((Constants.API_USERS + "/login")).permitAll()
                        .usernameParameter("user_email")
                        .passwordParameter("user_password")
                        .loginProcessingUrl("/auth").permitAll()
                        .failureUrl(Constants.API_USERS + "/login?error")
                        .defaultSuccessUrl(Constants.API_USERS + "/profile")
                )
                .logout((logout) -> logout.logoutUrl("/logout").permitAll()
                        .logoutSuccessUrl(Constants.API_USERS + "/login")
                );
        return http.build();
    }
}
