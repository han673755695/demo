package com.example.demo.domain;

public class UploadImg {

	private String name;
	
	private String base64;
	
	

	public UploadImg(String name, String base64) {
		super();
		this.name = name;
		this.base64 = base64;
	}

	public UploadImg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	@Override
	public String toString() {
		return "UploadImg [name=" + name + ", base64=" + base64 + "]";
	}
	
}
