package com.strandls.pages.pojo.response;

import java.util.List;

import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.PageGallerySlider;

public class PageShowMinimal {

	private Long id;
	private String title;
	private String content;
	private List<PageGallerySlider> galleryData;

	public PageShowMinimal() {
		super();
	}
	
	public PageShowMinimal(Page newsletter) {
		this.id = newsletter.getId();
		this.title = newsletter.getTitle();
		this.content = newsletter.getContent();
		this.galleryData = newsletter.getGallerySilder();
	}

	public PageShowMinimal(Long id, String title, String content,List<PageGallerySlider> galleryData) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.galleryData = galleryData;
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

	public List<PageGallerySlider> getGalleryData() {
		return galleryData;
	}

	public void setGalleryData(List<PageGallerySlider> galleryData) {
		this.galleryData = galleryData;
	}
}
