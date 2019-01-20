package com.mi_girl.excel.writer.filewirter.impl;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.mi_girl.excel.writer.config.ExcelWriterConfig;
import com.mi_girl.excel.writer.filewirter.ExcelFileWriter;

public class ExcelFileWriter2007 extends ExcelFileWriter {

	public ExcelFileWriter2007(ExcelWriterConfig... excelWriterConfigArgs) {
		super(excelWriterConfigArgs);
	}

	@Override
	protected void initWorkbook() {
		this.wb = new SXSSFWorkbook(100);
	}

	@Override
	protected void clean() {
		SXSSFWorkbook sXSSFWorkbook = (SXSSFWorkbook) wb;
		sXSSFWorkbook.dispose();
		IOUtils.closeQuietly(this.excelOut);
	}

}
