package com.hkc.mdw.common.entity;
// default package

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * TTypegroup entity.
 */
@Entity
@Table(name = "t_s_typegroup")
public class MDWTSTypegroup extends IdEntity implements java.io.Serializable {
	private String typegroupname;
	private String typegroupcode;
	private List<MDWTSType> TSTypes = new ArrayList<MDWTSType>();
	@Column(name = "typegroupname", length = 50)
	public String getTypegroupname() {
		return this.typegroupname;
	}

	public void setTypegroupname(String typegroupname) {
		this.typegroupname = typegroupname;
	}

	@Column(name = "typegroupcode", length = 50)
	public String getTypegroupcode() {
		return this.typegroupcode;
	}

	public void setTypegroupcode(String typegroupcode) {
		this.typegroupcode = typegroupcode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSTypegroup")
	public List<MDWTSType> getTSTypes() {
		return this.TSTypes;
	}

	public void setTSTypes(List<MDWTSType> TSTypes) {
		this.TSTypes = TSTypes;
	}

}