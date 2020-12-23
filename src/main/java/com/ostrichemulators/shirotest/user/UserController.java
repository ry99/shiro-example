/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.user;

import com.ostrichemulators.shirotest.crosscutting.JwtUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ryan
 */
@RestController
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger( UserController.class );

	@PostMapping( "/login" )
	public String login( @RequestBody LoginData userdata, HttpServletRequest res,
			HttpServletResponse resp ) throws AuthenticationException {
		Subject sub = SecurityUtils.getSubject();
		sub.login( new UsernamePasswordToken( userdata.username, userdata.password, false ) );
		JwtUtils.appendTokenToResponse( sub, resp );

		return userdata.username;
	}

	@RequiresAuthentication
	@GetMapping( "/logout" )
	public void logout() {
		LOG.debug( "logging out!" );
		SecurityUtils.getSubject().logout();
	}

	@RequiresRoles( "admin" )
	@GetMapping( "/admin" )
	public String adminConfig() {
		return "got there!";
	}

	public static final class LoginData {

		public String username;
		public String password;
	}
}
