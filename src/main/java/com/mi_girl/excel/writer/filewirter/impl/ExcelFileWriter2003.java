package com.mi_girl.excel.writer.filewirter.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.IOUtils;

import com.mi_girl.excel.writer.config.ExcelWriterConfig;
import com.mi_girl.excel.writer.filewirter.ExcelFileWriter;

public class ExcelFileWriter2003 extends ExcelFileWriter {

	public ExcelFileWriter2003(ExcelWriterConfig... excelWriterConfigArgs) {
		super(excelWriterConfigArgs);
	}

	@Override
	protected void initWorkbook() {
		this.wb = new HSSFWorkbook();
	}

	@Override
	protected void clean() {
		IOUtils.closeQuietly(this.excelOut);
	}

}
