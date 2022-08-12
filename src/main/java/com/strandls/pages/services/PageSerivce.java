/**
 * 
 */
package com.strandls.pages.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.request.PageCreate;
import com.strandls.pages.pojo.request.PageTreeUpdate;
import com.strandls.pages.pojo.request.PageUpdate;
import com.strandls.pages.pojo.request.ReorderingGalleryPage;
import com.strandls.pages.pojo.response.PageTree;
import com.strandls.userGroup.ApiException;

/**
 * 
 * @author vilay
 *
 */
public interface PageSerivce {

	public Page updateParent(Long pageId, Long parentId);

	public Page findPageById(Long pageId);

	public Page update(Page page);

	public List<PageTree> getTreeStructure(Long userGroupId, Long languageId, Boolean sticky);

	public Boolean getCheckForStickyPermission(HttpServletRequest request);

	public Page savePage(HttpServletRequest request, PageCreate page);

	public void migrate();

	public List<PageTree> updateTreeStructure(HttpServletRequest request, List<PageTreeUpdate> pageTreeUpdates,
			Boolean sticky);

	public Page updatePage(HttpServletRequest request, PageUpdate pageUpdate);

	public Page deletePage(HttpServletRequest request, Long pageId);

	public boolean checkForPagePermission(HttpServletRequest request, Long pageId) throws ApiException;

	public boolean checkForGroupPermission(HttpServletRequest request, Long pageId) throws ApiException;

	public Page reorderingPageGallerySlider(Long pageId, List<ReorderingGalleryPage> reorderingGalleryPage);

	public Page removePageGallerySlider(Long gallerySilderId, Long pageId);
}
