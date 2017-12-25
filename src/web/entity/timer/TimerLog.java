package com.hkc.mdw.web.entity.timer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

@Entity
@Table(name = "T_TIMER_LOG")
public class TimerLog extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6391346322670882968L;
	private String timerID;
	private String logInfo;
	private Date logDate;
	
	@Column(name = "LOGDATE", nullable = false)
	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	@Column(name = "TIMERID", nullable = false, length = 32)
	public String getTimerID() {
		return timerID;
	}

	public void setTimerID(String timerID) {
		this.timerID = timerID;
	}
	
	@Column(name = "LOGINFO", nullable = false, length = 2000)
	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
}
