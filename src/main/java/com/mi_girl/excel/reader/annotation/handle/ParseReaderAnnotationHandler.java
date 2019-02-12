package com.mi_girl.excel.reader.annotation.handle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.mi_girl.excel.common.ExcelFieldFormatter;
import com.mi_girl.excel.common.annotation.ExcelField;
import com.mi_girl.excel.common.annotation.ReaderHeadIndex;
import com.mi_girl.excel.common.utils.AssertUtil;

public class ParseReaderAnnotationHandler {

	/**
	 * 解析出属性名与formatter的映射MAP
	 * 
	 * @param clx
	 *            要解析的类字节码
	 * @return 属性名与formatter的映射MAP
	 */
	public static Map<String, ExcelFieldFormatter> parseExcelFormatterMap(Class<?> clx) {
		List<Field> hasAnnotationFields = getHasAnnotationFields(clx);
		AssertUtil.collectionNotEmpty(hasAnnotationFields, "传入的类型无任何excelFiled注解无法解析");
		ExcelField excelField = null;
		Class<ExcelFieldFormatter> formatter = null;
		Map<String, ExcelFieldFormatter> result = null;
		for (Field field : hasAnnotationFields) {
			excelField = field.getAnnotation(ExcelField.class);
			formatter = excelField.formatter();
			if (!isExcelFieldFormatter(formatter)) {
				continue;
			}
			if (result == null) {
				result = new HashMap<String, ExcelFieldFormatter>();
			}
			result.put(field.getName(), getExcelFieldFormatter(formatter));
		}
		return result;
	}

	/**
	 * 解析出表头与属性名的映射关系
	 * 
	 * @param clx
	 *            要解析的类字节码
	 * @return 表头与属性名的映射关系
	 */
	public static Map<String, String> parseSheetHeadMapField(Class<?> clx) {
		List<Field> hasAnnotationFields = getHasAnnotationFields(clx);
		AssertUtil.collectionNotEmpty(hasAnnotationFields, "传入的类型无任何excelFiled注解无法解析");
		ExcelField excelField = null;
		Map<String, String> result = null;
		for (Field field : hasAnnotationFields) {
			excelField = field.getAnnotation(ExcelField.class);
			if (excelField == null) {
				continue;
			}
			if (result == null) {
				result = new HashMap<String, String>();
			}
			result.put(excelField.column(), field.getName());
		}
		return result;
	}

	/**
	 * 获取表头信息(当未指定表头在excel中的下标时此数据起作用)
	 * 
	 * @param clx
	 *            要解析的类字节码
	 * @return 表头信息
	 */
	public static List<String> parseSheetHeadData(Class<?> clx) {
		// 获取所有ExcelField注解
		List<ExcelField> excelFields = getAllExcelFieldAnnotation(clx);
		AssertUtil.collectionNotEmpty(excelFields, "传入的类型无任何excelFiled注解无法解析");
		Collections.sort(excelFields, new Comparator<ExcelField>() {
			@Override
			public int compare(ExcelField source, ExcelField target) {
				if (source.index() > target.index()) {
					return 1;
				}
				if (source.index() < target.index()) {
					return -1;
				}
				return 0;
			}
		});
		return convert2SheetHeadData(excelFields);
	}

	private static List<String> convert2SheetHeadData(List<ExcelField> excelFields) {
		List<String> result = new ArrayList<String>();
		for (ExcelField excelField : excelFields) {
			result.add(excelField.column());
		}
		return result;
	}

	private static List<ExcelField> getAllExcelFieldAnnotation(Class<?> clx) {
		if (clx == null) {
			return null;
		}
		Field[] declaredFields = clx.getDeclaredFields();
		AssertUtil.arrayNotEmpty(declaredFields, "传入的类型无任何属性无法解析");
		List<ExcelField> result = null;
		ExcelField excelField = null;
		for (Field field : declaredFields) {
			excelField = field.getAnnotation(ExcelField.class);
			if (excelField == null) {
				continue;
			}
			if (result == null) {
				result = new ArrayList<ExcelField>();
			}
			result.add(excelField);
		}
		return result;
	}

	/**
	 * 获取ExcelFieldFormatter对象
	 * 
	 * @param formatter
	 *            字节码
	 * @return ExcelFieldFormatter对象
	 */
	private static ExcelFieldFormatter getExcelFieldFormatter(Class<ExcelFieldFormatter> formatter) {
		try {
			return formatter.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("初始化读取formatter时发生异常", e);
		}
	}

	/**
	 * 判断注解中的字节码是否是ExcelFieldFormatter
	 * 
	 * @param formatter
	 *            字节码对象
	 * @return 真或假
	 */
	private static boolean isExcelFieldFormatter(Class<?> formatter) {
		if (formatter == null || formatter==Object.class) {
			return false;
		}
		if(ExcelFieldFormatter.class.isAssignableFrom(formatter)){
			return true;
		}
		throw new RuntimeException("ExcelField中有formatter类型不正确,"+formatter+"类型不被允许赋值于formatter属性中");
	}

	/**
	 * 获取有注解ExcelField的属性列表
	 * 
	 * @param clx
	 *            解析类的字节码对象
	 * @return 有注解ExcelField的属性列表
	 */
	private static List<Field> getHasAnnotationFields(Class<?> clx) {
		if (clx == null) {
			return null;
		}
		Field[] declaredFields = clx.getDeclaredFields();
		if (declaredFields == null || declaredFields.length == 0) {
			return null;
		}
		List<Field> hasAnnotationFields = null;
		for (Field field : declaredFields) {
			if (field.getAnnotation(ExcelField.class) == null) {
				continue;
			}
			if (hasAnnotationFields == null) {
				hasAnnotationFields = new ArrayList<Field>();
			}
			hasAnnotationFields.add(field);
		}
		return hasAnnotationFields;
	}

	/**
	 * 获取读取表头下标
	 * @param clx
	 * @return
	 */
	public static Integer parseSheetHeadIndex(Class<?> clx) {
		AssertUtil.notNull(clx, "获取表头读取下标时传入的类型clx为空");
		ReaderHeadIndex indexAnnotation = clx.getAnnotation(ReaderHeadIndex.class);
		if(indexAnnotation==null){
			//未设置下标则不读取
			return -1;
		}
		return indexAnnotation.value();
	}

}
