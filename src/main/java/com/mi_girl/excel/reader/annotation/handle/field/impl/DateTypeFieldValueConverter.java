package com.mi_girl.excel.reader.annotation.handle.field.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mi_girl.excel.reader.annotation.handle.field.FieldValueConverter;

/**
 * 进入到此处将使用默认的格式yyyy-MM-dd HH:mm:ss
 * @author Administrator
 *
 */
public class DateTypeFieldValueConverter implements FieldValueConverter {
	
	private final String DEFAULT_PATT="yyyy-MM-dd HH:mm:ss";

	@Override
	public Object convert(String value, Class<?> clx) {
		SimpleDateFormat dateFormat=new SimpleDateFormat(DEFAULT_PATT);
		try {
			return dateFormat.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(String.format("解析日期数据时发生错误:字符串为:[%s]", value),e);
		}
	}

	@Override
	public boolean convertAble(Class<?> clx) {
		return Date.class.isAssignableFrom(clx);
	}

}
