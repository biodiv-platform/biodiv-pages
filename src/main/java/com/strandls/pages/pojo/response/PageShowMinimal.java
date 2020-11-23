package com.strandls.pages.pojo.response;

import java.sql.Timestamp;

import com.strandls.pages.pojo.Page;

public class PageShowMinimal {

	private Long id;
	private String title;
	private String content;

	public PageShowMinimal() {
		super();
	}
	
	public PageShowMinimal(Page newsletter) {
		this.id = newsletter.getId();
		this.title = newsletter.getTitle();
		this.content = newsletter.getContent();
	}

	public PageShowMinimal(Long id, String title, String content, Timestamp date, Boolean sticky, Boolean showInFooter,
			Long parentId) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
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
}
