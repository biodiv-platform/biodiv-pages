package com.strandls.pages.pojo;

public class Page {

	private Long id;
	private String title;
	private String content;

	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Page(Newsletter newsletter) {
		this.id = newsletter.getId();
		this.title = newsletter.getTitle();
		this.content = newsletter.getNewsitem();
	}

	public Page(Long id, String title, String content) {
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
