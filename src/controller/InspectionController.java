package com.hkc.mdw.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.id.UUIDGenerator;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hkc.mdw.common.service.MDWSystemService;
import com.hkc.mdw.core.constant.HKCConstant;
import com.hkc.mdw.web.entity.inspection.InspectionEntity;
import com.hkc.mdw.web.service.ds.SwitchDataSource;

/**
 * Inspection控制类：包含Inspection创建、修改、删除、日志查看
 * <p>Project:hkcass</p>
 * <p>Package:com.hkc.mdw.web.controller</p>
 * <p>Title: InspectionController</p>
 * <p>Description: </p>
 * <p>Copyright:1996-2050 </p>
 * <p>Company: 惠科股份有限公司</p>
 * @author liwei
 * @version 1.0.0
 * @since jdk1.7.0_80
 * @see
 * Create Time：2017/12/16
 * Modified By: 
 * Modified Time:
 */
@Controller
@RequestMapping("/inspectionController")
public class InspectionController  extends BaseController {
	
	private static final Logger logger = Logger.getLogger(InspectionController.class);
	
	private MDWSystemService mdwSystemService;
	private SystemService systemService;
	
	private SwitchDataSource switchDataSource;
	@Autowired
	public void setMdwSystemService(MDWSystemService mdwSystemService) {
		this.mdwSystemService = mdwSystemService;
	}
    @Autowired
	public void setSwitchDataSource(SwitchDataSource switchDataSource) {
		this.switchDataSource = switchDataSource;
	}
    
    @Autowired
    public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
    
	@RequestMapping(params = "goList")
	public String goList(HttpServletRequest request) {
		return "hkc/inspection/inspectionList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(InspectionEntity inspection, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(InspectionEntity.class, dataGrid);
		try {
			// 查询条件组装器
			HqlGenerateUtil.installHqlMtp(cq, inspection, request
					.getParameterMap());
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		switchDataSource.query();
		this.mdwSystemService.getDataGridReturn(cq, true);
		
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(InspectionEntity inspection, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(inspection.getId())) {
			inspection = mdwSystemService.getEntity(InspectionEntity.class, inspection.getId());
			inspection.setId(null);
		} else {
			TSUser user = ResourceUtil.getSessionUserName();
			inspection.setOperaterId(user.getId());
			inspection.setInspectionStatus("0");
		}
		req.getSession().setAttribute("name", ResourceUtil.getSessionUserName().getRealName());
		req.setAttribute("inspection", inspection);
		req.setAttribute("createTime", DateUtils.formatDate(new Date(), 
				"yyyy-MM-dd HH:mm:ss"));
		return new ModelAndView("hkc/inspection/createOrUpdateInspection");
	}
	
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(InspectionEntity inspection, HttpServletRequest request){
		Object entity = mdwSystemService.findByProperty(InspectionEntity.class,"inspectionId" , inspection.getInspectionId());
		inspection.setId(UUIDGenerator.UUID_GEN_STRATEGY);
		AjaxJson j = new AjaxJson();
		StringBuffer sb = new StringBuffer();
		if(entity != null){
			sb.append("产品已存在");
		}else{
			sb.append("商检信息");
			try {
				switchDataSource.add();
				if (HKCConstant.UPDATE_STR.equals(request.getParameter("flag"))) {
					sb.append("修改");

					mdwSystemService.saveOrUpdate(inspection);
				} else {
					sb.append("添加");
					mdwSystemService.save(inspection);
				}
				sb.append("成功");
				mdwSystemService.addLog(sb.toString(), Globals.Log_Type_DEL,
						Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				sb.append("失败");
				logger.error(e);
				throw new BusinessException(e.getMessage());
			}
		}
		j.setMsg(sb.toString());
		return j;
	}
	
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(InspectionEntity inspection, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		inspection = mdwSystemService.getEntity(InspectionEntity.class, inspection.getId());
		String message = "删除商检信息成功";

		try {
			switchDataSource.add();
			this.mdwSystemService.delete(inspection);
			mdwSystemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除商检信息失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(InspectionEntity inspection, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(inspection.getId())) {
			inspection = mdwSystemService.getEntity(InspectionEntity.class, inspection.getId());
		}
		req.setAttribute("inspection", inspection);
		req.setAttribute("flag", HKCConstant.UPDATE_STR);
		req.getSession().setAttribute("name", ResourceUtil.getSessionUserName().getRealName());
		return new ModelAndView("hkc/inspection/createOrUpdateInspection");
	}
	
	@RequestMapping(params = "doCommit")
	@ResponseBody
	public AjaxJson doCommit(InspectionEntity inspection, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		inspection = mdwSystemService.getEntity(InspectionEntity.class, inspection.getId());
		String message = "提交商检信息成功";

		try {
			switchDataSource.add();
			this.mdwSystemService.delete(inspection);
			mdwSystemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除商检信息失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
