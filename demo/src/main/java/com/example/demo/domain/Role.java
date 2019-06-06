package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Role implements Serializable {
	private String id;

	private String rolename;

	private String bewrite;

	private String createuser;

	private Date createdate;

	private String updateuser;

	private Date updatedate;

	private String active;

	private List<Menu> menuList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename == null ? null : rolename.trim();
	}

	public String getBewrite() {
		return bewrite;
	}

	public void setBewrite(String bewrite) {
		this.bewrite = bewrite == null ? null : bewrite.trim();
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser == null ? null : createuser.trim();
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser == null ? null : updateuser.trim();
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", rolename=" + rolename + ", bewrite=" + bewrite + ", createuser=" + createuser
				+ ", createdate=" + createdate + ", updateuser=" + updateuser + ", updatedate=" + updatedate
				+ ", active=" + active + ", menuList=" + menuList + "]";
	}

}