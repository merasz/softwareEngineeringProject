package at.qe.skeleton.configs;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import at.qe.skeleton.spring.CustomizedLogoutSuccessHandler;

/**
 * Spring configuration for web security.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Configuration
@EnableWebSecurity()
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    protected LogoutSuccessHandler logoutSuccessHandler() {
    	return new CustomizedLogoutSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.headers().frameOptions().disable(); // needed for H2 console

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login.xhtml")
                .logoutSuccessHandler(this.logoutSuccessHandler());

        http.authorizeRequests()
                //Permit access to the H2 console
                .antMatchers("/h2-console/**").permitAll()
                //Permit access for all to error pages
                .antMatchers("/error/**")
                .permitAll()
                // Only access with admin role
                .antMatchers("/admin/**")
                .hasAnyAuthority("ADMIN")
                //Permit access only for some roles
                .antMatchers("/secured/**")
                .hasAnyAuthority("ADMIN", "GAME_MANAGER", "PLAYER")
                // Allow only certain roles to use websockets (only logged in users)
                .antMatchers("/omnifaces.push/**")
                .hasAnyAuthority("ADMIN", "GAME_MANAGER", "PLAYER")
                .and().formLogin()
                .loginPage("/login.xhtml")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/secured/welcome.xhtml")
                .failureUrl("/login.xhtml?error");
 
        http.exceptionHandling().accessDeniedPage("/error/access_denied.xhtml");
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/login.xhtml?invalid_session");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //Configure roles and passwords via datasource
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from user where username=?")
                .authoritiesByUsernameQuery("select user_username, roles from user_user_role where user_username=?")
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
