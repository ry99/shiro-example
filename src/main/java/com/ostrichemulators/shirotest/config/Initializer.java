/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ostrichemulators.shirotest.config;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author ryan
 */
public class Initializer implements WebApplicationInitializer {

	@Override
	public void onStartup( ServletContext container ) throws ServletException {
		//now add the annotations
		AnnotationConfigWebApplicationContext appContext = getContext();

		// Manage the lifecycle of the root application context
		container.addListener( new ContextLoaderListener( appContext ) );

		FilterRegistration.Dynamic shiroFilter
				= container.addFilter( "shiroFilterFactoryBean", DelegatingFilterProxy.class );
		shiroFilter.setInitParameter( "targetFilterLifecycle", "true" );
		shiroFilter.addMappingForUrlPatterns( EnumSet.allOf( DispatcherType.class ), false, "/*" );

		ServletRegistration.Dynamic dispatcher
				= container.addServlet( "DispatcherServlet", new DispatcherServlet( appContext ) );
		dispatcher.setLoadOnStartup( 1 );
		dispatcher.addMapping( "/" );
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation( getClass().getPackage().getName() );
		return context;
	}
}
