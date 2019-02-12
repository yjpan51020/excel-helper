package com.mi_girl.excel.common;

import java.util.Map;

/**
 * EXCEL属性的转换器
 * @author panyujiang
 */
public interface ExcelFieldFormatter {
	
	/**
	 * 将实体属性转换成excel数据
	 * @param field 属性值
	 * @param entity 实体对象
	 * @return
	 */
	public String toExcelValue(Object field,Object entity);
	
	/**
	 * 将读取到的excel数据转换成属性值
	 * @param value 属性值
	 * @param rowData 实体对象map
	 * @return
	 */
	public Object toJavaField(String value,Map<String,String> rowData);

}
