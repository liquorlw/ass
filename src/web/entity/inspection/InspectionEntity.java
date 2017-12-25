package com.hkc.mdw.web.entity.inspection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name = "t_s_inspection")
public class InspectionEntity extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = -207403563335162876L;
	
	@Excel(name = "创建时间", format = "yyyy-MM-dd hh:mm:ss")
	private Date createTime;
	@Excel(name = "地点")
	private String inspectionAddr;
	@Excel(name = "操作人id")
	private String operaterId;
	@Excel(name = "故障描述")
	private String inspectionDesc;
	@Excel(name = "序号")
	private String inspectionId;
	@Excel(name = "售后状态")
	private String inspectionStatus;
	@Excel(name = "其他")
	private String others;
	
	@Column(name = "CREATETIME", nullable = false, length = 50)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "INSPECTIONADDR", nullable = false, length = 100)
	public String getInspectionAddr() {
		return inspectionAddr;
	}
	public void setInspectionAddr(String inspectionAddr) {
		this.inspectionAddr = inspectionAddr;
	}
	
	@Column(name = "OPERATERID", nullable = false, length = 50)
	public String getOperaterId() {
		return operaterId;
	}
	public void setOperaterId(String operaterId) {
		this.operaterId = operaterId;
	}
	
	@Column(name = "INSPECTIONDESC", nullable = false, length = 250)
	public String getInspectionDesc() {
		return inspectionDesc;
	}
	public void setInspectionDesc(String inspectionDesc) {
		this.inspectionDesc = inspectionDesc;
	}
	
	@Column(name = "INSPECTIONID", nullable = false, length = 50)
	public String getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	
	@Column(name = "INSPECTIONSTATUS", nullable = false, length = 1)
	public String getInspectionStatus() {
		return inspectionStatus;
	}
	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}
	
	@Column(name = "OTHERS", nullable = false, length = 250)
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}

	
}
