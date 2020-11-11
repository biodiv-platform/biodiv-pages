/**
 * 
 */
package com.strandls.pages.services;

import java.util.List;

import com.strandls.pages.pojo.Newsletter;
import com.strandls.pages.pojo.response.NewsletterWithParentChildRelationship;

/**
 * 
 * @author vilay
 *
 */
public interface NewsletterSerivce {
	
	public Newsletter findById(Long id);
	
	public List<NewsletterWithParentChildRelationship> getByUserGroupAndLanguage(Long userGroupId, Long languageId);

	public Newsletter save(Newsletter newsletter);

	public Newsletter update(Newsletter newsletter);
}
