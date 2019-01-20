package com.mi_girl.excel.reader.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectMapConverterUtil{
	
	public static List<Map<String, String>> convert(List<Map<String, String>> excelData, Map<String, String> tableHeadMapField) {
		if(excelData==null||excelData.isEmpty()){
			return null;
		}
		List<Map<String, String>> result=new ArrayList<Map<String, String>>(excelData.size());
		for (Map<String, String> map : excelData) {
			result.add(convert(map,tableHeadMapField));
		}
		return result;
	}
	
	public static Map<String, String> convert(Map<String, String> excelRow, Map<String, String> tableHeadMapField) {
		Map<String,String> ObjectMap=new HashMap<String,String>();
		for (Entry<String, String> entry : excelRow.entrySet()) {
			String key=tableHeadMapField.get(entry.getKey());
			ObjectMap.put(key, entry.getValue());
		}
		return ObjectMap;
	}

}
