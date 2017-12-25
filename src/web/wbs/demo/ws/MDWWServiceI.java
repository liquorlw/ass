package com.hkc.mdw.web.wbs.demo.ws;

import javax.jws.WebService;

@WebService
public interface MDWWServiceI {

	public String say(String context);
	
	public String sayHello();
	
	public void sayUI();
	
	public String sayDemo(DemoEntity demo);
	
}
