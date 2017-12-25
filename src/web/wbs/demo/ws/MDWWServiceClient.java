package com.hkc.mdw.web.wbs.demo.ws;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * 
 * @ClassName: JeecgWServiceClient
 * @Description: cxf客户端测试类
 * @author huangzq
 * @date 2015-12-31 下午05:44:50
 * 
 */
public class MDWWServiceClient {

	public static void main11(String[] args) {
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
		bean.setServiceClass(MDWWServiceI.class);
		bean.setAddress("http://localhost:8080/jeecg/cxf/JeecgWService");
		MDWWServiceI client = (MDWWServiceI) bean.create();
		//System.out.println(client.sayHello());
	}
	
	public static void main(String[] args) {
		JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
		bean.setServiceClass(MDWWServiceI.class);
		bean.setAddress("http://172.16.0.96/hkcmdw/cxf/mdwWService");
		MDWWServiceI client = (MDWWServiceI) bean.create();
		System.out.println(client.sayHello());
	}
}
