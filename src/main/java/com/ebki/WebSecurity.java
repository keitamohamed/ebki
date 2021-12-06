package com.ebki;

import com.ebki.auth.UserDetailsServiceAuth;
import com.ebki.config.JwtConfig;
import com.ebki.jwt.CustomAuthenticationFilter;
import com.ebki.jwt.JwtCustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ebki.security.UserRole.ADMIN;

@Configurable
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceAuth detailsServiceAuth;
    private final JwtConfig jwtConfig;

    private static final String[] PUBLIC_ACCESS = {
            "/**",
            "/ebik/**",
            "/driver/add",
            "/car/carList/**",
            "/car/brand/**",
            "/car/year/**",
            "/car/vin/**",
            "/car/download-image/**"
    };

    private final String[] ADMIN_ACCESS = {
            "/ebik/driver/**",
            "/ebik/car/**",
            "/ebik/checkout/**",
            "/ebik/checkin/**"
    };

    public WebSecurity(UserDetailsServiceAuth detailsServiceAuth, JwtConfig jwtConfig) {
        this.detailsServiceAuth = detailsServiceAuth;
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsServiceAuth).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(jwtConfig, authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/ebik/login");
        http
                .csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_ACCESS).permitAll()
                .antMatchers(ADMIN_ACCESS).hasAnyAuthority("ROLE_ADMIN")
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new JwtCustomAuthorizationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
