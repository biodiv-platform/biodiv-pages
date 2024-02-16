package com.strandls.pages.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

/**
 * This class represent the page structure
 * 
 * @author vilay
 *
 */
@Entity
@Table(name = "page")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3955248341059248359L;

	private Long id;
	private String title;
	private String content;
	private String description;

	private String socialPreview;

	private Long userGroupId;
	private Long languageId;

	private Long parentId;
	private Integer pageIndex;

	private PageType pageType;
	private String url;

	private Long autherId;
	private String autherName;

	private Timestamp date;
	private Boolean sticky;
	private Boolean showInFooter;
	private Boolean showInMenu;

	private Boolean isDeleted;
	private Boolean allowComments;

	private List<PageGallerySlider> gallerySilder;

	public Page() {
		super();
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false)
	@Type(type = "text")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false)
	@Type(type = "text")
	@JsonIgnore
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "description")
	@Type(type = "text")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "social_preview")
	@Type(type = "text")
	public String getSocialPreview() {
		return socialPreview;
	}

	public void setSocialPreview(String socialPreview) {
		this.socialPreview = socialPreview;
	}

	@Column(name = "user_group_id")
	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	@Column(name = "language_id", nullable = false)
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	@Column(name = "parent_id", nullable = false)
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "pageIndex", nullable = false)
	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Column(name = "pageType", nullable = false)
	public PageType getPageType() {
		return pageType;
	}

	public void setPageType(PageType pageType) {
		this.pageType = pageType;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "auther_id")
	public Long getAutherId() {
		return autherId;
	}

	public void setAutherId(Long autherId) {
		this.autherId = autherId;
	}

	@Column(name = "auther_name")
	public String getAutherName() {
		return autherName;
	}

	public void setAutherName(String autherName) {
		this.autherName = autherName;
	}

	@Column(name = "date", nullable = false)
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Column(name = "sticky", nullable = false)
	public Boolean getSticky() {
		return this.sticky;
	}

	public void setSticky(Boolean sticky) {
		this.sticky = sticky;
	}

	@Column(name = "show_in_footer", nullable = false)
	public Boolean getShowInFooter() {
		return showInFooter;
	}

	public void setShowInFooter(Boolean showInFooter) {
		this.showInFooter = showInFooter;
	}

	@JsonIgnore
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Transient
	public List<PageGallerySlider> getGallerySilder() {
		return gallerySilder;
	}

	public void setGallerySilder(List<PageGallerySlider> gallerySilder) {
		this.gallerySilder = gallerySilder;
	}

	@Column(name = "allow_comments", columnDefinition = "boolean default true")
	public Boolean getAllowComments() {
		return allowComments;
	}

	public void setAllowComments(Boolean allowComments) {
		this.allowComments = allowComments;
	}

	@Column(name = "show_in_menu", columnDefinition = "boolean default false")
	public Boolean getShowInMenu() {
		return showInMenu;
	}

	public void setShowInMenu(Boolean showInMenu) {
		this.showInMenu = showInMenu;
	}

}
