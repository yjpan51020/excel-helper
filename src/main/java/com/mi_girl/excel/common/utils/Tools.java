package com.mi_girl.excel.common.utils;

import java.lang.reflect.ParameterizedType;
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
	
	/**
	 * 判断是否为基础数据类型或其包装类
	 * @param clx 类型
	 * @return 真假
	 */
	public static boolean isBasicDataTypes(Class<?> clx){
		if(Boolean.class==clx){
			return true;
		}
		if(Character.class==clx){
			return true;
		}
		if(clx.isPrimitive()){
			return true;
		}
		return Number.class.isAssignableFrom(clx);
	}
	
	/**
	 * 获取类泛型的字节码对象
	 * @return 字节码
	 */
	public static Class<?> getTClass(Class<?> clx) {
		ParameterizedType genericSuperclass = (ParameterizedType) clx.getGenericSuperclass();
		return (Class<?>) genericSuperclass.getActualTypeArguments()[0];
	}

}
