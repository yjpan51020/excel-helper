package com.mi_girl.excel.writer.config;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelWriterBaseConfig {
	
	/**
	 * 每列的样式
	 * @param wb        excel对象
	 * @param cellIndex 当前列的下标
	 * @return 单元格样式信息
	 */
	public CellStyle getCellStyle(Workbook wb,int cellIndex);
	
	/**
	 * 每个表的表明
	 * @param index  表下标
	 * @return 表名
	 */
	public String SheetName(int index);
	

	
	public String getCellType(int cellIndex);
	
	/**
	 * 获取当前单元格的公式字符串
	 * @param cellIndex  当前单元格的列标
	 * @param rowIndex   当前单元格的行标
	 * @return 返回公式字符串
	 */
	public String getCellFormula(int cellIndex,int rowIndex);

}
