package com.mi_girl.excel.reader.annotation.handle.field;

public interface FieldValueConverter {
	
	/**
	 * 转化数据
	 * @param value 值
	 * @param clx 属性类型
	 * @return 转换后的数据
	 */
	public Object convert(String value,Class<?> clx);

	/**
	 * 判断当前属性类型是否可以由本接口转换
	 * @param clx 属性类型
	 * @return 真假
	 */
	public boolean convertAble(Class<?> clx);

}
