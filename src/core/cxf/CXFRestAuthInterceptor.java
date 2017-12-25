package com.hkc.mdw.core.cxf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.jeecgframework.p3.core.utils.common.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hkc.mdw.common.service.MDWSystemService;
import com.hkc.mdw.core.constant.HKCConstant;
import com.hkc.mdw.web.entity.api.APIEntity;
/**
 * wbs权限认证拦截器
 * <p>Project:hkcmdw</p>
 * <p>Package:com.hkc.mdw.core.cxf</p>
 * <p>Title: CXFAuthInterceptor</p>
 * <p>Description: </p>
 * <p>Copyright:1996-2050 </p>
 * <p>Company: 惠科股份有限公司</p>
 * @author yuchengsong
 * @version 1.0.0
 * @since jdk1.7.0_80
 * @see
 * Create Time：Sep 20, 2017 10:33:45 AM
 * Modified By: 
 * Modified Time:
 */
public class CXFRestAuthInterceptor extends AbstractPhaseInterceptor<Message> {

	private static final Logger logger = Logger.getLogger(CXFRestAuthInterceptor.class);
	private MDWSystemService mdwSystemService;
	
	public MDWSystemService getMdwSystemService() {
		return mdwSystemService;
	}

	public void setMdwSystemService(MDWSystemService mdwSystemService) {
		this.mdwSystemService = mdwSystemService;
	}

	public CXFRestAuthInterceptor() {
		super(Phase.PRE_PROTOCOL);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleMessage(Message msg) throws Fault {
		String logMsg = null;
		Map<String, String> reqParams = new HashMap<String, String>();
		if(HKCConstant.HTTP_GET.equals(msg.get(Message.HTTP_REQUEST_METHOD))) {
			String params = (String) msg.get(Message.QUERY_STRING);
			if(StringUtils.isNotEmpty(params)) {
				String[] paramArray = params.split("&");
				for(String param : paramArray) {
					String[] ss = param.split("=");
					reqParams.put(ss[0], ss[1]);
				}
			} else {
				logMsg = "restful request's header is null, please add header!";
				throw new Fault(new Exception(logMsg));
			}
		} else {
			InputStream is = msg.getContent(InputStream.class);
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			try {
				while ((line = in.readLine()) != null) {
				    //post参数，json格式
				    buffer.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e);
			}
			JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
			reqParams = JSONObject.toJavaObject(jsonObject, Map.class);
		}
		
		if(reqParams.size() <= 0) {
			logMsg = "restful request's header is null, please add header!";
			throw new Fault(new Exception(logMsg));
		} else {
			String user = reqParams.get(HKCConstant.USER_STR);
			if(StringUtils.isEmpty(user)) {
				logMsg = "restful request's user is null, please add user to request!";
				throw new Fault(new Exception(logMsg));
			}
			
			String password = reqParams.get(HKCConstant.PASSWORD_STR);
			if(StringUtils.isEmpty(password)) {
				logMsg = "restful request's password is null, please add password to request!";
				throw new Fault(new Exception(logMsg));
			}
			
			String token = reqParams.get(HKCConstant.TOKEN_STR);
			if(StringUtils.isEmpty(token)) {
				logMsg = "restful request's token is null, please add token to request!";
				throw new Fault(new Exception(logMsg));
			}
			
			String method = reqParams.get(HKCConstant.METHOD_STR);
			if(StringUtils.isEmpty(method)) {
				logMsg = "restful request's method is null, please add method to request!";
				throw new Fault(new Exception(logMsg));
			}
			
			APIEntity apiEntity = mdwSystemService.getEntity(APIEntity.class, method);
			if(apiEntity == null) {
				logMsg = "restful Request Method value is invalid!";
				throw new Fault(new Exception(logMsg));
			}
			
			String status = apiEntity.getApiStatus();
			if(!HKCConstant.WBS_REST_DEPLOY_STATUS.equals(status)) {
				logMsg = "Request Method is unavailable， please Contact HKCMDW System Administrator!";
				throw new Fault(new Exception(logMsg));
			}
			
			String factToken = apiEntity.getApiToken();
			if(HKCConstant.HKC_USER_NAME.equals(user) 
					&& HKCConstant.HKC_USER_PWD.equals(password) 
					&& factToken.equals(factToken)) {
				logMsg = "cxf restful auth validate success!";
				logger.info(logMsg);
			} else {
				logMsg = "Restful Auth Validate Failed, please check request header's user or password or method!";
				throw new Fault(new Exception(logMsg));
			}
		}
		
		
	}

}
