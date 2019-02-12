package com.mi_girl.excel.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.mi_girl.excel.common.ExcelFieldFormatter;

public class OpenTimeFormatter implements ExcelFieldFormatter {

	@Override
	public String toExcelValue(Object field, Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object toJavaField(String value, Map<String, String> rowData) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return simpleDateFormat.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
