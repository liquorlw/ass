package com.hkc.mdw.core.cxf;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

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
public class CXFAuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private static final Logger logger = Logger.getLogger(CXFAuthInterceptor.class);
	private SAAJInInterceptor saa = new SAAJInInterceptor();
	private MDWSystemService mdwSystemService;
	
	public MDWSystemService getMdwSystemService() {
		return mdwSystemService;
	}

	public void setMdwSystemService(MDWSystemService mdwSystemService) {
		this.mdwSystemService = mdwSystemService;
	}

	public CXFAuthInterceptor() {
		super(Phase.PRE_PROTOCOL);
		getAfter().add(SAAJInInterceptor.class.getName());
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		SOAPMessage soapMsg = msg.getContent(SOAPMessage.class);
		if(soapMsg == null) {
			saa.handleMessage(msg);
			soapMsg = msg.getContent(SOAPMessage.class);
		}
		
		SOAPHeader head = null;
		try {
			head = soapMsg.getSOAPHeader();
		} catch (SOAPException e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		String logMsg = null;
		if(head == null) {
			logMsg = "soap head is null, please add header!";
			SOAPException soapExc = new SOAPException(logMsg);
			throw new Fault(soapExc);
		}
		
		NodeList user = head.getElementsByTagName(HKCConstant.USER_STR);
		if(user.item(0) == null) {
			logMsg = "Method user not null, please add user to header!";
			SOAPException soapExc = new SOAPException(logMsg);
			throw new Fault(soapExc);
		}
		NodeList password = head.getElementsByTagName(HKCConstant.PASSWORD_STR);
		if(password.item(0) == null) {
			logMsg = "Method password not null, please add password to header!";
			SOAPException soapExc = new SOAPException(logMsg);
			throw new Fault(soapExc);
		}
		NodeList token = head.getElementsByTagName(HKCConstant.TOKEN_STR);
		if(token.item(0) == null) {
			logMsg = "Method token not null, please add token to header!";
			SOAPException soapExc = new SOAPException(logMsg);
			throw new Fault(soapExc);
		}
		NodeList method = head.getElementsByTagName(HKCConstant.METHOD_STR);
		String factToken = null;
		if(method.item(0) == null) {
			logMsg = "Method must not null, please add method to header!";
			SOAPException soapExc = new SOAPException(logMsg);
			throw new Fault(soapExc);
		} else {
			APIEntity apiEntity = mdwSystemService.getEntity(APIEntity.class, method.item(0).getTextContent());
			if(apiEntity == null) {
				logMsg = "Header Method value is valid!";
				SOAPException soapExc = new SOAPException(logMsg);
				throw new Fault(soapExc);
			} else {
				String status = apiEntity.getApiStatus();
				if(!HKCConstant.WBS_REST_DEPLOY_STATUS.equals(status)) {
					logMsg = "Header Method is unavailable， please Contact HKCMDW System Administrator!";
					SOAPException soapExc = new SOAPException(logMsg);
					throw new Fault(soapExc);
				}
				factToken = apiEntity.getApiToken();
			}
		}
		
		if(HKCConstant.HKC_USER_NAME.equals(user.item(0).getTextContent()) 
				&& HKCConstant.HKC_USER_PWD.equals(password.item(0).getTextContent()) 
				&& factToken.equals(token.item(0).getTextContent())) {
			logMsg = "cxf auth validate success!";
			logger.info(logMsg);
		} else {
			logMsg = "Auth Validate Failed, please check header's user or password or method!";
			SOAPException soapExc = new SOAPException(logMsg);
			throw new Fault(soapExc);
		}
	}

}
