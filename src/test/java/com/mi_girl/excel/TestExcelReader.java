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

		//new 出config来 顺道把地址传进去
        ClazzObjectExcelReaderConfig clazzObjectExcelReaderConfig=new ClazzObjectExcelReaderConfig(filePath);
        //new 出读取帮助类
        ExcelReaderHelper<ClazzObject> excelReaderHelper=new ExcelReaderHelper<ClazzObject>(clazzObjectExcelReaderConfig);
        //读取结果
        //第一个参数为读取哪个表(下标由0开始)
        //第二个参数为第几行开始读取(下标也由0开始 此处为1的原因是0是表头数据)
        //第三个参数为读取到第几行(下标也有0开始  此处为-1代表读取所有)
        //返回结果中可以获取数据有几行 和当前读取的数据
        ReadExcelResult<ClazzObject> readExcelResult=excelReaderHelper.readExcel(0, 1, -1);
        //打印数据
        for (ClazzObject clazzObject : readExcelResult.getRecord()) {
			System.out.println(clazzObject);
		}
        
	}

}
