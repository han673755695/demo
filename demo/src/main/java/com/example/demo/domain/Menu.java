package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

public class Menu implements Serializable{
	private String id;

	private String parentId;

	private Date createDate;

	private Date updateDate;

	private String name;

	private String status;

	private String sort;

	private String isParent;

	private String url;

	private String level;

	private String icon;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort == null ? null : sort.trim();
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent == null ? null : isParent.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", parentId=" + parentId + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", name=" + name + ", status=" + status + ", sort=" + sort + ", isParent=" + isParent + ", url=" + url
				+ ", level=" + level + ", icon=" + icon + "]";
	}

}