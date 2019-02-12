package com.mi_girl.excel;

import java.io.File;

import com.mi_girl.excel.config.ClazzObjectExcelReaderConfig;
import com.mi_girl.excel.model.ClazzObject;
import com.mi_girl.excel.reader.ExcelReaderHelper;
import com.mi_girl.excel.reader.ReadExcelResult;
import com.mi_girl.excel.reader.annotation.reader.AnnotationExcelReaderHelper;

public class AnnotationTestExcelReader {

	public static void main(String[] args) {
		// mytest
		String filePath=AnnotationTestExcelReader.class.getResource("mytest.xlsx").getPath();
		System.out.println(new File(filePath).exists());
		AnnotationExcelReaderHelper<ClazzObject> excelReaderHelper=new AnnotationExcelReaderHelper<ClazzObject>(ClazzObject.class,filePath);
        ReadExcelResult<ClazzObject> readExcelResult=excelReaderHelper.readExcel(0, 1, -1);
        for (ClazzObject clazzObject : readExcelResult.getRecord()) {
			System.out.println(clazzObject);
		}
        
	}

}
