package com.mi_girl.excel.reader.filereader;

import java.util.List;
import java.util.Map;

public class FileReadResult {
	
	private List<Map<String,String>> record;
	
	private int lastIndex;
	
	public FileReadResult() {
	}

	public FileReadResult(List<Map<String, String>> record, int lastIndex) {
		this.record = record;
		this.lastIndex = lastIndex;
	}

	public List<Map<String, String>> getRecord() {
		return record;
	}

	public void setRecord(List<Map<String, String>> record) {
		this.record = record;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	
	

}
