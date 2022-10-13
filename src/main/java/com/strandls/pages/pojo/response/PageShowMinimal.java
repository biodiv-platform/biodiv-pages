package com.strandls.pages.pojo.response;

import java.util.List;

import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.PageGallerySlider;
import com.strandls.pages.pojo.PageType;

public class PageShowMinimal {

	private Long id;
	private String title;
	private String content;
	private String description;
	private String socialPreview;

	private String url;
	private PageType pageType;
	private Boolean isDeleted;
	
	private List<PageGallerySlider> galleryData;

	public PageShowMinimal() {
		super();
	}

	public PageShowMinimal(Page newsletter) {
		this.id = newsletter.getId();
		this.title = newsletter.getTitle();
		this.content = newsletter.getContent();
		this.galleryData = newsletter.getGallerySilder();
		this.url = newsletter.getUrl();
		this.description = newsletter.getDescription();
		this.setSocialPreview(newsletter.getSocialPreview());
		this.pageType = newsletter.getPageType();
		this.isDeleted = newsletter.getIsDeleted();
	}

	public PageShowMinimal(Long id, String title, String content, List<PageGallerySlider> galleryData,
			String description, String url,String socialPreview ,PageType pageType, Boolean isDeleted) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.galleryData = galleryData;
		this.url = url;
		this.description = description;
		this.setSocialPreview(socialPreview);
		this.pageType = pageType;
		this.isDeleted = isDeleted;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSocialPreview() {
		return socialPreview;
	}

	public void setSocialPreview(String socialPreview) {
		this.socialPreview = socialPreview;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PageType getPageType() {
		return pageType;
	}

	public void setPageType(PageType pageType) {
		this.pageType = pageType;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
