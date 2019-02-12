package com.mi_girl.excel.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;

import com.mi_girl.excel.common.ExcelFieldFormatter;

public class StatusFormatter implements ExcelFieldFormatter {

	@Override
	public String toExcelValue(Object field, Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object toJavaField(String value, Map<String, String> rowData) {
		if(Objects.equals("0",value)){
			return "关闭";
		}
		if(Objects.equals("1",value)){
			return "开启";
		}
		return "未知";
	}

}
