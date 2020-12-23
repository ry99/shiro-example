/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.crosscutting;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author ryan
 */
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler( AuthorizationException.class )
	public final ResponseEntity<String> noauth( AuthorizationException ex, WebRequest request ) {
		return new ResponseEntity<>( "Authorization failure", HttpStatus.UNAUTHORIZED );
	}

	@ExceptionHandler( IncorrectCredentialsException.class )
	public final ResponseEntity<String> badcreds( IncorrectCredentialsException ex, WebRequest request ) {
		return new ResponseEntity<>( "Authentication failed (wrong username/password)", HttpStatus.UNAUTHORIZED );
	}

	@ExceptionHandler( ExpiredCredentialsException.class )
	public final ResponseEntity<String> expired( ExpiredCredentialsException ex, WebRequest request ) {
		return new ResponseEntity<>( "Authentication token expired", HttpStatus.UNAUTHORIZED );
	}

	@ExceptionHandler( AuthenticationException.class )
	public final ResponseEntity<String> generalauth( AuthenticationException ex, WebRequest request ) {
		LoggerFactory.getLogger( getClass() ).warn( "{}", ex );
		return new ResponseEntity<>( ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED );
	}
}
