package com.hkc.mdw.web.wbs.demo.ws;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class HttpSoapClient {

	public static String postDownloadJson(String path, String post){
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write(post);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void doHttpPost(String url, Map<String, String> headers) {
		HttpURLConnection httpConn = null;
		OutputStream out = null;
		String returnXml = null;
		try {
			httpConn = (HttpURLConnection) new URL(url).openConnection();
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			if(headers != null) {
				Set<Entry<String, String>> entrySet = headers.entrySet();
				for(Entry<String, String> entry : entrySet) {
					httpConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			httpConn.connect();

			out = httpConn.getOutputStream(); // 获取输出流对象
			out.flush();
			out.close();
			int code = httpConn.getResponseCode(); // 用来获取服务器响应状态
			String tempString = null;
			StringBuffer rt = new StringBuffer();
			BufferedReader reader = null;
			if (code == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(),
								"UTF-8"));
			} else {
				reader = new BufferedReader(
						new InputStreamReader(httpConn.getErrorStream(),
								"UTF-8"));
			}
			
			while ((tempString = reader.readLine()) != null) {
				rt.append(tempString);
			}
			if (null != reader) {
				reader.close();
			}
			
			returnXml = rt.toString();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(returnXml);
	}
	
	public static void doSoapPost(String url, String xml) {
		HttpURLConnection httpConn = null;
		OutputStream out = null;
		String returnXml = null;
		try {
			httpConn = (HttpURLConnection) new URL(url).openConnection();
			httpConn.setRequestProperty("Content-Type",
					"text/xml; charset=utf-8");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.connect();

			out = httpConn.getOutputStream(); // 获取输出流对象
			if(xml != null && "".equals(xml)) {
				httpConn.getOutputStream().write(xml.getBytes()); // 将要提交服务器的SOAP请求字符流写入输出流
			}
			out.flush();
			out.close();
			int code = httpConn.getResponseCode(); // 用来获取服务器响应状态
			String tempString = null;
			StringBuffer rt = new StringBuffer();
			BufferedReader reader = null;
			if (code == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(),
								"UTF-8"));
			} else {
				reader = new BufferedReader(
						new InputStreamReader(httpConn.getErrorStream(),
								"UTF-8"));
			}
			
			while ((tempString = reader.readLine()) != null) {
				rt.append(tempString);
			}
			if (null != reader) {
				reader.close();
			}
			
			returnXml = rt.toString();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		System.out.println(returnXml);
	}
	
	public static void main(String[] args) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.demo.wbs.web.mdw.hkc.com/\">")
//		.append("<soapenv:Header>")
//		.append("<user>hkc</user>")
//		.append("<password>Hkc0755</password>")
//		.append("<token>15ea36104f1e32581039b0147ac8d567</token>")
//		.append("<method>2c9087a85ea357f0015ea361860c000b</method>")
//		.append("</soapenv:Header>")
//		.append("<soapenv:Body>")
//		.append("<ws:say>")
//		.append("<arg0>ycs</arg0>")
//		.append("</ws:say>")
//		.append("</soapenv:Body>")
//		.append("</soapenv:Envelope>");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("user", "hkc");
		headers.put("password", "Hkc0755");
		headers.put("token", "15fc8ca0174f9014c7505954f128b53f");
		headers.put("method", "2c9080e15f96b095015fc8ca9eae000d");
		postDownloadJson("http://hkcmdw.hkc.com/hkcmdw/cxf/rest/studentService/status", 
				"user=hkc&password=Hkc0755&token=15fc8ca0174f9014c7505954f128b53f");
		//doSoapPost("http://172.16.0.96/hkcmdw/cxf/mdwWService", sb.toString());
	}
}
