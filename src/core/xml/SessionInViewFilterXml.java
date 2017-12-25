package com.hkc.mdw.core.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.hkc.mdw.core.util.FileUtil;

/**
 * 解析sessionInViewFilter.xml文件.
 */
public class SessionInViewFilterXml
{

	private static final Log logger = LogFactory.getLog(SessionInViewFilterXml.class);

	private String filename;

	private Document doc = null;
	
	private Map propertyCache;

	/**
	 * 构造方法
	 * 
	 * @param fileName
	 *            指定配置文件的文件名
	 */
	public SessionInViewFilterXml(String fileName) 
	{
		filename = fileName;
	}

	/**
	 * 初始化XML文件Document
	 */
	private void init()
    {
        try
        {
            if (doc == null)
            {
                propertyCache = new HashMap();
                doc = new SAXReader().read(FileUtil.getInputStream(filename));
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 返回指定属性的值
	 * 
	 * @param name
	 *            需要取得的属性的名称
	 * @return String 需要取得的属性的值
	 */
	public String getProperty(String name) 
	{
		init();

		// 先尝试从缓冲中读取属性
		if (propertyCache.containsKey(name)) {
			return (String) propertyCache.get(name);
		}

		// 如果缓冲中没有，再去XML中寻找
		Element element = getPropertyElement(name);

		if (logger.isDebugEnabled() && (element == null)) 
		{
			logger.debug("null element name is " + name);
		}

		String value = element.getText();

		if (!StringUtils.isEmpty(value)) 
		{
			// 把取到的值加入Cache，以使得下次取得时加快速度
			value = value.trim();
			propertyCache.put(name, value);
		}

		return value;
	}

	/**
	 * 以Map形式返回一个父属性下所有的子属性 如果没有子属性，则返回一个Null 例如：当前属性有<tt>X.Y.A</tt>,
	 * <tt>X.Y.B</tt>, and <tt>X.Y.C</tt> 那么属性<tt>X.Y</tt>的子属性就有<tt>A</tt>,
	 * <tt>B</tt>, and
	 * 
	 * @param parent
	 *            父属性的名称
	 * @return Map 所有的子属性，以子属性的名称为键值
	 */
	public Map getChildrenProperties(String parent) 
	{
		init();

		// 先尝试从缓冲中读取属性
		if (propertyCache.containsKey(parent)) {
			return (Map) propertyCache.get(parent);
		}

		Map childs = null;
		Element element = getPropertyElement(parent);

		// 我们找到对应的属性，返回其所有子节点的值
		List children = element.elements();

		if ((children != null) && (children.size() != 0)) {
			childs = new HashMap();

			int childCount = children.size();
			Element child = null;

			for (int i = 0; i < childCount; i++) {
				child = (Element) children.get(i);
				childs.put(child.getName(), child.getText());
			}
		}

		propertyCache.put(parent, childs);

		return childs;
	}

	/**
	 * 以数组形式返回一个父属性下所有的子属性的<b>值</b> 如果没有子属性，则返回一个空数组 例如：当前属性有<tt>X.Y.A</tt>,
	 * <tt>X.Y.B</tt>, and <tt>X.Y.C</tt> 那么属性<tt>X.Y</tt>的子属性就有<tt>A</tt>,
	 * <tt>B</tt>, and
	 * 
	 * @param parent
	 *            父属性的名称
	 * @return String[] 所有的子属性的值
	 */
	public String[] getChildrenPropertiesValue(String parent) {
		init();

		// 先尝试从缓冲中读取属性
		if (propertyCache.containsKey(parent)) {
			return (String[]) propertyCache.get(parent);
		}

		String[] childs = null;
		Element element = getPropertyElement(parent);

		// 我们找到对应的属性，返回其所有子节点的值
		List children = element.elements();

		if ((children != null) && (children.size() != 0)) {
			int childCount = children.size();

			childs = new String[childCount];

			Element child = null;

			for (int i = 0; i < childCount; i++) {
				child = (Element) children.get(i);
				childs[i] = child.getText();
			}
		}

		propertyCache.put(parent, childs);

		return childs;
	}

	/**
	 * 设置一个属性的值 如果这个值不存在就自动创建它
	 * 
	 * @param name
	 *            需要设置的属性的名称
	 * @param value
	 *            需要设置的属性的值
	 */
	public void setProperty(String name, String value) {
		init();

		// 把该属性及值放入缓冲
		propertyCache.put(name, value);

		String[] propName = StringUtils.split(name, ".");

		// 由上至下搜索属性
		Element element = doc.getRootElement();

		for (int i = 0; i < propName.length; i++) {
			// 如果没有找到相应属性就创建它
			if (element.element(propName[i]) == null) {
				element.addElement(propName[i]);
			}

			element = element.element(propName[i]);
		}

		// 设置属性的值
		element.setText(value);

		// 保存更改的结果
		saveProperties();
	}

	/**
	 * 删除指定属性
	 * 
	 * @param name
	 *            需要删除的属性的名称
	 */
	public void deleteProperty(String name) {
		init();

		Element element = getPropertyElement(name);

		element.remove(element);
		saveProperties();
	}

	/**
	 * 保存属性到文件
	 */
	private synchronized void saveProperties() {
		OutputStream os = null;

		try {
			File file = FileUtil.getFile(filename);
			os = new FileOutputStream(file);

			XMLWriter outputter = new XMLWriter(os, OutputFormat
					.createPrettyPrint());

			outputter.write(doc);
			outputter.close();
		} catch (Exception e) {
			logger.error("set configure file failed", e);
		} finally {
			try {
				os.close();
			} catch (Exception io) {
			}
		}
	}

	/**
	 * 根据属性名称获得它所在的Element
	 * 
	 * @param name
	 *            属性名称
	 * @return Element 属性所在的Element
	 */
	private Element getPropertyElement(String name) {
		String[] propName = StringUtils.split(name, ".");

		// 由上至下搜索属性
		Element element = doc.getRootElement();

		for (int i = 0; (i < propName.length) && (element != null); i++) {
			element = element.element(propName[i]);
		}

		return element;
	}

	/**
	 * 设置配置文件名称路径
	 * 
	 * @param fileName
	 *            文件名称路径
	 */
	public void setFilename(String fileName) {
		filename = fileName;
	}

	/**
	 * 重新读取配置文件
	 */
	public void reload() {
		doc = null;
	}
	
	public Map<String, List<String>> getAllElementValues(String name)
	{
	    init();
        Element element = doc.getRootElement();
        List childs = element.elements(name);
        Map<String, List<String>> rtnMap = new LinkedHashMap<String, List<String>>();
        
        if (null != childs && childs.size() > 0)
        {
            for (Object o : childs)
            {
                Element childElement = (Element) o;
                Attribute attribute = childElement.attribute("name");
                String attributeName = attribute.getValue();
                
                List sChilds = childElement.elements();
                if (null != sChilds && sChilds.size() > 0)
                {
                    List<String> uriValues = new ArrayList<String>();
                    
                    for (Object o2 : sChilds)
                    {
                        Element uriElement = (Element) o2;
                        uriValues.add(uriElement.getTextTrim());
                    }
                    
                    if (!rtnMap.containsKey(attributeName))
                    {
                        rtnMap.put(attributeName, uriValues);
                    }
                }
            }
        }
        
	    return rtnMap;
	}
}
