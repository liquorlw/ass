package com.hkc.mdw.common.service;

import com.hkc.mdw.common.entity.MDWTSType;
import com.hkc.mdw.common.entity.MDWTSTypegroup;


/**
 *
 * @author  张代浩
 *
 */
public interface MDWSystemService extends MDWCommonService {
	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public MDWTSType getType(String typecode,String typename,MDWTSTypegroup tsTypegroup);
	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public MDWTSTypegroup getTypeGroup(String typegroupcode,String typgroupename);
	/**
	 * 根据编码获取字典组
	 *
	 * @param typegroupCode
	 * @return
	 */
	public MDWTSTypegroup getTypeGroupByCode(String typegroupCode);
	/**
	 * 对数据字典进行缓存
	 */
	public void initAllTypeGroups();

	/**
	 * 刷新字典缓存
	 * @param type
	 */
	public void refleshTypesCach(MDWTSType type);
	/**
	 * 刷新字典分组缓存
	 */
	public void refleshTypeGroupCach();
	
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 * @param TUser 操作人
	 */
	public void addLog(String logContent, Short loglevel,Short operatetype);

}
