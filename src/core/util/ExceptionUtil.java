package com.hkc.mdw.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.hkc.mdw.core.constant.HKCConstant;
/**
 * 异常辅助工具类
 * <p>Project:hkcmdw</p>
 * <p>Package:com.hkc.mdw.core.util</p>
 * <p>Title: ExceptionUtil</p>
 * <p>Description: </p>
 * <p>Copyright:1996-2050 </p>
 * <p>Company: 惠科股份有限公司</p>
 * @author yuchengsong
 * @version 1.0.0
 * @since jdk1.7.0_80
 * @see
 * Create Time：Sep 21, 2017 4:35:51 PM
 * Modified By: 
 * Modified Time:
 */
public class ExceptionUtil {

	/**
	 * 获取异常堆栈信息
	 * <p>Title:printStackTraceToString</p>
	 * <p>Description:TODO</p>
	 * @author: yuchengsong
	 * @time: Sep 21, 2017 4:36:07 PM
	 * @param:@param t
	 * @param:@return
	 * @return:String
	 * @throws
	 */
	public static String printStackTraceToString(Throwable t) {
	    StringWriter sw = new StringWriter();
	    t.printStackTrace(new PrintWriter(sw, true));
	    StringBuffer exceBuffer = sw.getBuffer();
	    String exceMsg = null;
	    if(exceBuffer.length() > HKCConstant.EXCEPITON_STACK_MAX_SIZE) {
	    	exceMsg = exceBuffer.substring(0, HKCConstant.EXCEPITON_STACK_MAX_SIZE);
	    } else {
	    	exceMsg = exceBuffer.toString();
	    }
	    return exceMsg;
	}
}
