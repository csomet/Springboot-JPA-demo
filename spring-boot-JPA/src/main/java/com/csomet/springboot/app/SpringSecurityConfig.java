package com.csomet.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.csomet.springboot.app.auth.handlers.LoginSuccessHandler;
import com.csomet.springboot.app.model.services.JPAUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	//@Autowired
	//private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private JPAUserDetailsService jpaUserDetailsService;
	
	@Autowired
	public void configGlobal(AuthenticationManagerBuilder build) {
		
		//Spring security JDBC
		try {
			
			//With JPA
			build.userDetailsService(jpaUserDetailsService)
			.passwordEncoder(passwordEncoder);
			
			
			/* WITH JDBC
			 * build.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder)
				.usersByUsernameQuery("select username, password, enabled from users u where username = ?")
				.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (u.id = a.user_id) where u.username=?");
			*/
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		/*PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
        try {
			build.inMemoryAuthentication().withUser(users.username("admin").password("1234").roles("ADMIN", "USER"))
			.withUser(users.username("carlos").password("1234").roles("USER"));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/list", "/locale").permitAll()
		.anyRequest().authenticated()
		//.antMatchers("/view/**").hasAnyRole("USER")
		//.antMatchers("/uploads/**").hasAnyRole("USER")
		//Permissions to create or edit clients
		//.antMatchers("/form/**").hasAnyRole("ADMIN")
		//Permission to delete clients 
		//.antMatchers("/delete/**").hasAnyRole("ADMIN")
		//Permissions to manage invoices...
		//.antMatchers("/invoice/**").hasAnyRole("ADMIN").anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login")
			.successHandler(successHandler)
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		//it is also in MVCConfig...
		.exceptionHandling().accessDeniedPage("/error_403");
	
	}
}
