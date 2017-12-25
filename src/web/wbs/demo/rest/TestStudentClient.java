package com.hkc.mdw.web.wbs.demo.rest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class TestStudentClient {

	public static void main(String[] args) throws Exception {
		GetMethod get = new GetMethod(
				"http://localhost:8080/hkcmdw/cxf/rest/studentService/status?user=hkc&password=Hkc0755&token=15ea34e7b36fc1f6aa045174453b0a26&method=2c9087a85ea34a89015ea34f1eb10001");
		get.setRequestHeader("accept", "application/json");
		get.setRequestHeader("user", "hkc");
		get.setRequestHeader("password", "Hkc0755");
		get.setRequestHeader("token", "");
		get.setRequestHeader("method", "");
		HttpClient hc = new HttpClient();
		hc.getParams().setContentCharset("UTF-8"); // 设置编码
		int code = hc.executeMethod(get);
		System.err.println(" 返回的状态码：" + code);
		if (code == 200) {
			String str = get.getResponseBodyAsString();
			System.err.println(" 返回信息：\n" + str);
		} else {
			
		}
		get.releaseConnection();
	}
}
