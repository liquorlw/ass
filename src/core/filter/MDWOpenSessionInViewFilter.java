package com.hkc.mdw.core.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

import com.hkc.mdw.core.xml.SessionInViewFilterXml;

public class MDWOpenSessionInViewFilter extends OpenSessionInViewFilter {

	public static Map<String, List<String>> beanNameMap;
    private String requestURI;
    private String contextPath;
    private static final Log logger = LogFactory.getLog(MDWOpenSessionInViewFilter.class);

    @Override
    protected String getSessionFactoryBeanName()
    {
        initBeanName();
        
        if (null != beanNameMap && StringUtil.isNotEmpty(requestURI))
        {
            for (Map.Entry<String, List<String>> entry : beanNameMap.entrySet())
            {
                List<String> values = entry.getValue();
                for (String s : values)
                {
                    if (requestURI.startsWith(s))
                    {
                        String actualBeanName = entry.getKey();
                        
                        if (logger.isInfoEnabled())
                        {
                            logger.info("To match item of '" + actualBeanName + "' name, request http uri: " + requestURI);
                        }
                        
                        return actualBeanName;
                    }
                }
            }
        }
        
        if (logger.isDebugEnabled())
        {
            logger
                    .debug("Doesn't match item of 'sessionFactoryBeanName', default bean name: "
                            + DEFAULT_SESSION_FACTORY_BEAN_NAME);
        }
        
        return DEFAULT_SESSION_FACTORY_BEAN_NAME;
    }

    public void initBeanName()
    {
        if (null == beanNameMap)
        {
            beanNameMap = new HashMap<String, List<String>>();
            
            // 默认的session工厂名称为sessionFactory
            SessionInViewFilterXml xmlUtil = new SessionInViewFilterXml("sessionInViewFilter.xml");
            beanNameMap = xmlUtil.getAllElementValues("sessionFactory");
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, FilterChain filterChain) 
            throws ServletException, IOException
    {
        requestURI = httpServletRequest.getRequestURI();
        contextPath = httpServletRequest.getContextPath();
        
        if (StringUtil.isNotEmpty(contextPath) && requestURI.startsWith(contextPath))
        {
            requestURI = requestURI.replaceFirst(contextPath, "");
        }
        super.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
    }
}
