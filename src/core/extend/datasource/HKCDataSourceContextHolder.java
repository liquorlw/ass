package com.hkc.mdw.core.extend.datasource;

import org.jeecgframework.core.extend.datasource.DataSourceType;
/**
 * 
 * <p>Project:hkcmdw</p>
 * <p>Package:com.hkc.mdw.core.extend.datasource</p>
 * <p>Title: HKCDataSourceContextHolder</p>
 * <p>Description: </p>
 * <p>Copyright:1996-2050 </p>
 * <p>Company: 惠科股份有限公司</p>
 * @author yuchengsong
 * @version 1.0.0
 * @since jdk1.7.0_80
 * @see
 * Create Time：Sep 15, 20174:25:22 PM
 * Modified By: 
 * Modified Time:
 */
public class HKCDataSourceContextHolder {

	private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>();
	
	public static void setDataSourceType(DataSourceType dataSourceType){
		contextHolder.set(dataSourceType);
	}
	
	public static DataSourceType getDataSourceType(){
		return (DataSourceType) contextHolder.get();
	}
	
	public static void clearDataSourceType(){
		contextHolder.remove();
	}
}
