/**
 * 
 */
package com.strandls.pages.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * 
 * @author vilay
 *
 */
public class PagesDaoModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(NewsletterDao.class).in(Scopes.SINGLETON);
		bind(PageDao.class).in(Scopes.SINGLETON);
		bind(PageGallerySilderDao.class).in(Scopes.SINGLETON);
	}
}
