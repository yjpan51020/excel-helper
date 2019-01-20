package com.mi_girl.excel.common.utils;

import java.util.Collection;
import java.util.Map;

import com.mi_girl.excel.common.ExcelVersion;



public class Tools {
	
	public static boolean collectionIsEmpty(Collection<?> collection){
		return collection==null||collection.isEmpty();
	}
	
	public static boolean mapIsEmpty(Map<?,?> map){
		return map==null||map.isEmpty();
	}
	
	public static ExcelVersion getExcelVersionByFilePath(String filePath){
		if(filePath==null||filePath.length()==0){
			return null;
		}
		String fileType=filePath.substring(filePath.lastIndexOf(".")+1);
		if("xlsx".equals(fileType)){
			return ExcelVersion.V2007;
		}
		if("xls".equals(fileType)){
			return ExcelVersion.V2003;
		}
		return null;
	}
	
	public static boolean isNum(String num){
		if(num==null){
			return false;
		}
		try{
		   Double.valueOf(num);
		   return true;
		}catch(Exception e){
		   return false;
		}
	}

}
