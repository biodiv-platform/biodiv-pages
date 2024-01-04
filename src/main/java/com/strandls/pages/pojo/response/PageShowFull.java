package com.strandls.pages.pojo.response;

import java.sql.Timestamp;
import java.util.List;

import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.PageGallerySlider;
import com.strandls.pages.pojo.PageType;
import com.strandls.pages.util.AuthUtility;
import com.strandls.user.pojo.User;
import com.strandls.user.pojo.UserIbp;

public class PageShowFull {

	private Long id;
	private String title;
	private String content;
	private String description;
	private String socialPreview;

	private UserIbp userIbp;
	private Long languageId;
	private PageType pageType;
	private String url;

	private Long parentId;
	private Integer pageIndex;

	private Timestamp date;
	private Boolean sticky;
	private Boolean showInFooter;
	private Boolean showInPrimaryMenu;
	private Boolean showInSecondaryMenu;
	private List<PageGallerySlider> galleryData;
	private Boolean allowComments;

	public PageShowFull() {
		super();
	}

	public PageShowFull(Page page, User user) {
		this.id = page.getId();
		this.title = page.getTitle();
		this.content = page.getContent();
		this.description = page.getDescription();
		this.userIbp = new UserIbp();
		this.userIbp.setId(page.getAutherId());
		this.userIbp.setName(page.getAutherName());
		this.userIbp.setIsAdmin(AuthUtility.isAdmin(user));
		this.userIbp.setProfilePic(user.getProfilePic());
		this.languageId = page.getLanguageId();
		this.pageType = page.getPageType();
		this.url = page.getUrl();
		this.parentId = page.getParentId();
		this.pageIndex = page.getPageIndex();
		this.date = page.getDate();
		this.sticky = page.getSticky();
		this.showInFooter = page.getShowInFooter();
		this.showInPrimaryMenu = page.getShowInPrimaryMenu();
		this.showInSecondaryMenu = page.getShowInSecondaryMenu();
		this.galleryData = page.getGallerySilder();
		this.socialPreview = page.getSocialPreview();
		this.allowComments = page.getAllowComments();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserIbp getUserIbp() {
		return userIbp;
	}

	public void setUserIbp(UserIbp userIbp) {
		this.userIbp = userIbp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public PageType getPageType() {
		return pageType;
	}

	public void setPageType(PageType pageType) {
		this.pageType = pageType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Boolean getSticky() {
		return sticky;
	}

	public void setSticky(Boolean sticky) {
		this.sticky = sticky;
	}

	public Boolean getShowInFooter() {
		return showInFooter;
	}

	public void setShowInFooter(Boolean showInFooter) {
		this.showInFooter = showInFooter;
	}

	public List<PageGallerySlider> getGalleryData() {
		return galleryData;
	}

	public void setGalleryData(List<PageGallerySlider> galleryData) {
		this.galleryData = galleryData;
	}

	public String getSocialPreview() {
		return socialPreview;
	}

	public void setSocialPreview(String socialPreview) {
		this.socialPreview = socialPreview;
	}

	public Boolean getAllowComments() {
		return allowComments;
	}

	public void setAllowComments(Boolean allowComments) {
		this.allowComments = allowComments;
	}

	public Boolean getShowInPrimaryMenu() {
		return showInPrimaryMenu;
	}

	public void setShowInPrimaryMenu(Boolean showInPrimaryMenu) {
		this.showInPrimaryMenu = showInPrimaryMenu;
	}

	public Boolean getShowInSecondaryMenu() {
		return showInSecondaryMenu;
	}

	public void setShowInSecondaryMenu(Boolean showInSecondaryMenu) {
		this.showInSecondaryMenu = showInSecondaryMenu;
	}

}
