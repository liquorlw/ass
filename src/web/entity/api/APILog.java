package com.hkc.mdw.web.entity.api;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

@Entity
@Table(name = "T_API_LOG")
public class APILog extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6391346322670882968L;
	private String apiID;
	private String invoker;
	private String logInfo;
	private Date logDate;
	
	@Column(name = "LOGDATE", nullable = false)
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	@Column(name = "APIID", nullable = false, length = 32)
	public String getApiID() {
		return apiID;
	}

	public void setApiID(String apiID) {
		this.apiID = apiID;
	}

	@Column(name = "INVOKER", nullable = false, length = 100)
	public String getInvoker() {
		return invoker;
	}

	public void setInvoker(String invoker) {
		this.invoker = invoker;
	}

	@Column(name = "LOGINFO", nullable = false, length = 2000)
	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
}
