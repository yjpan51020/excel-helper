package com.mi_girl.excel.reader.annotation.handle.field;

import java.util.HashSet;
import java.util.Set;

import com.mi_girl.excel.reader.annotation.handle.field.impl.BasicDataTypesFieldValueConverter;
import com.mi_girl.excel.reader.annotation.handle.field.impl.DateTypeFieldValueConverter;
import com.mi_girl.excel.reader.annotation.handle.field.impl.StringTypeFieldValueConverter;

public class FieldValueAdapter {
	
	private final static Set<FieldValueConverter> CONVERTERS;
	
	static{
		CONVERTERS=new HashSet<FieldValueConverter>();
		CONVERTERS.add(new BasicDataTypesFieldValueConverter());
		CONVERTERS.add(new DateTypeFieldValueConverter());
		CONVERTERS.add(new StringTypeFieldValueConverter());
	}
	
	/**
	 * 转换属性值成对象属性合适的数值
	 * @param value excel读取的数据
	 * @param clx 属性值类型
	 * @return 属性值
	 */
	public static Object convert(String value, Class<?> clx){
		for (FieldValueConverter fieldValueConverter : CONVERTERS) {
			if(fieldValueConverter.convertAble(clx)){
				return fieldValueConverter.convert(value, clx);
			}
		}
		return value;
	}
	
	/**
	 * 判断当前属性类型是否可以转换
	 * @param clx
	 * @return
	 */
	public static boolean isConvertAble(Class<?> clx){
		for (FieldValueConverter fieldValueConverter : CONVERTERS) {
			if(fieldValueConverter.convertAble(clx)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 新增转换器
	 * @param fieldValueConverter 转换器
	 */
	public static void addConverter(FieldValueConverter fieldValueConverter){
		if(fieldValueConverter==null){
			return;
		}
		CONVERTERS.add(fieldValueConverter);
	}

}
