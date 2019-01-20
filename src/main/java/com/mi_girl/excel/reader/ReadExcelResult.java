package com.mi_girl.excel.reader;

import java.util.List;

public class ReadExcelResult<T> {
	
	private List<T> record;
	
	private int lastIndex;
	
	public ReadExcelResult() {
	}

	public ReadExcelResult(List<T> record, int lastIndex) {
		this.record = record;
		this.lastIndex = lastIndex;
	}

	public List<T> getRecord() {
		return record;
	}

	public void setRecord(List<T> record) {
		this.record = record;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

}
