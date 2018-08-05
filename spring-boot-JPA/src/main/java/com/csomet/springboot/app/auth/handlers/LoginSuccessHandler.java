package com.csomet.springboot.app.auth.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		FlashMapManager flashMapManager = new SessionFlashMapManager();
		
		FlashMap flashmap = new FlashMap();
		
		flashmap.put("success", "You have logged in succesfuly");
		
		flashMapManager.saveOutputFlashMap(flashmap, request, response);
		
		super.onAuthenticationSuccess(request, response, authentication);
		
	}
	
	

}
