package io.c12.bala.web.leaf.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/registerSubmit").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").successForwardUrl("/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().rememberMe().key("p*Uq2An$R^GZn8CNY#cP3su`zEDSTHS7b^+3k/x2!4a,kCjZ{m")
                .rememberMeParameter("rememberMe").rememberMeCookieName("DgR0R6FlY6CVwyiZBea7M").userDetailsService(customUserDetailsService)
                .and().authenticationProvider(customAuthenticationProvider)
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").deleteCookies("DgR0R6FlY6CVwyiZBea7M")
                .and().exceptionHandling()
                .and().sessionManagement().sessionFixation().newSession();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/js/**", "/css/**", "/icon/**", "/webjars/**");
    }

}
