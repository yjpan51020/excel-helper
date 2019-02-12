package com.mi_girl.excel.reader.annotation.handle.field.impl;

import com.mi_girl.excel.common.utils.Tools;
import com.mi_girl.excel.reader.annotation.handle.field.FieldValueConverter;

/**
 * 基本数据类型转换器
 * @author Administrator
 *
 */
public class BasicDataTypesFieldValueConverter implements FieldValueConverter {

	@Override
	public Object convert(String value, Class<?> clx) {
		if(value==null){
			return null;
		}
		if(Double.class==clx||double.class==clx){
			return Double.valueOf(value);
		}
		if(Float.class==clx||float.class==clx){
			return Float.valueOf(value);
		}
		if(Long.class==clx||long.class==clx){
			return Long.valueOf(value);
		}
		if(Integer.class==clx||int.class==clx){
			return Integer.valueOf(value);
		}
		if(Short.class==clx||short.class==clx){
			return Short.valueOf(value);
		}
		if(Byte.class==clx||byte.class==clx){
			return Byte.valueOf(value);
		}
		if(Boolean.class==clx||boolean.class==clx){
			return Boolean.valueOf(value);
		}
		if(Character.class==clx||char.class==clx){
			return value.charAt(0);
		}
		return null;
	}

	@Override
	public boolean convertAble(Class<?> clx) {
		return Tools.isBasicDataTypes(clx);
	}

}
