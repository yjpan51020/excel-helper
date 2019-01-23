package com.mi_girl.excel;

import com.mi_girl.excel.config.ClazzObjectExcelWriterConfig;
import com.mi_girl.excel.writer.filewirter.ExcelFileWriter;
import com.mi_girl.excel.writer.filewirter.ExcelFileWriterFactory;

public class TestExcelWriter {
	
	public TestExcelWriter() {
		
	}
	
    public static void main(String[] args) throws InterruptedException {
    	long start=System.currentTimeMillis();
    	ClazzObjectExcelWriterConfig   clazzObjectExcelWriterConfig=new ClazzObjectExcelWriterConfig();
		ExcelFileWriter excelFileWriter=ExcelFileWriterFactory.getInstantce(clazzObjectExcelWriterConfig);
		excelFileWriter.writeToExcel();
		System.out.println("消耗时间为:"+(System.currentTimeMillis()-start));
	}

}
