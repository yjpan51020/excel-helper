package com.mi_girl.excel.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReaderHeadIndex {
	
	/**
	 * 设置读取excel的表头信息的下标
	 * 设置数值小于0时代表不读取
	 * @return
	 */
	int value() default -1;

}
