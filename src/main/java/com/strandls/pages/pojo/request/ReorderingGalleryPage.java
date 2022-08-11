package com.strandls.pages.pojo.request;

public class ReorderingGalleryPage {
	private Long galleryId;
	private Long displayOrder;

	/**
	 * 
	 */
	public ReorderingGalleryPage() {
		super();
	}

	/**
	 * @param galleryId
	 * @param displayOrder
	 */
	public ReorderingGalleryPage(Long galleryId, Long displayOrder) {
		super();
		this.galleryId = galleryId;
		this.displayOrder = displayOrder;
	}

	public Long getGalleryId() {
		return galleryId;
	}

	public void setGalleryId(Long galleryId) {
		this.galleryId = galleryId;
	}

	public Long getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}

}
