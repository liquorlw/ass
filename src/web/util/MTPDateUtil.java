package com.hkc.mdw.web.util;

import java.util.Date;

public class MTPDateUtil {

	public static String isExpired(Date expectCpletTime, Date factFinishTime, Date curDate, int status) {
		String key = null;
		if(factFinishTime != null) {
			int cmp = factFinishTime.compareTo(expectCpletTime);
			if(cmp == 1) {
				//延迟完成
				key = "mtp.task.delay";
			} else {
				//完成
				key = "mtp.task.complete";
			}
		} else {
			int cmp = curDate.compareTo(expectCpletTime);
			if(cmp == 1) {
				//未完成，已延迟
				key = "mtp.task.expired";
			} else {
				if(status == 1) {
					//预完成，待审核
					key = "mtp.task.verifycmp";
				} else {
					//进行中...
					key = "mtp.task.verify";
				}
			}
		}
		return key;
	}
	
}
