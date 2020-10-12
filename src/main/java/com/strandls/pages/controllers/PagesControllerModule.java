/**
 * 
 */
package com.strandls.pages.controllers;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * 
 * @author vilay
 *
 */
public class PagesControllerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(NewsletterController.class).in(Scopes.SINGLETON);
		bind(PageController.class).in(Scopes.SINGLETON);
	}
}
