package com.mi_girl.excel.common.utils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssertUtil {
	
   public static void collectionNotEmpty(Collection<?> collection,String message){
	   if(collection==null||collection.isEmpty()){
		   throw new IllegalArgumentException(message);
	   }
   }
   
   public static void mapNotEmpty(Map<?,?> map,String message){
	   if(map==null||map.isEmpty()){
		   throw new IllegalArgumentException(message);
	   }
   }
   
   public static void equals(String resource,String target,String message){
	   if(!resource.equals(target)){
		   throw new IllegalArgumentException(message);
	   }
   }
   
   public static void moreThan(int resource,int target,String message){
	   if(resource<=target){
		   throw new IllegalArgumentException(message);
	   }
   }
   
   public static void equalOrMoreThan(int resource,int target,String message){
	   if(resource<target){
		   throw new IllegalArgumentException(message);
	   }
   }
   
   public static void notNull(Object obj,String message){
	   if(obj==null){
		   throw new IllegalArgumentException(message);
	   }
   }
   
   public static void matchReg(String source,String regex,String message){
	   Pattern pattern=Pattern.compile(regex);
	   Matcher matcher=pattern.matcher(source);
	   if(!matcher.matches()){
		   throw new IllegalArgumentException(message);
	   }
   }
   
   public static void arrayNotEmpty(Object[] args,String message){
	   if(args==null||args.length==0){
		   throw new IllegalArgumentException(message);
	   }
   }
   
}
