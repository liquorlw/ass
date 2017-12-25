package com.hkc.mdw.web.timer.demo.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hkc.mdw.common.service.MDWSystemService;
import com.hkc.mdw.web.timer.demo.MDWTaskSaveLogService;

@Service("mdwTaskSaveLogService")
public class MDWTaskSaveLogServiceImpl implements MDWTaskSaveLogService {
	/**
	 * 定时任务</br>
	 * 判断当前日期，如果为1号即创建当月表，例如一月一号：t_log_1</br>
	 * 并将上月最后一天的日志信息存储到对应月份表
	 * 如果为其他日期即收集日志信息，将前一天日志信息存储到对应月份表
	 * 如果。。。
	 * */
	private MDWSystemService mdwSystemService;
	@Autowired
	public void setMtpSystemService(MDWSystemService mdwSystemService) {
		this.mdwSystemService = mdwSystemService;
	}
	@Override
	public void work() {
		//第一次运行需要将除今天以外所有的表进行分类,暂时手动分类
		Calendar a=Calendar.getInstance();
		Date date = a.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String str = df.format(date).toString();
		String[] d = str.split("-");
		if("1".equals(d[2])){
			//1.定时创建日志月表
			a.add(Calendar.DATE, -1);
			String sql = "create table t_log_"+a.get(Calendar.YEAR)+"_"+a.get(Calendar.MONTH)+"(" +//
					"id varchar2(32)," +
					"apiortimerid varchar2(100)," +
					"invoker varchar2(100)," +
					"loginfo varchar2(4000)," +
					"logdate date)";
			mdwSystemService.executeSql(sql);
			//2.将前一天数据存储
			//存储表t_timer_log
			String sql2 = "insert into t_log_"+d[0]+"_"+d[1]+"(id,apiortimerid,loginfo,logdate) "+
			"select * from t_timer_log " +
			"where to_date(to_char(logdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') " +
			"between " +
			"to_date('"+str+" 00:00:01','yyyy-mm-dd hh24:mi:ss') " +
			"and " +
			"to_date('"+str+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			mdwSystemService.executeSql(sql2);
			//存储表t_api_log
			String sql3 = "insert into t_log_"+d[0]+"_"+d[1]+" "+
			"select * from t_api_log " +
			"where to_date(to_char(logdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') " +
			"between " +
			"to_date('"+str+" 00:00:01','yyyy-mm-dd hh24:mi:ss') " +
			"and " +
			"to_date('"+str+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			mdwSystemService.executeSql(sql3);
		}else{
			//2.定时获取日志并存储至相应的日志月表
			//存储表t_timer_log
			a.add(Calendar.DATE, -1);
			String sql2 = "insert into t_log_"+d[0]+"_"+d[1]+"(id,apiortimerid,loginfo,logdate) "+
			"select * from t_timer_log " +
			"where to_date(to_char(logdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') " +
			"between " +
			"to_date('"+str+" 00:00:01','yyyy-mm-dd hh24:mi:ss') " +
			"and " +
			"to_date('"+str+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			mdwSystemService.executeSql(sql2);
			//存储表t_api_log
			String sql3 = "insert into t_log_"+d[0]+"_"+d[1]+" "+
			"select * from t_api_log " +
			"where to_date(to_char(logdate,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') " +
			"between " +
			"to_date('"+str+" 00:00:01','yyyy-mm-dd hh24:mi:ss') " +
			"and " +
			"to_date('"+str+" 23:59:59','yyyy-mm-dd hh24:mi:ss') ";
			mdwSystemService.executeSql(sql3);
		}
	}
}
