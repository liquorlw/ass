package com.hkc.mdw.web.entity.api;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "t_s_api")
public class APIEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -207403564001162876L;

	@Excel(name = "接口名称")
	private String apiName;
	@Excel(name = "接口类路径")
	private String apiPath;
	@Excel(name = "接口描述")
	private String apiDesc;
	@Excel(name = "接口令牌码")
	private String apiToken;
	@Excel(name = "接口类型")
	private String apiType;
	@Excel(name = "接口状态")
	private String apiStatus;
	private String createId;
	@Excel(name = "创建人")
	private String createName;
	@Excel(name = "创建时间", format = "yyyy-MM-dd hh:mm:ss")
	private Date createTime;
	@Excel(name = "修改时间", format = "yyyy-MM-dd hh:mm:ss")
	private Date updateTime;

	@Column(name = "APINAME", nullable = false, length = 100)
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	@Column(name = "APIPATH", nullable = false, length = 200)
	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	@Column(name = "APIDESC", nullable = false, length = 250)
	public String getApiDesc() {
		return apiDesc;
	}

	public void setApiDesc(String apiDesc) {
		this.apiDesc = apiDesc;
	}

	@Column(name = "APITOKEN", nullable = false, length = 36)
	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	@Column(name = "APITYPE", nullable = false, length = 10)
	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	@Column(name = "APISTATUS", nullable = false, length = 1)
	public String getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(String apiStatus) {
		this.apiStatus = apiStatus;
	}

	@Column(name = "CREATEID", nullable = false, length = 32)
	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name = "CREATENAME", nullable = false, length = 50)
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Column(name = "CREATETIME", nullable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "UPDATETIME", nullable = true)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
