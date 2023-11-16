package com.strandls.pages.pojo.response;

import java.util.List;

import com.strandls.pages.pojo.Page;

public class PageTree {

	private Long id;
	private String title;
	private Long parentId;
	private Integer pageIndex;
	private List<PageTree> children;
	private Boolean showInPrimaryHeader;
	private Boolean showInSecondaryHeader;

	public PageTree() {
		super();
	}

	public PageTree(Page page) {
		super();
		this.id = page.getId();
		this.title = page.getTitle();
		this.parentId = page.getParentId();
		this.pageIndex = page.getPageIndex();
		this.children = new PageArrayList();
		this.showInPrimaryHeader = page.getShowInPrimaryHeader();
		this.showInSecondaryHeader = page.getShowInSecondaryHeader();
	}

	public PageTree(Long id, String title, Long parentId, Integer index, List<PageTree> childs,
			Boolean showInPrimaryHeader, Boolean showInSecondaryHeader) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.pageIndex = index;
		this.children = childs;
		this.showInPrimaryHeader = showInPrimaryHeader;
		this.showInSecondaryHeader = showInSecondaryHeader;
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

	public List<PageTree> getChildren() {
		return children;
	}

	public void setChildren(List<PageTree> children) {
		this.children = children;
	}

	public Boolean getShowInPrimaryHeader() {
		return showInPrimaryHeader;
	}

	public void setShowInPrimaryHeader(Boolean showInPrimaryHeader) {
		this.showInPrimaryHeader = showInPrimaryHeader;
	}

	public Boolean getShowInSecondaryHeader() {
		return showInSecondaryHeader;
	}

	public void setShowInSecondaryHeader(Boolean showInSecondaryHeader) {
		this.showInSecondaryHeader = showInSecondaryHeader;
	}
}
