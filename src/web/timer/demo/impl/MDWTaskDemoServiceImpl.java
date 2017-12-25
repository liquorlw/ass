package com.hkc.mdw.web.timer.demo.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.hkc.mdw.web.timer.demo.MDWTaskDemoService;

@Service("mdwTaskDemoService")
public class MDWTaskDemoServiceImpl implements MDWTaskDemoService {

	@Override
	public void work() {
		org.jeecgframework.core.util.LogUtil.info(new Date().getTime());
		org.jeecgframework.core.util.LogUtil.info("-------中台系统正在爆发之中----------");
	}

}
