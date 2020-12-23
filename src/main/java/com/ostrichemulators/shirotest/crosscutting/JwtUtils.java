/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.crosscutting;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 *
 * @author ryan
 */
public class JwtUtils {

	private static final Logger LOG = LoggerFactory.getLogger( JwtUtils.class );
	private static final int tokenlifespan_s = 60 * 60;
	private static final String tokensigningkey = "replace me";

	private JwtUtils() {
	}

	public static void appendTokenToResponse( Subject token, ServletResponse response ) {
		String principal = token.getPrincipals().oneByType( String.class );

		HttpServletResponse resp = WebUtils.toHttp( response );
		resp.addHeader( HttpHeaders.AUTHORIZATION, "Bearer " + Jwts.builder()
				.setSubject( principal )
				.setExpiration( new Date( System.currentTimeMillis() + tokenlifespan_s * 1000 ) )
				.signWith( SignatureAlgorithm.HS512, tokensigningkey )
				.compact() );
	}

	public static String parse( String token ) {
		Jws<Claims> jws;
		jws = Jwts.parser()
				.setSigningKey( tokensigningkey )
				.parseClaimsJws( token );
		String username = jws.getBody().getSubject();
		return username;
	}
}
