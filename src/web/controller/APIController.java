package com.hkc.mdw.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hkc.mdw.common.service.MDWSystemService;
import com.hkc.mdw.core.constant.HKCConstant;
import com.hkc.mdw.core.util.IDGenerator;
import com.hkc.mdw.web.entity.api.APIEntity;
import com.hkc.mdw.web.entity.api.APILog;
import com.hkc.mdw.web.service.ds.SwitchDataSource;
/**
 * API控制类：包含API创建、修改、删除、日志查看
 * <p>Project:hkcmdw</p>
 * <p>Package:com.hkc.mdw.web.controller</p>
 * <p>Title: APIController</p>
 * <p>Description: </p>
 * <p>Copyright:1996-2050 </p>
 * <p>Company: 惠科股份有限公司</p>
 * @author yuchengsong
 * @version 1.0.0
 * @since jdk1.7.0_80
 * @see
 * Create Time：Sep 18, 2017 11:00:56 AM
 * Modified By: 
 * Modified Time:
 */
@Controller
@RequestMapping("/apiController")
public class APIController extends BaseController {

	private static final Logger logger = Logger.getLogger(APIController.class);
	private MDWSystemService mdwSystemService;
	//private SystemService systemService;
	private SwitchDataSource switchDataSource;
	
	@Autowired
	public void setSwitchDataSource(SwitchDataSource switchDataSource) {
		this.switchDataSource = switchDataSource;
	}
	
	@Autowired
	public void setMtpSystemService(MDWSystemService mdwSystemService) {
		this.mdwSystemService = mdwSystemService;
	}

//	@Autowired
//	public void setSystemService(SystemService systemService) {
//		this.systemService = systemService;
//	}
	
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(APIEntity api, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(api.getId())) {
			api = mdwSystemService.getEntity(APIEntity.class, api.getId());
			api.setId(null);
		} else {
			TSUser user = ResourceUtil.getSessionUserName();
			api.setCreateId(user.getId());
			api.setCreateName(user.getRealName());
			api.setApiToken(IDGenerator.generateID());
			api.setApiStatus("0");
			api.setApiType("wbs");
		}
		req.setAttribute("api", api);
		req.setAttribute("createTime", DateUtils.formatDate(new Date(), 
				"yyyy-MM-dd HH:mm:ss"));
		return new ModelAndView("hkc/api/createOrUpdateAPI");
	}
	
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(APIEntity api, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(api.getId())) {
			api = mdwSystemService.getEntity(APIEntity.class, api.getId());
		}
		req.setAttribute("api", api);
		req.setAttribute("flag", HKCConstant.UPDATE_STR);
		return new ModelAndView("hkc/api/createOrUpdateAPI");
	}
	
	@RequestMapping(params = "goList")
	public String goList(HttpServletRequest request) {
		return "hkc/api/apiList";
	}
	
	@RequestMapping(params = "goLog")
	public String goLog(HttpServletRequest request) {
		request.setAttribute("apiId", request.getParameter("apiId"));
		return "hkc/api/apiLogList";
	}
	
	@RequestMapping(params = "goAPIStore")
	public String goAPIStore(HttpServletRequest request) {
		return "hkc/api/apiStoreList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridstore")
	public void datagridstore(APIEntity api, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(APIEntity.class, dataGrid);
		cq.eq("apiStatus", "1");

		try {
			// 查询条件组装器
			HqlGenerateUtil.installHqlMtp(cq, api, request
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(APIEntity api, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(APIEntity.class, dataGrid);
		String userId = ResourceUtil.getSessionUserName().getId();
		cq.eq("createId", userId);

		try {
			// 查询条件组装器
			HqlGenerateUtil.installHqlMtp(cq, api, request
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagridLog")
	public void datagridLog(APILog apiLog, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(APILog.class, dataGrid);
		cq.addOrder("logDate", SortDirection.desc);

		try {
			// 查询条件组装器
			HqlGenerateUtil.installHqlMtp(cq, apiLog, request
					.getParameterMap());
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		switchDataSource.query();
		this.mdwSystemService.getDataGridReturn(cq, true);

		if(dataGrid.getResults() != null && dataGrid.getResults().size() > 0) {
			for(Object o : dataGrid.getResults()) {
				APILog log = (APILog)o;
				StringBuffer sb = new StringBuffer();
				sb.append("<div title=\"").append(log.getLogInfo()).append("\"")
				.append(">").append(log.getLogInfo()).append("</div>");
				log.setLogInfo(sb.toString());
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(APIEntity api, HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		StringBuffer sb = new StringBuffer();
		sb.append("接口");
		
		try{
			switchDataSource.add();
			if(HKCConstant.UPDATE_STR.equals(request.getParameter("flag"))) {
				sb.append("修改");
				mdwSystemService.saveOrUpdate(api);
			} else {
				sb.append("添加");
				mdwSystemService.save(api);
			}
			sb.append("成功");
			mdwSystemService.addLog(sb.toString(), Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		}catch(Exception e){
			sb.append("失败");
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(sb.toString());
		
		return j;
	}
	
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(APIEntity api, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		api = mdwSystemService.getEntity(APIEntity.class, api.getId());
		String message = "删除接口成功";

		try {
			switchDataSource.add();
			this.mdwSystemService.delete(api);
			mdwSystemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除接口失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "批量删除接口成功";
		try {
			switchDataSource.add();
			for(String id : ids.split(",")) {
				APIEntity api = mdwSystemService.getEntity(APIEntity.class, id);
				this.mdwSystemService.delete(api);
			}
			mdwSystemService.addLog(message, Globals.Log_Type_DEL,
					Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			message = "批量删除接口失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "exportXls")
	public String exportXls(APIEntity api, HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(APIEntity.class, dataGrid);
		cq.eq("createId", ResourceUtil.getSessionUserName().getId());
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				api, request.getParameterMap());
		switchDataSource.query();
		List<APIEntity> apiLst = this.mdwSystemService
				.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "接口列表");
		modelMap.put(NormalExcelConstants.CLASS, APIEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("接口列表",
				"导出人:" + ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, apiLst);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
}
