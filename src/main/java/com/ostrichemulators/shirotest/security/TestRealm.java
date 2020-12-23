/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.security;

import com.ostrichemulators.shirotest.crosscutting.JwtUtils;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ryan
 */
public class TestRealm extends AuthorizingRealm {

	private static final Logger LOG = LoggerFactory.getLogger(TestRealm.class );
	private static final String REALM = "Test Realm";

	private static final CredentialsMatcher BCRYPT = new BcryptCredentialsMatcher();

	public TestRealm() {
		super.setName( REALM );
	}

	public TestRealm( CacheManager cacheManager ) {
		super( cacheManager );
		super.setName( REALM );
	}

	public TestRealm( CredentialsMatcher matcher ) {
		super( matcher );
		super.setName( REALM );
	}

	public TestRealm( CacheManager cacheManager, CredentialsMatcher matcher ) {
		super( cacheManager, matcher );
		super.setName( REALM );
	}

	@Override
	public boolean supports( AuthenticationToken token ) {
		return ( token instanceof UsernamePasswordToken
				|| token instanceof BearerToken );
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken token ) {
		if ( null == token ) {
			throw new AuthenticationException( "authenticating null token?" );
		}

		// no matter what type of token we have, we need to pull the hashed password
		// from our data store and pass this along for Shiro to authenticate
		String username = null;

		if ( token instanceof UsernamePasswordToken ) {
			UsernamePasswordToken upt = UsernamePasswordToken.class.cast( token );
			username = upt.getUsername();
		}
		else if ( token instanceof BearerToken ) {
			BearerToken bt = BearerToken.class.cast( token );
			username = JwtUtils.parse( bt.getToken() );
		}

		SimplePrincipalCollection spc = new SimplePrincipalCollection();
		spc.add( username, REALM );

		return new SimpleAuthenticationInfo( spc, "" );
	}

	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		return BCRYPT;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo​( PrincipalCollection principals ) {
		return new SimpleAuthorizationInfo( Set.of( "admin" ) );

	}}
