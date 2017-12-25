package com.hkc.mdw.core.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeecgframework.core.extend.datasource.DataSourceType;
import org.jeecgframework.web.system.pojo.base.SParamsEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkc.mdw.core.constant.HKCConstant;
import com.hkc.mdw.core.enums.AspectMthodEnum;
import com.hkc.mdw.core.extend.datasource.HKCDataSourceContextHolder;

/**
 * DataSource自由切换AOP
 * <p>Project:hkcmdw</p>
 * <p>Package:com.hkc.mdw.core.aop</p>
 * <p>Title: DataSourceAspect</p>
 * <p>Description: </p>
 * <p>Copyright:1996-2050 </p>
 * <p>Company: 惠科股份有限公司</p>
 * @author yuchengsong
 * @version 1.0.0
 * @since jdk1.7.0_80
 * @see
 * Create Time：Sep 15, 2017 4:57:12 PM
 * Modified By: 
 * Modified Time:
 */
@Aspect
public class DataSourceAspect {
	private static final Logger logger = Logger
			.getLogger(DataSourceAspect.class);
	
	@Autowired
	private SystemService systemService;
	
	@Pointcut("execution(* com.hkc.mdw.web.service.ds.*.*(..))")
	public void selectAll() {
	}

	@Before("selectAll()")
	public void before(JoinPoint point) {
		SParamsEntity sparams = (SParamsEntity)systemService.getList(
				SParamsEntity.class).get(0);
		if(HKCConstant.YES_STR.equals(sparams.getDsIsSyn())) {
			Object target = point.getTarget();
			String method = point.getSignature().getName();

			Class<?>[] classz = target.getClass().getInterfaces();

			Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
					.getMethod().getParameterTypes();
			try {
				Method m = classz[0].getMethod(method, parameterTypes);
				if (m != null) {
					String name = m.getName();
					boolean isExists = false;
					AspectMthodEnum[] enums = AspectMthodEnum.values();
					for (AspectMthodEnum e : enums) {
						if (name.startsWith(e.name())) {
							HKCDataSourceContextHolder
									.setDataSourceType(DataSourceType.master);
							isExists = true;
							break;
						}
					}
					if(!isExists) {
						HKCDataSourceContextHolder
						.setDataSourceType(DataSourceType.slave);
					}
				} else {
					HKCDataSourceContextHolder
							.setDataSourceType(DataSourceType.slave);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
	}
}
