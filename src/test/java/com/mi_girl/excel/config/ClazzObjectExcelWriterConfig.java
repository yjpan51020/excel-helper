package com.mi_girl.excel.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.mi_girl.excel.writer.config.ExcelWriterConfigSupport;

public class ClazzObjectExcelWriterConfig extends ExcelWriterConfigSupport {
	
	private CellStyle cellStyle;

	@Override
	public String SheetName(int index) {
		return "test";
	}

	@Override
	public String filePath() {
		return "F:\\tmp\\test\\mytest.xlsx";
	}

	@Override
	public int sheetMaxRow() {
		return 1000000;
	}

	@Override
	public int pageSize() {
		return 1000000;
	}

	@Override
	public List<String[]> getData(int pageIndex) {
		List<String[]> result=new ArrayList<String[]>();
		for (int i = 0; i < 500; i++) {
			result.add(getRowData());
		}
		return result;
	}

	private String[] getRowData() {
		List<String> result=new ArrayList<String>();
		Random ran=new Random();
		StringBuilder sb=null;
		for (int j = 0; j < 255; j++) {
			sb=new StringBuilder();
			for (int i = 0; i < 10; i++) {
				sb.append(ran.nextInt(10));
			}
			result.add(sb.toString());
		}
		return result.toArray(new String[result.size()]);
	}

	@Override
	public String[] sheetHeadData() {
		return new String[]{"编号","名称"};
	}
	
	@Override
	public CellStyle getCellStyle(Workbook wb, int cellIndex) {
		if(this.cellStyle!=null){
			return this.cellStyle;
		}
		CellStyle cellStyle=wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.SLANTED_DASH_DOT);
		cellStyle.setBorderTop(BorderStyle.SLANTED_DASH_DOT);
		cellStyle.setBorderLeft(BorderStyle.SLANTED_DASH_DOT);
		cellStyle.setBorderRight(BorderStyle.SLANTED_DASH_DOT);
		Font font=wb.createFont();
		font.setBold(true);
		font.setColor(HSSFColor.RED.index);
		cellStyle.setFont(font);
		this.cellStyle=cellStyle;
		return this.cellStyle;
	}
	
	@Override
	public int[] cellsWidth() {
		
		return getCellsWidth();
	}
	
	private int[] getCellsWidth(){
		int[] width=new int[255];
		for (int i = 0; i < 255; i++) {
			width[i]=5000;
		}
		return width;
	}
}
