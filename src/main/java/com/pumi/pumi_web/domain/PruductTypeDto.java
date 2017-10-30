package com.pumi.pumi_web.domain;

public class PruductTypeDto {

	private String pruductTypeId      ;
	private String typeName            ;
	
	private String ImgId     ;
	private String name      ;
	private String url       ;
	private String createId  ;
	private String createTime;
	private String updateTime;
	private String ImgType             ;
	
	public String getPruductTypeId() {
		return pruductTypeId;
	}
	public void setPruductTypeId(String pruductTypeId) {
		this.pruductTypeId = pruductTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getImgId() {
		return ImgId;
	}
	public void setImgId(String imgId) {
		ImgId = imgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getImgType() {
		return ImgType;
	}
	public void setImgType(String imgType) {
		ImgType = imgType;
	}
	
	
	
}
