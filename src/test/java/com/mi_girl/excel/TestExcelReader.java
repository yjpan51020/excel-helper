package com.mi_girl.excel;

import java.io.File;

import com.mi_girl.excel.config.ClazzObjectExcelReaderConfig;
import com.mi_girl.excel.model.ClazzObject;
import com.mi_girl.excel.reader.ExcelReaderHelper;
import com.mi_girl.excel.reader.ReadExcelResult;

public class TestExcelReader {

	public static void main(String[] args) {
		// mytest
		String filePath=TestExcelReader.class.getResource("mytest.xlsx").getPath();
		System.out.println(new File(filePath).exists());
        ClazzObjectExcelReaderConfig clazzObjectExcelReaderConfig=new ClazzObjectExcelReaderConfig(filePath);
        ExcelReaderHelper<ClazzObject> excelReaderHelper=new ExcelReaderHelper<ClazzObject>(clazzObjectExcelReaderConfig);
        ReadExcelResult<ClazzObject> readExcelResult=excelReaderHelper.readExcel(0, 1, -1);
        for (ClazzObject clazzObject : readExcelResult.getRecord()) {
			System.out.println(clazzObject);
		}
        
	}

}
