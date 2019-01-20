package com.mi_girl.excel.reader.filereader.parsehandle;

import org.apache.poi.xssf.model.SharedStringsTable;

public class ParseHandleConfig {

	private SharedStringsTable sharedStringsTable;

	private int startIndex;

	private int endIndex;

	private Integer headIndex;

	public ParseHandleConfig() {
	}

	public ParseHandleConfig(SharedStringsTable sharedStringsTable, int startIndex, int endIndex, Integer headIndex) {
		super();
		this.sharedStringsTable = sharedStringsTable;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.headIndex = headIndex;
	}

	public SharedStringsTable getSharedStringsTable() {
		return sharedStringsTable;
	}

	public void setSharedStringsTable(SharedStringsTable sharedStringsTable) {
		this.sharedStringsTable = sharedStringsTable;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public Integer getHeadIndex() {
		return headIndex;
	}

	public void setHeadIndex(Integer headIndex) {
		this.headIndex = headIndex;
	}

}
