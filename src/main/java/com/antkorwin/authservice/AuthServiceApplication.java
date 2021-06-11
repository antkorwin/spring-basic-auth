package com.antkorwin.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityMethodConfig  {

}

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		    .authorizeRequests().anyRequest().authenticated()
		    .and()
		    .httpBasic();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		    .withUser("admin")
		    .password("{noop}q1w2e3r4")
		    .roles("ADMIN")
		    .and()
		    .withUser("user")
		    .password("{noop}q1w2e3r4")
		    .roles("USER");
	}
}

@RestController
@RequestMapping("/bar")
class BarController {

	@GetMapping("/beer")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminEndPoint() {
		return "Your beers: 🍺🍺🍺\n";
	}

	@GetMapping("/burger")
	@PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
	public String userEndPoint() {
		return "Bon Appetite: 🍔🍔🍔\n";
	}
}

@RestController
@RequestMapping("/nuclear")
class NuclearController {

	@GetMapping("/destroy")
	@PreAuthorize("hasRole('ADMIN')")
	public String nuclearEndPoint() {
		return "     _.-^^---....,,--       \n" +
		       " _--                  --_  \n" +
		       "<                        >)\n" +
		       "|                         | \n" +
		       " \\._                   _./  \n" +
		       "    ```--. . , ; .--'''       \n" +
		       "          | |   |             \n" +
		       "       .-=||  | |=-.   \n" +
		       "       `-=#$%&%$#=-'   \n" +
		       "          | ;  :|     \n" +
		       " _____.,-#%&$@%#&#~,._____";
	}
}



