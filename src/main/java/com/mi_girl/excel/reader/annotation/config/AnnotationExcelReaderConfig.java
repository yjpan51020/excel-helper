package com.mi_girl.excel.reader.annotation.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mi_girl.excel.common.ExcelFieldFormatter;
import com.mi_girl.excel.common.ExcelVersion;
import com.mi_girl.excel.common.annotation.ExcelField;
import com.mi_girl.excel.common.utils.AssertUtil;
import com.mi_girl.excel.common.utils.Tools;
import com.mi_girl.excel.reader.ExcelReadConfig;
import com.mi_girl.excel.reader.annotation.handle.field.FieldValueAdapter;

public class AnnotationExcelReaderConfig<T> implements ExcelReadConfig<T> {

	private String filePath;

	private Map<String, String> sheetHeadMapField;

	private Integer sheetHeadIndex;

	private List<String> sheetHeadData;

	private ExcelVersion excelVersion;

	private Map<String, ExcelFieldFormatter> excelFormatterMap;

	private Class<T> clx;

	private List<Field> hasExcelFieldfields;

	public AnnotationExcelReaderConfig(Class<T> clx, String filePath) {
		AssertUtil.notNull(clx, "未传入正确的注解类字节码无法解析");
		AssertUtil.notNull(filePath, "要解析的文件地址为空无法正确解析");
		this.clx = clx;
		this.filePath = filePath;
		this.excelVersion = Tools.getExcelVersionByFilePath(filePath);
		this.hasExcelFieldfields = initHasExcelfieldList();
		AssertUtil.notNull(excelVersion, "要解析的文件不是正确的excel文件名无法正确解析请传入扩展名为:xls或xlsx的文件");
	}

	private List<Field> initHasExcelfieldList() {
		Field[] fields = this.clx.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return null;
		}
		List<Field> fieldList = new ArrayList<Field>();
		for (Field field : fields) {
			if (field.getAnnotation(ExcelField.class) == null) {
				continue;
			}
			fieldList.add(field);
		}
		return fieldList;
	}

	@Override
	public T convert(Map<String, String> rowData) {
		if (Tools.mapIsEmpty(rowData)) {
			return null;
		}
		// 获取泛型对象
		Object obj = getTObject();
		// 赋值到每个属性
		assignment(obj, rowData);
		return (T) obj;
	}

	/**
	 * 赋值到每个属性
	 * 
	 * @param obj
	 *            实例对象
	 * @param clx
	 *            对象的字节码
	 * @param rowData
	 *            excel读取的行数据
	 */
	private void assignment(Object obj, Map<String, String> rowData) {
		ExcelFieldFormatter excelFieldFormatter = null;
		for (Field field : hasExcelFieldfields) {
			// 判断是否是允许赋值的类型
			if (!FieldValueAdapter.isConvertAble(field.getType())) {
				continue;
			}
			excelFieldFormatter = getFormatter(field.getName());
			if (excelFieldFormatter != null) {
				assignmentFieldValueByFormatter(obj, field, rowData, excelFieldFormatter);
				continue;
			}
			assignmentFieldValue(field, obj, rowData.get(field.getName()));
		}
	}

	/**
	 * 通过formatter设置属性值
	 * 
	 * @param obj
	 *            实例对象
	 * @param field
	 *            属性对象
	 * @param rowData
	 *            行数据
	 * @param excelFieldFormatter
	 *            formatter
	 */
	private void assignmentFieldValueByFormatter(Object obj, Field field, Map<String, String> rowData,
			ExcelFieldFormatter excelFieldFormatter) {
		Object javaField = excelFieldFormatter.toJavaField(rowData.get(field.getName()), rowData);
		setValue(field, obj, javaField);
	}

	/**
	 * 设置属性值
	 * 
	 * @param field
	 *            属性对象
	 * @param obj
	 *            实例对象
	 * @param value
	 *            属性值
	 */
	private void setValue(Field field, Object obj, Object value) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(String.format("设置属性[%s]时发生异常,输入值为[%s]", field.getName(), value), e);
		}
	}

	/**
	 * 分配属性值
	 * 
	 * @param field
	 *            属性对象
	 * @param obj
	 *            实例对象
	 * @param exceValue
	 *            属性值的字符串
	 */
	private void assignmentFieldValue(Field field, Object obj, String exceValue) {
		Object value = FieldValueAdapter.convert(exceValue, field.getType());
		setValue(field, obj, value);
	}

	/**
	 * 获取formatter
	 * 
	 * @param fieldName
	 *            属性名
	 * @return formatter
	 */
	private ExcelFieldFormatter getFormatter(String fieldName) {
		if (Tools.mapIsEmpty(excelFormatterMap)) {
			return null;
		}
		return excelFormatterMap.get(fieldName);
	}

	/**
	 * 获取泛型对象
	 */
	private Object getTObject() {
		try {
			return this.clx.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("读取excel生成对象:" + clx + "发生异常", e);
		}
	}

	@Override
	public Integer sheetHeadIndex() {
		return sheetHeadIndex;
	}

	@Override
	public Map<String, String> sheetHeadMapField() {
		return sheetHeadMapField;
	}

	@Override
	public List<String> sheetHeadData() {
		return sheetHeadData;
	}

	@Override
	public String filePath() {
		return filePath;
	}

	@Override
	public ExcelVersion excelVersion() {
		return excelVersion;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setSheetHeadMapField(Map<String, String> sheetHeadMapField) {
		this.sheetHeadMapField = sheetHeadMapField;
	}

	public void setSheetHeadIndex(Integer sheetHeadIndex) {
		this.sheetHeadIndex = sheetHeadIndex;
	}

	public void setSheetHeadData(List<String> sheetHeadData) {
		this.sheetHeadData = sheetHeadData;
	}

	public void setExcelVersion(ExcelVersion excelVersion) {
		this.excelVersion = excelVersion;
	}

	public void setExcelFormatterMap(Map<String, ExcelFieldFormatter> excelFormatterMap) {
		this.excelFormatterMap = excelFormatterMap;
	}

}
