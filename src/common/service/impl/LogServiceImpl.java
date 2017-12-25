package com.hkc.mdw.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkc.mdw.common.service.LogService;
import com.hkc.mdw.common.service.MDWSystemService;
import com.hkc.mdw.web.service.ds.SwitchDataSource;

@Service("mdwLogService")
public class LogServiceImpl implements LogService {
	@Autowired
	private MDWSystemService mdwSystemService;
	@Autowired
	private SwitchDataSource switchDataSource;

	@Override
	public void addLog(Object log) {
		switchDataSource.add();
		mdwSystemService.save(log);
	}

}
