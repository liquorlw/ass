package com.hkc.mdw.core.extend.datasource;

import java.util.Map;

import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
/**
 * 动态Datasource，根据Service中方法名称的规则来改变
 * <p>Project:hkcmdw</p>
 * <p>Package:com.hkc.mtp.core.extend.datasource</p>
 * <p>Title: HKCDynamicDataSource</p>
 * <p>Description: </p>
 * <p>Copyright:1996-2050 </p>
 * <p>Company: 惠科股份有限公司</p>
 * @author yuchengsong
 * @version 1.0.0
 * @since jdk1.7.0_80
 * @see
 * Create Time：Sep 14, 20173:23:18 PM
 * Modified By: 
 * Modified Time:
 */
public class HKCDynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 该方法必须覆盖，获取实际业务中的datasource类型
	 * @author yuchengsong
	 * create time:Sep 14, 2017 3:24:31 PM
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		DataSourceType dataSourceType = HKCDataSourceContextHolder.getDataSourceType();
		return dataSourceType;
	}

	public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
		super.setDataSourceLookup(dataSourceLookup);
	}

	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
	}
	
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);
	}
}
