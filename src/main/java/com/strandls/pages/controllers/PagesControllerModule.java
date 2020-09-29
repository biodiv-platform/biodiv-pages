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
		bind(PagesController.class).in(Scopes.SINGLETON);
	}
}
