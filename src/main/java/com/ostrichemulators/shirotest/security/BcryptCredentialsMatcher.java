/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ryan
 */
public class BcryptCredentialsMatcher implements CredentialsMatcher {

	private static final Logger LOG = LoggerFactory.getLogger( BcryptCredentialsMatcher.class );

	@Override
	public boolean doCredentialsMatch( AuthenticationToken token, AuthenticationInfo info ) {
		if ( token instanceof BearerToken ) {
			// if we have a bearer token, the realm decoded the user already
			// and we don't have a raw password to check against our hash
			return true;
		}

		char[] userEntered = (char[]) token.getCredentials();
		return new String( userEntered ).equals( "password");
	}
}
