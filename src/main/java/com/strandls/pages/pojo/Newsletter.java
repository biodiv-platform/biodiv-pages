package com.strandls.pages.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

/**
 * This pojo represent the page structure from newsletter.
 * @author vilay
 *
 */
@Entity
@Table(name = "newsletter")
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class Newsletter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3955248341059248359L;
	
	private Long id;
	private Long version;
	private Timestamp date;
	private String newsitem;
	private String title;
	private Boolean Sticky;
	private Long userGroupId;
	private Integer displayOrder;
	private Long languageId;
	private Long parentId;
	private Boolean showInFooter;

	public Newsletter() {
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

	@Column(name = "version")
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(name = "date")
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Column(name = "newsitem")
	@Type(type = "text")
	@JsonIgnore
	public String getNewsitem() {
		return newsitem;
	}

	public void setNewsitem(String newsitem) {
		this.newsitem = newsitem;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "sticky")
	public Boolean getSticky() {
		return Sticky;
	}

	public void setSticky(Boolean sticky) {
		this.Sticky = sticky;
	}

	@Column(name = "user_group_id")
	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	@Column(name = "display_order")
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "language_id")
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	@Column(name = "parent_id")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "show_in_footer")
	public Boolean getShowInFooter() {
		return showInFooter;
	}

	public void setShowInFooter(Boolean showInFooter) {
		this.showInFooter = showInFooter;
	}
}
