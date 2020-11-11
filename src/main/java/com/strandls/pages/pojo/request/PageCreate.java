package com.strandls.pages.pojo.request;

import java.sql.Timestamp;

import com.strandls.pages.pojo.PageType;

public class PageCreate {
	
	private String title;
	private String content;
	private String description;
	private Long userGroupId;
	private Long languageId;
	private Long parentId;
	private Integer pageIndex; 
	private PageType pageType;
	private String url;
	private Long autherId;
	private String autherName;
	private Timestamp date;
	private Boolean Sticky;
	private Boolean showInFooter;

	public PageCreate() {
		super();
	}

	public PageCreate(String title, String content, String description, Long userGroupId, Long languageId,
			Long parentId, Integer pageIndex, PageType pageType, String url, Long autherId, String autherName,
			Timestamp date, Boolean sticky, Boolean showInFooter) {
		super();
		this.title = title;
		this.content = content;
		this.description = description;
		this.userGroupId = userGroupId;
		this.languageId = languageId;
		this.parentId = parentId;
		this.pageIndex = pageIndex;
		this.pageType = pageType;
		this.url = url;
		this.autherId = autherId;
		this.autherName = autherName;
		this.date = date;
		Sticky = sticky;
		this.showInFooter = showInFooter;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
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

	public Long getAutherId() {
		return autherId;
	}

	public void setAutherId(Long autherId) {
		this.autherId = autherId;
	}

	public String getAutherName() {
		return autherName;
	}

	public void setAutherName(String autherName) {
		this.autherName = autherName;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Boolean getSticky() {
		return Sticky;
	}

	public void setSticky(Boolean sticky) {
		Sticky = sticky;
	}

	public Boolean getShowInFooter() {
		return showInFooter;
	}

	public void setShowInFooter(Boolean showInFooter) {
		this.showInFooter = showInFooter;
	}
}
