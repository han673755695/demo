package com.example.demo.domain;

import java.util.Date;

public class RoleMenu {
	private String id;

	private String roleid;

	private String menuid;

	private String createuser;

	private Date createdate;

	private String updateuser;

	private Date updatedate;

	private String active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid == null ? null : roleid.trim();
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid == null ? null : menuid.trim();
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

	@Override
	public String toString() {
		return "RoleMenu [id=" + id + ", roleid=" + roleid + ", menuid=" + menuid + ", createuser=" + createuser
				+ ", createdate=" + createdate + ", updateuser=" + updateuser + ", updatedate=" + updatedate
				+ ", active=" + active + "]";
	}
}