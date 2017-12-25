package com.hkc.mdw.web.wbs.demo.ws;

import org.jeecgframework.core.util.LogUtil;
import org.springframework.stereotype.Service;

@Service("mdwDemoWService")
public class MDWWServiceImpl implements MDWWServiceI {
	
	@Override
	public String say(String context) {
		return "you say context:" + context;
	}

	@Override
	public String sayHello() {
		return "sayHello";
	}

	@Override
	public void sayUI() {
		LogUtil.info("UI");
	}

	@Override
	public String sayDemo(DemoEntity demo) {
		return "you say:" + demo.getName() + "'s age " + demo.getAge();
	}

}
