package com.mi_girl.excel.writer.config;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class ExcelWriterConfigSupport implements ExcelWriterConfig {
	
	/**
	 * 设置单元格公式
	 */
	@Override
	public String getCellFormula(int cellIndex, int rowIndex) {
		return null;
	}

	/**
	 * 每列的样式
	 * @param wb        excel对象
	 * @param cellIndex 当前列的下标
	 * @return 返回样式
	 */
	@Override
	public CellStyle getCellStyle(Workbook wb, int cellIndex) {
		return null;
	}
	
	/**
	 * 每列的数据类型，目前只支持String和数字
	 * 对应的常量在ExcelHelperConstants
	 */
	@Override
	public String getCellType(int cellIndex) {
		return null;
	}
	
	/**
	 * 设置列宽，对应下标从0开始对应每列的宽度
	 */
	@Override
	public int[] cellsWidth() {
		return null;
	}

}
