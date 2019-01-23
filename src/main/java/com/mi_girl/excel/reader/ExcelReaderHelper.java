package com.mi_girl.excel.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mi_girl.excel.common.utils.AssertUtil;
import com.mi_girl.excel.reader.filereader.ExcelFileReadConfig;
import com.mi_girl.excel.reader.filereader.ExcelFileReader;
import com.mi_girl.excel.reader.filereader.ExcelFileReaderFactory;
import com.mi_girl.excel.reader.filereader.FileReadResult;
import com.mi_girl.excel.reader.util.ObjectMapConverterUtil;


/**
 * 此类做为读取excel转换成java对象的主入口类
 * @author panyujiang
 */
public class ExcelReaderHelper <T>{
	
	private ExcelReadConfig<T> excelReadConfig;
	
	public ExcelReaderHelper(ExcelReadConfig<T> excelReadConfig) {
		this.excelReadConfig=excelReadConfig;
		AssertUtil.notNull(this.excelReadConfig, "��ʼ��EXCELREADERʧ��,��excelReadConfigΪ��");
		initExcelFileReader();
	}

	/**
	 * 读取EXCEL并返回结果
	 * @param sheetIndex 表下标
	 * @param startIndex 开始数据下标
	 * @param endIndex 结束下标
	 * @return 读取结果
	 */
	public ReadExcelResult<T> readExcel(int sheetIndex,int startIndex,int endIndex){
		ExcelFileReader reader=initExcelFileReader();
		FileReadResult fileReadResult=reader.readExcel(sheetIndex, startIndex, endIndex);
		List<Map<String,String>> excelData=fileReadResult.getRecord();
		List<Map<String,String>> objectMapList=ObjectMapConverterUtil.convert(excelData, excelReadConfig.sheetHeadMapField());
		List<T> result=convert(objectMapList);
		return new ReadExcelResult<T>(result, fileReadResult.getLastIndex());
	}

	private List<T> convert(List<Map<String, String>> objectMapList) {
		if(objectMapList==null||objectMapList.isEmpty()){
			return null;
		}
		List<T> result=new ArrayList<T>(objectMapList.size());
		for (Map<String, String> objectMap : objectMapList) {
			result.add(excelReadConfig.convert(objectMap));
		}
		return result;
	}
	
	private ExcelFileReader initExcelFileReader() {
		ExcelFileReadConfig excelFileReadConfig=new ExcelFileReadConfig();
		excelFileReadConfig.setFilePath(excelReadConfig.filePath());
		excelFileReadConfig.setSheetHeadData(excelReadConfig.sheetHeadData());
		excelFileReadConfig.setSheetHeadIndex(excelReadConfig.sheetHeadIndex());
		return ExcelFileReaderFactory.getInstantce(excelReadConfig.excelVersion(), excelFileReadConfig);
	}

}
