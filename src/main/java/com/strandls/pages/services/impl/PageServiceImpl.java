package com.strandls.pages.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.strandls.activity.controller.ActivitySerivceApi;
import com.strandls.activity.pojo.Activity;
import com.strandls.activity.pojo.CommentLoggingData;
import com.strandls.activity.pojo.MailData;
import com.strandls.activity.pojo.PageMailData;
import com.strandls.pages.Headers;
import com.strandls.pages.dao.NewsletterDao;
import com.strandls.pages.dao.PageDao;
import com.strandls.pages.dao.PageGallerySilderDao;
import com.strandls.pages.pojo.Newsletter;
import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.PageGallerySlider;
import com.strandls.pages.pojo.PageType;
import com.strandls.pages.pojo.request.PageCreate;
import com.strandls.pages.pojo.request.PageTreeUpdate;
import com.strandls.pages.pojo.request.PageUpdate;
import com.strandls.pages.pojo.request.ReorderingGalleryPage;
import com.strandls.pages.pojo.response.PageArrayList;
import com.strandls.pages.pojo.response.PageTree;
import com.strandls.pages.services.PageSerivce;
import com.strandls.pages.util.AbstractService;
import com.strandls.pages.util.AuthUtility;
import com.strandls.userGroup.ApiException;
import com.strandls.userGroup.controller.UserGroupSerivceApi;

public class PageServiceImpl extends AbstractService<Page> implements PageSerivce {
	private final Logger logger = LoggerFactory.getLogger(PageServiceImpl.class);

	@Inject
	private PageDao pageDao;

	@Inject
	private NewsletterDao newsletterDao;

	@Inject
	private PageGallerySilderDao pageGallerySilderDao;

	@Inject
	private UserGroupSerivceApi userGroupSerivceApi;

	@Inject
	private Headers headers;

	@Inject
	private ActivitySerivceApi activityService;

	@Inject
	private LogActivities logActivities;

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
		page.setShowInPrimaryHeader(pageCreate.getShowInPrimaryHeader());
		page.setShowInSecondaryHeader(pageCreate.getShowInSecondaryHeader());
		page.setSocialPreview(pageCreate.getSocialPreview());
		page.setIsDeleted(false);
		page.setPageIndex(0); // Setting the value 0 just because it is non null.

		page.setAllowComments(pageCreate.getAllowComments());

		page = save(page);

		page.setPageIndex(page.getId().intValue());

		List<PageGallerySlider> galleryData = pageCreate.getGalleryData();
		if (galleryData != null && !galleryData.isEmpty()) {
			for (PageGallerySlider gallery : galleryData) {
				gallery.setPageId(page.getId());
				gallery.setAuthorId(page.getAutherId());
				pageGallerySilderDao.save(gallery);
			}

		}

		logActivities.LogPageActivities(request.getHeader(HttpHeaders.AUTHORIZATION), null, page.getId(), page.getId(),
				"page", null, "Page created", generatePageMailData(page.getId()));

		return getPageWithGalleryData(page);
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
		page.setShowInPrimaryHeader(pageUpdate.getShowInPrimaryHeader());
		page.setShowInSecondaryHeader(pageUpdate.getShowInSecondaryHeader());
		page.setSocialPreview(pageUpdate.getSocialPreview());
		page.setAllowComments(pageUpdate.getAllowComments());

//		update gallery slider if contains Id update else create new record

		List<PageGallerySlider> galleryData = pageUpdate.getGalleryData();
		if (galleryData != null && !galleryData.isEmpty())
			for (PageGallerySlider gallery : galleryData) {
				if (gallery.getId() != null) {
					PageGallerySlider entity = pageGallerySilderDao.findById(gallery.getId());
					entity.setDisplayOrder(gallery.getDisplayOrder());
					entity.setFileName(gallery.getFileName());
					entity.setPageId(page.getId());
					entity.setAuthorId(page.getAutherId());
					entity.setAttribution(gallery.getAttribution());
					entity.setCaption(gallery.getCaption());
					entity.setLicenseId(gallery.getLicenseId());
					pageGallerySilderDao.update(entity);
				} else {
					gallery.setAttribution(gallery.getAttribution());
					gallery.setCaption(gallery.getCaption());
					gallery.setLicenseId(gallery.getLicenseId());
					gallery.setDisplayOrder(gallery.getDisplayOrder());
					gallery.setFileName(gallery.getFileName());
					gallery.setPageId(page.getId());
					gallery.setAuthorId(page.getAutherId());
					pageGallerySilderDao.save(gallery);

				}

			}

		logActivities.LogPageActivities(request.getHeader(HttpHeaders.AUTHORIZATION), null, page.getId(), page.getId(),
				"page", null, "Page updated", generatePageMailData(page.getId()));

		return getPageWithGalleryData(update(page));
	}

	@Override
	public Page findPageById(Long pageId) {
		if (pageId == null) {
			return null;
		}
		Page page = findById(pageId);
		List<PageGallerySlider> galleryData = pageGallerySilderDao.findByPageId(page.getId());
		page.setGallerySilder(galleryData);
		return page;
	}

	private Page getPageWithGalleryData(Page page) {

		List<PageGallerySlider> galleryData = pageGallerySilderDao.findByPageId(page.getId());
		page.setGallerySilder(galleryData);
		return page;

	}

	@Override
	public List<PageTree> updateTreeStructure(HttpServletRequest request, List<PageTreeUpdate> pageTreeUpdates,
			Boolean sticky) {

		if (pageTreeUpdates.isEmpty())
			return new ArrayList<>();

		for (PageTreeUpdate pageTreeUpdate : pageTreeUpdates) {
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
		if (page != null) {
			page.setParentId(parentId);
			page = pageDao.update(page);
		}
		return page;
	}

	@Override
	public void migrate() {
		List<Page> pages = pageDao.findAll();
		if (!pages.isEmpty())
			return;

		List<Newsletter> newsletters = newsletterDao.findAll();

		Map<Long, Page> idToPage = new HashMap<>();

		for (Newsletter newsletter : newsletters) {
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

			page = save(page);

			idToPage.put(id, page);
		}

		for (Newsletter newsletter : newsletters) {
			Long id = newsletter.getId();
			Long parentId = newsletter.getParentId();

			Page page = idToPage.get(id);

			if (parentId != 0) {
				Page parentPage = idToPage.get(parentId);
				page.setParentId(parentPage.getId());
				update(page);
			}
		}
	}

	@Override
	public List<PageTree> getTreeStructure(Long userGroupId, Long languageId, Boolean sticky) {
		List<Page> pages = pageDao.getByUserGroupAndLanguage(userGroupId, languageId, sticky);

		Map<Long, PageTree> treeNodes = new HashMap<>();
		for (Page page : pages) {
			PageTree pageTree = new PageTree(page);
			treeNodes.put(page.getId(), pageTree);
		}
		List<PageTree> resultTree = new PageArrayList();

		for (Map.Entry<Long, PageTree> entry : treeNodes.entrySet()) {
			PageTree pageTree = entry.getValue();
			Long parentId = pageTree.getParentId();
			if (parentId == 0) {
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

	@Override
	public Page deletePage(HttpServletRequest request, Long pageId) {
		Page page = findById(pageId);
		page.setIsDeleted(true);

		logActivities.LogPageActivities(request.getHeader(HttpHeaders.AUTHORIZATION), null, page.getId(), page.getId(),
				"page", null, "Page Deleted", generatePageMailData(page.getId()));

		return update(page);
	}

	@Override
	public boolean checkForPagePermission(HttpServletRequest request, Long pageId) throws ApiException {
		Page page = findById(pageId);
		Long userGroupId = page.getUserGroupId();
		return checkForGroupPermission(request, userGroupId);
	}

	@Override
	public boolean checkForGroupPermission(HttpServletRequest request, Long userGroupId) throws ApiException {
		if (userGroupId == null) {
			return AuthUtility.isAdmin(request);
		}
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		userGroupSerivceApi.getApiClient().addDefaultHeader(HttpHeaders.AUTHORIZATION, authHeader);
		return userGroupSerivceApi.enableEdit(userGroupId.toString());
	}

	@Override
	public Page reorderingPageGallerySlider(Long pageId, List<ReorderingGalleryPage> reorderingGalleryPage) {
		try {
			for (ReorderingGalleryPage reOrder : reorderingGalleryPage) {
				PageGallerySlider gallery = pageGallerySilderDao.findById(reOrder.getGalleryId());
				gallery.setDisplayOrder(reOrder.getDisplayOrder());
				pageGallerySilderDao.update(gallery);
			}

			return getPageWithGalleryData(findById(pageId));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	@Override
	public Page removePageGallerySlider(Long gallerySilderId, Long pageId) {
		try {

			PageGallerySlider entity = pageGallerySilderDao.findById(gallerySilderId);
			pageGallerySilderDao.delete(entity);

			return getPageWithGalleryData(findById(pageId));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;

	}

	private MailData generatePageMailData(Long pageId) {
		MailData mailData = new MailData();
		PageMailData pageMailData = new PageMailData();
		Page page = pageDao.findById(pageId);
		pageMailData.setAuthorId(page.getAutherId());
		pageMailData.setCreatedOn(null);
		pageMailData.setPageId(pageId);
		pageMailData.setTitle(page.getTitle());
		mailData.setPageMailData(pageMailData);

		return mailData;

	}

	@Override
	public Activity addPageComment(HttpServletRequest request, CommentLoggingData comment) {
		try {
			comment.setMailData(generatePageMailData(comment.getRootHolderId()));
			activityService = headers.addActivityHeaders(activityService, request.getHeader(HttpHeaders.AUTHORIZATION));
			return activityService.addComment("page", comment);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Activity removePagesComment(HttpServletRequest request, CommentLoggingData comment, String commentId) {
		try {
			comment.setMailData(generatePageMailData(comment.getRootHolderId()));
			activityService = headers.addActivityHeaders(activityService, request.getHeader(HttpHeaders.AUTHORIZATION));
			Activity result = activityService.deleteComment("page", commentId, comment);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
}
