package com.mi_girl.excel.writer.config;

import java.util.List;

public interface ExcelWriterConfig extends ExcelWriterBaseConfig {
	/**
	 * 要输出的文件地址
	 * @return 要输出的文件地址
	 */
	public String filePath();
	
	/**
	 * 每张表最大的行数
	 * @return 每张表最大的行数
	 */
	public int sheetMaxRow();
	
	/**
	 * 每次写数据的大小
	 * @return 每次写数据的大小
	 */
	public int pageSize();
	
	/**
	 * 需实现该方法返回实体类列表数据
	 * @param pageIndex 当前获取数据页码下标
	 * @return 返回对象转换后每行的数据
	 */
	public List<String[]> getData(int pageIndex);

	/**
	 * 表头数据如果为空则不写表头信息
	 * @return 表头信息
	 */
	public String[] sheetHeadData();
	
	/**
	 * 设置列宽
	 * @return 对应数组下标的列宽
	 */
	public int[] cellsWidth();

}
