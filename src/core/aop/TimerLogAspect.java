package com.hkc.mdw.core.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.hkc.mdw.common.service.LogService;
import com.hkc.mdw.core.util.ExceptionUtil;
import com.hkc.mdw.core.util.IDGenerator;
import com.hkc.mdw.web.entity.timer.TimerLog;

@Aspect
public class TimerLogAspect {

	@Autowired
	private LogService mdwLogService;
	
	@Pointcut("execution(* com.hkc.mdw.web.timer..*.*(..))")
	public void selectAll() {
		
	}
	
	@AfterReturning(pointcut = "selectAll()", returning = "retVal")
	public void afterReturningAdvice(JoinPoint point, Object retVal) {
		Object target = point.getTarget();
		TimerLog log = new TimerLog();
		log.setId(IDGenerator.generateID());
		log.setTimerID(target.getClass().getSimpleName());
		log.setLogDate(new Date());
		if(retVal != null) {
			log.setLogInfo("Returning:" + retVal.toString());
		} else {
			log.setLogInfo(target.getClass().getSimpleName() + " run success!");
		}
		mdwLogService.addLog(log);
	}

	@AfterThrowing(pointcut = "selectAll()", throwing = "ex")
	public void AfterThrowingAdvice(JoinPoint point, Throwable ex) {
		Object target = point.getTarget();
		TimerLog log = new TimerLog();
		log.setId(IDGenerator.generateID());
		log.setTimerID(target.getClass().getSimpleName());
		log.setLogDate(new Date());
		log.setLogInfo(ExceptionUtil.printStackTraceToString(ex));
		mdwLogService.addLog(log);
	}
}
