package com.mi_girl.excel.reader;

import java.util.List;
import java.util.Map;

import com.mi_girl.excel.common.ExcelVersion;




public interface ExcelReadConfig<T> {

	/**
	 * 实现该方法用于将map转换成实体类
	 * 其中key为属性名，value为属性值
	 * @param rowData
	 * @return
	 */
	public T convert(Map<String,String> rowData);
	
	/**
	 * 设置表头数据所在的下标
	 * @return
	 */
	public Integer sheetHeadIndex();
	
	/**
	 * 表头和属性的映射关系  如 表头的一列为:"名字" 对应实体类的属性名称为:"name"
	 * 则 key="名字"  value="name"
	 * @return
	 */
	public Map<String,String> sheetHeadMapField();
	
	/**
	 * 表头数据,当sheetHeadIndex为空时为必填
	 * @return
	 */
	public List<String> sheetHeadData();
	
	/**
	 * 设置文件所在位置
	 * @return
	 */
	public String filePath();
	
	/**
	 * 要读取目标的excel版本
	 * @return
	 */
	public ExcelVersion excelVersion();
}
