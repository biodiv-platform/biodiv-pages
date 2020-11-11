package com.strandls.pages.pojo.request;

public class PageTreeUpdate {
	private Long id;
	private Long parentId;
	private Integer pageIndex;

	public PageTreeUpdate() {
		super();
	}

	public PageTreeUpdate(Long id, Long parentId, Integer pageIndex) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.pageIndex = pageIndex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
