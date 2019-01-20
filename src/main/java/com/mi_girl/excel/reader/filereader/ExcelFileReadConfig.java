package com.mi_girl.excel.reader.filereader;

import java.util.List;

public class ExcelFileReadConfig {

	private String filePath;

	private List<String> sheetHeadData;

	private Integer sheetHeadIndex;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<String> getSheetHeadData() {
		return sheetHeadData;
	}

	public void setSheetHeadData(List<String> sheetHeadData) {
		this.sheetHeadData = sheetHeadData;
	}

	public Integer getSheetHeadIndex() {
		return sheetHeadIndex;
	}

	public void setSheetHeadIndex(Integer sheetHeadIndex) {
		this.sheetHeadIndex = sheetHeadIndex;
	}

}
