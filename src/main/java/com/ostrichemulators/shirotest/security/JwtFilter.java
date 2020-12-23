/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.security;

import com.ostrichemulators.shirotest.crosscutting.JwtUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ryan
 */
public class JwtFilter extends BearerHttpAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger( JwtFilter.class );

	@Override
	protected boolean onLoginSuccess( AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response ) throws Exception {
		boolean ok = super.onLoginSuccess( token, subject, request, response );
		if ( ok ) {
			JwtUtils.appendTokenToResponse( SecurityUtils.getSubject(), response );
		}

		return ok;
	}
}
