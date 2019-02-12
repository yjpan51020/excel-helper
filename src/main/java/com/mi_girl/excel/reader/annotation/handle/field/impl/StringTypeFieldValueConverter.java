package com.mi_girl.excel.reader.annotation.handle.field.impl;

import com.mi_girl.excel.reader.annotation.handle.field.FieldValueConverter;

public class StringTypeFieldValueConverter implements FieldValueConverter {

	@Override
	public Object convert(String value, Class<?> clx) {
		return value;
	}

	@Override
	public boolean convertAble(Class<?> clx) {
		return String.class==clx;
	}

}
