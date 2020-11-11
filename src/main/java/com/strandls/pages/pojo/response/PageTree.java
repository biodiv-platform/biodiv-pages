package com.strandls.pages.pojo.response;

import java.util.List;

import com.strandls.pages.pojo.Page;

public class PageTree {

	private Long id;
	private String title;
	private Long parentId;
	private Integer pageIndex;
	private List<PageTree> childs;

	public PageTree() {
		super();
	}

	public PageTree(Page page) {
		super();
		this.id = page.getId();
		this.title = page.getTitle();
		this.parentId = page.getParentId();
		this.pageIndex = page.getPageIndex();
		this.childs = new PageArrayList();
	}
	
	public PageTree(Long id, String title, Long parentId, Integer index, List<PageTree> childs) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.pageIndex = index;
		this.childs = childs;
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

	public List<PageTree> getChilds() {
		return childs;
	}

	public void setChilds(List<PageTree> childs) {
		this.childs = childs;
	}
}
