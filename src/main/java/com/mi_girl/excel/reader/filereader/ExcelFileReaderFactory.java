package com.mi_girl.excel.reader.filereader;

import com.mi_girl.excel.common.ExcelVersion;
import com.mi_girl.excel.reader.filereader.impl.Excel2003FileReader;
import com.mi_girl.excel.reader.filereader.impl.Excel2007FileReader;



public class ExcelFileReaderFactory {

	public static ExcelFileReader getInstantce(ExcelVersion excelVersion, ExcelFileReadConfig excelFileReadConfig) {
		switch (excelVersion) {
		case V2007:
			return new Excel2007FileReader(excelFileReadConfig);
		case V2003:
			return new Excel2003FileReader(excelFileReadConfig);
		default:
			return null;
		}
	}
}
