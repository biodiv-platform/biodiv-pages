package com.strandls.pages.pojo.response;

import java.sql.Timestamp;

import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.PageType;

public class PageShowFull {

	private Long id;
	private String title;
	private String content;
	private String description;

	private Long userId;
	private Long languageId;
	private PageType pageType;
	private String url;

	private Long parentId;
	private Integer pageIndex;

	private Timestamp date;
	private Boolean sticky;
	private Boolean showInFooter;

	public PageShowFull() {
		super();
	}

	public PageShowFull(Page page) {
		this.id = page.getId();
		this.title = page.getTitle();
		this.content = page.getContent();
		this.description = page.getDescription();
		this.userId = page.getAutherId();
		this.languageId = page.getLanguageId();
		this.pageType = page.getPageType();
		this.url = page.getUrl();
		this.parentId = page.getParentId();
		this.pageIndex = page.getPageIndex();
		this.date = page.getDate();
		this.sticky = page.getSticky();
		this.showInFooter = page.getShowInFooter();
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
}
