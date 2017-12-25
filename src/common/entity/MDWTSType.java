package com.hkc.mdw.common.entity;
// default package

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 通用类型字典表
 *  @author  张代浩
 */
@Entity
@Table(name = "t_s_type")
public class MDWTSType extends IdEntity implements java.io.Serializable {

	private MDWTSTypegroup TSTypegroup;//类型分组
	private MDWTSType TSType;//父类型
	private String typename;//类型名称
	private String typecode;//类型编码
	//private TaskEntity task;

	//	private List<TPProcess> TSProcesses = new ArrayList();
	private List<MDWTSType> TSTypes =new ArrayList<MDWTSType>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typegroupid")
	public MDWTSTypegroup getTSTypegroup() {
		return this.TSTypegroup;
	}

	public void setTSTypegroup(MDWTSTypegroup TSTypegroup) {
		this.TSTypegroup = TSTypegroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typepid")
	public MDWTSType getTSType() {
		return this.TSType;
	}

	public void setTSType(MDWTSType TSType) {
		this.TSType = TSType;
	}

	@Column(name = "typename", length = 50)
	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Column(name = "typecode", length = 50)
	public String getTypecode() {
		return this.typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSType")
//	public List<TPProcess> getTSProcesses() {
//		return this.TSProcesses;
//	}
//
//	public void setTSProcesses(List<TPProcess> TSProcesses) {
//		this.TSProcesses = TSProcesses;
//	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSType")
	public List<MDWTSType> getTSTypes() {
		return this.TSTypes;
	}

	public void setTSTypes(List<MDWTSType> TSTypes) {
		this.TSTypes = TSTypes;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "TASK_ID")
//	public TaskEntity getTask() {
//		return task;
//	}
//
//	public void setTask(TaskEntity task) {
//		this.task = task;
//	}
}