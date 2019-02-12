package com.mi_girl.excel.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
	
	/**
	 * 列名
	 */
	String column();
	
	/**
	 * 列下标
	 */
	int index();
	
	/**
	 * field转换器
	 * 当未指定时将根据具体属性类型转换
	 */
	Class formatter() default Object.class;

}
