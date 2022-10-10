package kz.bitlab.spring.crm.config;

import kz.bitlab.spring.crm.controllers.Constants;
import kz.bitlab.spring.crm.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsersService usersService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/").permitAll();

        http.exceptionHandling().accessDeniedPage("/403");

        http.formLogin()
                .loginPage(Constants.API_USERS + "/login").permitAll()
                .usernameParameter("user_email")
                .passwordParameter("user_password")
                .loginProcessingUrl("/auth").permitAll()
                .failureUrl(Constants.API_USERS + "/login?error")
                .defaultSuccessUrl(Constants.API_USERS + "/profile");

        http.logout()
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl(Constants.API_USERS + "/login");

        http.csrf().disable();
    }
}
