package com.hkc.mdw.common.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkc.mdw.common.entity.MDWTSLog;
import com.hkc.mdw.common.entity.MDWTSType;
import com.hkc.mdw.common.entity.MDWTSTypegroup;
import com.hkc.mdw.common.service.MDWSystemService;


@Service("mdwSystemService")
@Transactional("mdwTransactionManager")
public class MDWSystemComServiceImpl extends MDWCommonServiceImpl implements
		MDWSystemService {


	@Override
	public MDWTSTypegroup getTypeGroup(String typegroupcode, String typgroupename) {
		
		return null;
	}

	@Override
	public MDWTSTypegroup getTypeGroupByCode(String typegroupCode) {
		
		return null;
	}

	@Override
	public void initAllTypeGroups() {
		
	}

	@Override
	public void refleshTypeGroupCach() {
		
	}

	@Override
	public MDWTSType getType(String typecode, String typename, MDWTSTypegroup tsTypegroup) {
		//TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode", typecode,tsTypegroup.getId());
		List<MDWTSType> ls = mDWCommonDao.findHql("from TSType where typecode = ? and typegroupid = ?", 
				typecode,tsTypegroup.getId());
		MDWTSType actType = null;
		if (ls == null || ls.size()==0) {
			actType = new MDWTSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			mDWCommonDao.save(actType);
		}else{
			actType = ls.get(0);
		}
		return actType;
	}

	@Override
	public void refleshTypesCach(MDWTSType type) {
		
	}

	@Override
	public void addLog(String logContent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		MDWTSLog log = new MDWTSLog();
		log.setLogcontent(logContent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperatetime(DateUtils.gettimestamp());
		log.setUserID(ResourceUtil.getSessionUserName().getId());
		log.setUserName(ResourceUtil.getSessionUserName().getUserName());
		mDWCommonDao.save(log);
		
	}

}
