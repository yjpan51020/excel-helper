package com.mi_girl.excel.writer.config;

import java.util.List;

public interface ExcelWriterConfig extends ExcelWriterBaseConfig {
	/**
	 * 要输出的文件地址
	 * @return
	 */
	public String filePath();
	
	/**
	 * 每张表最大的行数
	 * @return
	 */
	public int sheetMaxRow();
	
	/**
	 * 每次写数据的大小
	 * @return
	 */
	public int pageSize();
	
	/**
	 * 需实现该方法返回实体类列表数据
	 * @param pageIndex 当前获取数据页码下标
	 * @return
	 */
	public List<String[]> getData(int pageIndex);

	/**
	 * 表头数据如果为空则不写表头信息
	 * @return
	 */
	public String[] sheetHeadData();
	
	/**
	 * 设置列宽
	 * @return 对应数组下标的列宽
	 */
	public int[] cellsWidth();

}
