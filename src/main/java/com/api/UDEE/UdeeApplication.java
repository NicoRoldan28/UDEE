package com.api.UDEE;

import com.api.UDEE.filter.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class UdeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdeeApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/auth/login").permitAll()
					.antMatchers(HttpMethod.POST, "/auth/login2").permitAll()
					.antMatchers(HttpMethod.POST, "/users2").permitAll()
					.antMatchers(HttpMethod.GET, "/users2").permitAll()
					.antMatchers(HttpMethod.POST, "/api/rates").permitAll()
					.antMatchers(HttpMethod.POST, "/api/clients").permitAll()
					.antMatchers(HttpMethod.POST, "/api/address").permitAll()
					.anyRequest().authenticated();

			/*
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/measurement").permitAll()
					.antMatchers(HttpMethod.POST, "/login").permitAll()
					.antMatchers(HttpMethod.POST, "/backoffice/login").permitAll()
					.antMatchers("/console/**").permitAll() //TODO borrar esta linea
					.antMatchers("/tariff/**").hasAuthority(User.TYPE.BLACKOFFICE.name())
					.antMatchers("/clients/**").hasAuthority(User.TYPE.BLACKOFFICE.name())
					.anyRequest().authenticated();
			 */
		}
	}
}
