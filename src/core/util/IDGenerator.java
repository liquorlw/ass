package com.hkc.mdw.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class IDGenerator {
	/**
	 * 生成主键（32位）
	 * 
	 * @return
	 */
	public static String generateID() {
		String rtnVal = Long.toHexString(System.currentTimeMillis());
		rtnVal += UUID.randomUUID();
		rtnVal = rtnVal.replaceAll("-", "");
		return rtnVal.substring(0, 32);
	}
	
	public static String getSerialNum() {
		Date d = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String s = sf.format(d);
		Random radom = new Random();
		int l = radom.nextInt(99);
		s += Math.abs(l);
		return s;
	}
	public static void main(String[] args) {
		for(int i=1;i<4300;i++) {
			System.out.println(generateID());
		}
	}
}
