package com.hkc.mdw.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.web.system.pojo.base.SParamsEntity;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 系统参数设置控制器
 * <p>Project:hkcmdw</p>
 * <p>Package:com.hkc.mdw.web.controller</p>
 * <p>Title: SParamsController</p>
 * <p>Description: </p>
 * <p>Copyright:1996-2050 </p>
 * <p>Company: 惠科股份有限公司</p>
 * @author yuchengsong
 * @version 1.0.0
 * @since jdk1.7.0_80
 * @see
 * Create Time：Sep 22, 2017 2:37:06 PM
 * Modified By: 
 * Modified Time:
 */
@Controller
@RequestMapping("/sparamsController")
public class SParamsController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(SParamsEntity sparams, HttpServletRequest req) {
		sparams = (SParamsEntity)systemService.getList(SParamsEntity.class).get(0);
		req.setAttribute("sparams", sparams);
		return new ModelAndView("hkc/sparams/updateSparams");
	}
	
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(SParamsEntity sparams, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		StringBuffer sb = new StringBuffer();
		sb.append("系统参数");
		
		try{
			sb.append("修改");
			systemService.saveOrUpdate(sparams);
			sb.append("成功");
			systemService.addLog(sb.toString(), Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		}catch(Exception e){
			sb.append("失败");
			throw new BusinessException(e.getMessage());
		}
		
		j.setMsg(sb.toString());
		
		return j;
	}
}
