package com.alkemy.challenge.config;

import javax.annotation.Resource;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.alkemy.challenge.filter.JwtAuthorizationFilter;
import com.alkemy.challenge.model.UserRol;
@EnableGlobalMethodSecurity(prePostEnabled = true) // # Segundo metodo de autorizacion pero este es prioritario si entran en conflicto
@EnableWebSecurity( debug = false )
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Resource
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.httpBasic()
			.and()
			.authorizeRequests()
			.mvcMatchers("/auth/register").permitAll()
			.mvcMatchers(HttpMethod.GET,"/movies").hasAnyRole(UserRol.USER.toString(),UserRol.ADMIN.toString())
			.mvcMatchers(HttpMethod.GET,"/characters").hasAnyRole(UserRol.USER.toString(),UserRol.ADMIN.toString())
			.mvcMatchers("/auth/register-admin").hasRole(UserRol.ADMIN.toString())
			.mvcMatchers(HttpMethod.POST,"/movies").hasRole(UserRol.ADMIN.toString())
			.mvcMatchers(HttpMethod.POST,"/characters").hasRole(UserRol.ADMIN.toString())
			.mvcMatchers(HttpMethod.DELETE,"/movies").hasRole(UserRol.ADMIN.toString())
			.mvcMatchers(HttpMethod.DELETE,"/characters").hasRole(UserRol.ADMIN.toString())
			.mvcMatchers(HttpMethod.PUT,"/movies").hasRole(UserRol.ADMIN.toString())
			.mvcMatchers(HttpMethod.PUT,"/characters").hasRole(UserRol.ADMIN.toString())
			.anyRequest().authenticated()
			.and()
			.formLogin().permitAll()
			.and()
			.logout().permitAll()
			.and()
			.addFilter(jwtAuthorizationFilter());
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
	@Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
	
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter()throws Exception{
		return new JwtAuthorizationFilter(this.authenticationManager());
	}
	
	/*@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user")
				.password("pass")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}*/
}
