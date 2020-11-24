package com.strandls.pages.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.strandls.pages.dao.NewsletterDao;
import com.strandls.pages.dao.PageDao;
import com.strandls.pages.pojo.Newsletter;
import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.PageType;
import com.strandls.pages.pojo.request.PageCreate;
import com.strandls.pages.pojo.request.PageTreeUpdate;
import com.strandls.pages.pojo.request.PageUpdate;
import com.strandls.pages.pojo.response.PageArrayList;
import com.strandls.pages.pojo.response.PageTree;
import com.strandls.pages.services.PageSerivce;
import com.strandls.pages.util.AbstractService;

public class PageServiceImpl extends AbstractService<Page> implements PageSerivce{
	
	@Inject
	private PageDao pageDao;
	
	@Inject
	private NewsletterDao newsletterDao;
	
	@Inject
	public PageServiceImpl(PageDao dao) {
		super(dao);
	}
	
	@Override
	public Page savePage(HttpServletRequest request, PageCreate pageCreate) {

		Page page = new Page();
		page.setTitle(pageCreate.getTitle());
		page.setContent(pageCreate.getContent());
		page.setDescription(pageCreate.getDescription());
		page.setUserGroupId(pageCreate.getUserGroupId());
		page.setLanguageId(pageCreate.getLanguageId());
		page.setParentId(pageCreate.getParentId());
		page.setPageType(pageCreate.getPageType());
		page.setUrl(pageCreate.getUrl());
		page.setAutherId(pageCreate.getAutherId());
		page.setAutherName(pageCreate.getAutherName());
		page.setDate(pageCreate.getDate());
		page.setSticky(pageCreate.getSticky());
		page.setShowInFooter(pageCreate.getShowInFooter());
		page.setIsDeleted(false);
		page.setPageIndex(0); // Setting the value 0 just because it is non null.
		
		page = save(page);
		
		page.setPageIndex(page.getId().intValue());
		
		return page;
	}
	
	@Override
	public Page updatePage(HttpServletRequest request, PageUpdate pageUpdate) {
		
		Page page = findById(Long.parseLong(pageUpdate.getId()));
		
		page.setTitle(pageUpdate.getTitle());
		page.setContent(pageUpdate.getContent());
		page.setDescription(pageUpdate.getDescription());
		page.setPageType(pageUpdate.getPageType());
		page.setUrl(pageUpdate.getUrl());
		page.setSticky(pageUpdate.getSticky());
		page.setShowInFooter(pageUpdate.getShowInFooter());
		
		return update(page);
	}
	
	@Override
	public List<PageTree> updateTreeStructure(HttpServletRequest request, List<PageTreeUpdate> pageTreeUpdates, Boolean sticky) {
		
		if(pageTreeUpdates.isEmpty())
			return new ArrayList<PageTree>();
		
		for(PageTreeUpdate pageTreeUpdate : pageTreeUpdates) {
			Page page = findById(pageTreeUpdate.getId());
			page.setParentId(pageTreeUpdate.getParentId());
			page.setPageIndex(pageTreeUpdate.getPageIndex());
			update(page);
		}
		
		Page page = findById(pageTreeUpdates.get(0).getId());
		Long userGroupId = page.getUserGroupId();
		Long languageId = page.getLanguageId();
		
		return getTreeStructure(userGroupId, languageId, sticky);
	}
	
	@Override
	public Page updateParent(Long pageId, Long parentId) {
		Page page = pageDao.findById(pageId);
		if(page != null) {
			page.setParentId(parentId);
			page = pageDao.update(page);
		}
		return page;
	}
	
	@Override
	public void migrate() {
		List<Page> pages = pageDao.findAll();
		if(!pages.isEmpty())
			return;

		List<Newsletter> newsletters = newsletterDao.findAll();
		
		Map<Long, Page> idToPage = new HashMap<Long, Page>();
		
		for(Newsletter newsletter : newsletters) {
			Long id = newsletter.getId();
			String title = newsletter.getTitle();
			String content = newsletter.getNewsitem();
			String description = newsletter.getTitle();
			Long userGroupId = newsletter.getUserGroupId();
			Long languageId = newsletter.getLanguageId();
			Integer pageIndex = newsletter.getDisplayOrder();
			Long parentId = 0L;
			PageType pageType = PageType.CONTENT;
			String url = "";
			Long autherId = 1L;
			String autherName = "admin";
			Timestamp date = newsletter.getDate();
			Boolean sticky = newsletter.getSticky();
			Boolean showInFooter = newsletter.getShowInFooter();
			Boolean isDeleted = false;
			
			Page page = new Page();
			page.setTitle(title);
			page.setContent(content);
			page.setDescription(description);
			page.setUserGroupId(userGroupId);
			page.setLanguageId(languageId);
			page.setPageIndex(pageIndex);
			page.setParentId(parentId);
			page.setPageType(pageType);
			page.setUrl(url);
			page.setAutherId(autherId);
			page.setAutherName(autherName);
			page.setDate(date);
			page.setSticky(sticky);
			page.setShowInFooter(showInFooter);
			page.setIsDeleted(isDeleted);
			
			page.setId(id);
			
			page = save(page);
			
			idToPage.put(id, page);
		}
		
		for(Newsletter newsletter : newsletters) {
			Long id = newsletter.getId();
			Long parentId = newsletter.getParentId();
			
			Page page = idToPage.get(id);
			
			if(parentId != 0) {
				Page parentPage = idToPage.get(parentId);
				page.setParentId(parentPage.getId());
				update(page);
			}
		}
	}

	@Override
	public List<PageTree> getTreeStructure(Long userGroupId, Long languageId, Boolean sticky) {
		List<Page> pages = pageDao.getByUserGroupAndLanguage(userGroupId, languageId, sticky);
		
		Map<Long, PageTree> treeNodes = new HashMap<Long, PageTree>();
		for(Page page : pages) {
			PageTree pageTree = new PageTree(page);
			treeNodes.put(page.getId(), pageTree);
		}
		List<PageTree> resultTree = new PageArrayList();
		
		for(Map.Entry<Long, PageTree> entry : treeNodes.entrySet()) {
			PageTree pageTree = entry.getValue();
			Long parentId = pageTree.getParentId();
			if(parentId == 0 ) {
				resultTree.add(pageTree);
			} else {
				PageTree parentPage = treeNodes.get(parentId);
				parentPage.getChildren().add(pageTree);
			}
		}
		
		return resultTree;
	}

	@Override
	public Boolean getCheckForStickyPermission(HttpServletRequest request) {
		return true;
	}

}
