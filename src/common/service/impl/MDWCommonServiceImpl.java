package com.hkc.mdw.common.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.HqlQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.common.DBTable;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.common.model.json.ImportFile;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.easyui.Autocomplete;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hkc.mdw.common.entity.MDWTSAttachment;
import com.hkc.mdw.common.service.MDWCommonService;

@Service("mdwCommonService")
@Transactional("mdwTransactionManager")
public class MDWCommonServiceImpl implements MDWCommonService {
	public ICommonDao mDWCommonDao = null;

	/**
	 * 获取所有数据库表
	 * 
	 * @return
	 */
	public List<DBTable> getAllDbTableName() {
		return mDWCommonDao.getAllDbTableName();
	}

	public Integer getAllDbTableSize() {
		return mDWCommonDao.getAllDbTableSize();
	}

	@Resource
	public void setMDWCommonDao(ICommonDao mDWCommonDao) {
		this.mDWCommonDao = mDWCommonDao;
	}

	public <T> Serializable save(T entity) {
		return mDWCommonDao.save(entity);
	}

	public <T> void saveOrUpdate(T entity) {
		mDWCommonDao.saveOrUpdate(entity);

	}

	public <T> void delete(T entity) {
		mDWCommonDao.delete(entity);

	}

	/**
	 * 删除实体集合
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteAllEntitie(Collection<T> entities) {
		mDWCommonDao.deleteAllEntitie(entities);
	}

	/**
	 * 根据实体名获取对象
	 */
	public <T> T get(Class<T> class1, Serializable id) {
		return mDWCommonDao.get(class1, id);
	}

	/**
	 * 根据实体名返回全部对象
	 * 
	 * @param <T>
	 * @param hql
	 * @param size
	 * @return
	 */
	public <T> List<T> getList(Class clas) {
		return mDWCommonDao.loadAll(clas);
	}

	/**
	 * 根据实体名获取对象
	 */
	public <T> T getEntity(Class entityName, Serializable id) {
		return mDWCommonDao.getEntity(entityName, id);
	}

	/**
	 * 根据实体名称和字段名称和字段值获取唯一记录
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public <T> T findUniqueByProperty(Class<T> entityClass,
			String propertyName, Object value) {
		return mDWCommonDao.findUniqueByProperty(entityClass, propertyName,
				value);
	}

	/**
	 * 按属性查找对象列表.
	 */
	public <T> List<T> findByProperty(Class<T> entityClass,
			String propertyName, Object value) {

		return mDWCommonDao.findByProperty(entityClass, propertyName, value);
	}

	/**
	 * 加载全部实体
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public <T> List<T> loadAll(final Class<T> entityClass) {
		return mDWCommonDao.loadAll(entityClass);
	}

	public <T> T singleResult(String hql) {
		return mDWCommonDao.singleResult(hql);
	}

	/**
	 * 删除实体主键ID删除对象
	 * 
	 * @param <T>
	 * @param entities
	 */
	public <T> void deleteEntityById(Class entityName, Serializable id) {
		mDWCommonDao.deleteEntityById(entityName, id);
	}

	/**
	 * 更新指定的实体
	 * 
	 * @param <T>
	 * @param pojo
	 */
	public <T> void updateEntitie(T pojo) {
		mDWCommonDao.updateEntitie(pojo);

	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findByQueryString(String hql) {
		return mDWCommonDao.findByQueryString(hql);
	}

	/**
	 * 根据sql更新
	 * 
	 * @param query
	 * @return
	 */
	public int updateBySqlString(String sql) {
		return mDWCommonDao.updateBySqlString(sql);
	}

	/**
	 * 根据sql查找List
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findListbySql(String query) {
		return mDWCommonDao.findListbySql(query);
	}

	/**
	 * 通过属性称获取实体带排序
	 * 
	 * @param <T>
	 * @param clas
	 * @return
	 */
	public <T> List<T> findByPropertyisOrder(Class<T> entityClass,
			String propertyName, Object value, boolean isAsc) {
		return mDWCommonDao.findByPropertyisOrder(entityClass, propertyName,
				value, isAsc);
	}

	/**
	 * 
	 * cq方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final CriteriaQuery cq, final boolean isOffset) {
		return mDWCommonDao.getPageList(cq, isOffset);
	}

	/**
	 * 返回DataTableReturn模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataTableReturn getDataTableReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		return mDWCommonDao.getDataTableReturn(cq, isOffset);
	}

	/**
	 * 返回easyui datagrid模型
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public DataGridReturn getDataGridReturn(final CriteriaQuery cq,
			final boolean isOffset) {
		return mDWCommonDao.getDataGridReturn(cq, isOffset);
	}

	/**
	 * 
	 * hqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageList(final HqlQuery hqlQuery,
			final boolean needParameter) {
		return mDWCommonDao.getPageList(hqlQuery, needParameter);
	}

	/**
	 * 
	 * sqlQuery方式分页
	 * 
	 * @param cq
	 * @param isOffset
	 * @return
	 */
	public PageList getPageListBySql(final HqlQuery hqlQuery,
			final boolean isToEntity) {
		return mDWCommonDao.getPageListBySql(hqlQuery, isToEntity);
	}

	public Session getSession()

	{
		return mDWCommonDao.getSession();
	}

	public List findByExample(final String entityName,
			final Object exampleEntity) {
		return mDWCommonDao.findByExample(entityName, exampleEntity);
	}

	/**
	 * 通过cq获取全部实体
	 * 
	 * @param <T>
	 * @param cq
	 * @return
	 */
	public <T> List<T> getListByCriteriaQuery(final CriteriaQuery cq,
			Boolean ispage) {
		return mDWCommonDao.getListByCriteriaQuery(cq, ispage);
	}

	/**
	 * 文件上传
	 * 
	 * @param request
	 */
	public <T> T uploadFile(UploadFile uploadFile) {
		return mDWCommonDao.uploadFile(uploadFile);
	}

	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile)

	{
		return mDWCommonDao.viewOrDownloadFile(uploadFile);
	}

	/**
	 * 生成XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 * @return
	 */
	public HttpServletResponse createXml(ImportFile importFile) {
		return mDWCommonDao.createXml(importFile);
	}

	/**
	 * 解析XML文件
	 * 
	 * @param fileName
	 *            XML全路径
	 */
	public void parserXml(String fileName) {
		mDWCommonDao.parserXml(fileName);
	}

	public List<ComboTree> comTree(List<TSDepart> all, ComboTree comboTree) {
		return mDWCommonDao.comTree(all, comboTree);
	}

	public List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel,
			List in, boolean recursive) {
		return mDWCommonDao.ComboTree(all, comboTreeModel, in, recursive);
	}

	/**
	 * 构建树形数据表
	 */
	public List<TreeGrid> treegrid(List all, TreeGridModel treeGridModel) {
		return mDWCommonDao.treegrid(all, treeGridModel);
	}

	/**
	 * 获取自动完成列表
	 * 
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getAutoList(Autocomplete autocomplete) {
		StringBuffer sb = new StringBuffer("");
		for (String searchField : autocomplete.getSearchField().split(",")) {
			sb.append("  or " + searchField + " like '%"
					+ autocomplete.getTrem() + "%' ");
		}
		String hql = "from " + autocomplete.getEntityName() + " where 1!=1 "
				+ sb.toString();
		return mDWCommonDao.getSession().createQuery(hql).setFirstResult(
				autocomplete.getCurPage() - 1).setMaxResults(
				autocomplete.getMaxRows()).list();
	}

	public Integer executeSql(String sql, List<Object> param) {
		return mDWCommonDao.executeSql(sql, param);
	}

	public Integer executeSql(String sql, Object... param) {
		return mDWCommonDao.executeSql(sql, param);
	}

	public Integer executeSql(String sql, Map<String, Object> param) {
		return mDWCommonDao.executeSql(sql, param);
	}

	public Object executeSqlReturnKey(String sql, Map<String, Object> param) {
		return mDWCommonDao.executeSqlReturnKey(sql, param);
	}

	public List<Map<String, Object>> findForJdbc(String sql, int page, int rows) {
		return mDWCommonDao.findForJdbc(sql, page, rows);
	}

	public List<Map<String, Object>> findForJdbc(String sql, Object... objs) {
		return mDWCommonDao.findForJdbc(sql, objs);
	}

	public List<Map<String, Object>> findForJdbcParam(String sql, int page,
			int rows, Object... objs) {
		return mDWCommonDao.findForJdbcParam(sql, page, rows, objs);
	}

	public <T> List<T> findObjForJdbc(String sql, int page, int rows,
			Class<T> clazz) {
		return mDWCommonDao.findObjForJdbc(sql, page, rows, clazz);
	}

	public Map<String, Object> findOneForJdbc(String sql, Object... objs) {
		return mDWCommonDao.findOneForJdbc(sql, objs);
	}

	public Long getCountForJdbc(String sql) {
		return mDWCommonDao.getCountForJdbc(sql);
	}

	public Long getCountForJdbcParam(String sql, Object[] objs) {
		return mDWCommonDao.getCountForJdbcParam(sql, objs);
	}

	public <T> void batchSave(List<T> entitys) {
		this.mDWCommonDao.batchSave(entitys);
	}

	/**
	 * 通过hql 查询语句查找对象
	 * 
	 * @param <T>
	 * @param query
	 * @return
	 */
	public <T> List<T> findHql(String hql, Object... param) {
		return this.mDWCommonDao.findHql(hql, param);
	}

	public <T> List<T> pageList(DetachedCriteria dc, int firstResult,
			int maxResult) {
		return this.mDWCommonDao.pageList(dc, firstResult, maxResult);
	}

	public <T> List<T> findByDetached(DetachedCriteria dc) {
		return this.mDWCommonDao.findByDetached(dc);
	}

	/**
	 * 调用存储过程
	 */
	public <T> List<T> executeProcedure(String procedureSql, Object... params) {
		return this.mDWCommonDao.executeProcedure(procedureSql, params);
	}

	@Override
	public void multiUpload(List<MDWTSAttachment> attaLst, UploadFile uploadFile)
			throws Exception {
		this.mDWCommonDao.multiUpload(attaLst, uploadFile);
	}

}
