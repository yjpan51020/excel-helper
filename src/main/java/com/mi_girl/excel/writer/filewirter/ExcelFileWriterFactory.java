package com.mi_girl.excel.writer.filewirter;

import com.mi_girl.excel.common.ExcelVersion;
import com.mi_girl.excel.writer.config.ExcelWriterConfig;
import com.mi_girl.excel.writer.filewirter.impl.ExcelFileWriter2003;
import com.mi_girl.excel.writer.filewirter.impl.ExcelFileWriter2007;


public class ExcelFileWriterFactory {
    public static ExcelFileWriter getInstantce(ExcelVersion excelVersion,ExcelWriterConfig...excelWriterConfigArgs){
    	if(excelVersion==null){
    		return null;
    	}
    	switch (excelVersion) {
		case V2003:
			return new ExcelFileWriter2003(excelWriterConfigArgs);
		case V2007:
			return new ExcelFileWriter2007(excelWriterConfigArgs);
		default:
			return null;
		}
    }
    
    
    public static ExcelFileWriter getInstantce(ExcelWriterConfig...excelWriterConfigArgs){
		return new ExcelFileWriter2007(excelWriterConfigArgs);
    }
}
