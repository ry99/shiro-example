/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.config;

import com.ostrichemulators.shirotest.security.JwtFilter;
import com.ostrichemulators.shirotest.security.TestRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.config.ShiroAnnotationProcessorConfiguration;
import org.apache.shiro.spring.config.ShiroBeanConfiguration;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroRequestMappingConfig;
import org.apache.shiro.spring.web.config.ShiroWebConfiguration;
import org.apache.shiro.spring.web.config.ShiroWebFilterConfiguration;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author ryan
 */
@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan( basePackages = "com.ostrichemulators" )
@Import( {
	ShiroBeanConfiguration.class,
	ShiroAnnotationProcessorConfiguration.class,
	ShiroWebConfiguration.class,
	ShiroWebFilterConfiguration.class,
	ShiroRequestMappingConfig.class
} )
public class Config {

	private static final Logger LOG = LoggerFactory.getLogger( Config.class );

	@Autowired
	private Environment env;

	public Config() {
		LOG.debug( "here we are! initializing!" );
	}

	@Bean
	public TestRealm realm() {
		return new TestRealm();
	}

	@Bean
	protected CacheManager cacheManager() {
		return new MemoryConstrainedCacheManager();
	}

	@Bean
	public JwtFilter jwtFilter() {
		return new JwtFilter();
	}

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
	//public ShiroFilterChainDefinition improperlyNamedShiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

		// anonymous users can hit the login page
		chainDefinition.addPathDefinition( "/login", "anon" );

		// logged in users with the 'admin' role
		chainDefinition.addPathDefinition( "/admin/**", "noSessionCreation, jwtFilter, roles[admin]" );

		// all other paths require a logged in user
		chainDefinition.addPathDefinition( "/**", "noSessionCreation, jwtFilter" );
		return chainDefinition;
	}

	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager mgr = new DefaultWebSessionManager();
		mgr.setSessionIdCookieEnabled( false );
		return mgr;
	}
}
