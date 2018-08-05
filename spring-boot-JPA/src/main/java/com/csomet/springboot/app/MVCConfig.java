package com.csomet.springboot.app;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		
		//With this method, you can call the relative reference to /upload/* in html because is registered as project folder 
		//(even if it is outside of the project...)
		/*WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String uploadFolderPath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/uploads/**").addResourceLocations(uploadFolderPath);
		*/
		
	}
	
	public void addViewControllers(ViewControllerRegistry reg) {
		
		reg.addViewController("/error_403").setViewName("error_403");
		
	}

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver sessionLR = new SessionLocaleResolver();
		sessionLR.setDefaultLocale(new Locale("en", "US"));
		return sessionLR;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setClassesToBeBound(new Class[] {com.csomet.springboot.app.view.xml.ClientList.class});
		return jaxb2Marshaller;
	}
	
}
