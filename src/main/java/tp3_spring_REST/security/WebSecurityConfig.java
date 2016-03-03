package tp3_spring_REST.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	AuthenticationSuccessHandler restAuthenticationSuccessHandler;
	@Autowired
	AuthenticationFailureHandler restAuthenticationFailureHandler;
	@Autowired
	LogoutSuccessHandler restLogoutSuccessHandler;
	@Autowired
	AuthenticationEntryPoint restEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
		.authorizeRequests()
		.antMatchers("/api/authenticate", "/api/public/**").permitAll()
		.antMatchers("/api/**").authenticated()
		.and()
		.formLogin()
		.usernameParameter("login")
		.passwordParameter("password")
		.loginProcessingUrl("/api/authenticate")
		.successHandler(restAuthenticationSuccessHandler)
		.failureHandler(restAuthenticationFailureHandler).permitAll()
		.and().logout().logoutUrl("/api/logout")
		.logoutSuccessHandler(restLogoutSuccessHandler)
		.deleteCookies("JSESSIONID_COOKIE", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(restEntryPoint)
		.and().csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
	}
}
