/**
 * 
 */
package com.strandls.pages.services.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.strandls.pages.services.NewsletterSerivce;
import com.strandls.pages.services.PageSerivce;

/**
 * 
 * @author vilay
 *
 */
public class PagesServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(NewsletterSerivce.class).to(NewsletterServiceImpl.class).in(Scopes.SINGLETON);
		bind(PageSerivce.class).to(PageServiceImpl.class).in(Scopes.SINGLETON);
	}
}
