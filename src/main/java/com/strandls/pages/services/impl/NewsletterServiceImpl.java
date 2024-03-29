/**
 * 
 */
package com.strandls.pages.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import com.strandls.pages.dao.NewsletterDao;
import com.strandls.pages.pojo.Newsletter;
import com.strandls.pages.pojo.response.NewsletterArrayList;
import com.strandls.pages.pojo.response.NewsletterWithParentChildRelationship;
import com.strandls.pages.services.NewsletterSerivce;
import com.strandls.pages.util.AbstractService;

/**
 * 
 * @author vilay
 *
 */
public class NewsletterServiceImpl extends AbstractService<Newsletter> implements NewsletterSerivce {

	@Inject
	public NewsletterServiceImpl(NewsletterDao dao) {
		super(dao);
	}

	@Override
	public List<NewsletterWithParentChildRelationship> getByUserGroupAndLanguage(Long userGroupId, Long languageId) {
		List<Newsletter> newsletters = ((NewsletterDao) dao).getByUserGroupAndLanguage(userGroupId, languageId);
		Map<Long, NewsletterWithParentChildRelationship> newsletterWithParentChildRelationships = new HashMap<>();
		for (Newsletter newsletter : newsletters) {
			NewsletterWithParentChildRelationship n = new NewsletterWithParentChildRelationship();
			n.setId(newsletter.getId());
			n.setTitle(newsletter.getTitle());
			n.setParentId(newsletter.getParentId());
			n.setDisplayOrder(newsletter.getDisplayOrder());
			List<NewsletterWithParentChildRelationship> childs = new NewsletterArrayList();
			n.setChilds(childs);
			newsletterWithParentChildRelationships.put(newsletter.getId(), n);
		}
		return makeParentChildRelationship(newsletterWithParentChildRelationships);
	}

	private List<NewsletterWithParentChildRelationship> makeParentChildRelationship(
			Map<Long, NewsletterWithParentChildRelationship> newsletterWithParentChildRelationships) {

		List<NewsletterWithParentChildRelationship> result = new NewsletterArrayList();
		

		for (Entry<Long, NewsletterWithParentChildRelationship> e : newsletterWithParentChildRelationships.entrySet()) {
			NewsletterWithParentChildRelationship value = e.getValue();
			Long parentId = value.getParentId();
			if (parentId == 0) {
				result.add(e.getValue());
			} else {
				NewsletterWithParentChildRelationship parent = newsletterWithParentChildRelationships.get(parentId);
				List<NewsletterWithParentChildRelationship> childs = parent.getChilds();
				childs.add(value);
			}
		}
		return result;
	}
}
