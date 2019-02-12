package com.mi_girl.excel.reader.annotation.reader;

import java.util.List;
import java.util.Map;

import com.mi_girl.excel.common.ExcelFieldFormatter;
import com.mi_girl.excel.common.utils.Tools;
import com.mi_girl.excel.reader.ExcelReaderHelper;
import com.mi_girl.excel.reader.ReadExcelResult;
import com.mi_girl.excel.reader.annotation.config.AnnotationExcelReaderConfig;
import com.mi_girl.excel.reader.annotation.handle.ParseReaderAnnotationHandler;

/**
 * 此类做为注解读取excel转换成java对象的主入口类
 * @author panyujiang
 */
public class AnnotationExcelReaderHelper <T>{
	
	private ExcelReaderHelper<T> excelReaderHelper;
	
	public AnnotationExcelReaderHelper(Class<T> clx,String filePath) {
		AnnotationExcelReaderConfig<T> annotationExcelReaderConfig=createReaderConfig(clx,filePath);
		excelReaderHelper=new ExcelReaderHelper<T>(annotationExcelReaderConfig);
	}
	
	/**
	 * 读取EXCEL并返回结果
	 * @param sheetIndex 表下标
	 * @param startIndex 开始数据下标
	 * @param endIndex 结束下标
	 * @return 读取结果
	 */
	public ReadExcelResult<T> readExcel(int sheetIndex,int startIndex,int endIndex){
		return excelReaderHelper.readExcel(sheetIndex, startIndex, endIndex);
	}
	
	/**
	 * 初始化读取配置信息
	 * @param filePath 文件地址
	 * @return 读取配置信息
	 */
	private AnnotationExcelReaderConfig<T> createReaderConfig(Class<T> clx,String filePath) {
		Map<String, ExcelFieldFormatter> excelFormatterMap = ParseReaderAnnotationHandler.parseExcelFormatterMap(clx);
		List<String> sheetHeadData = ParseReaderAnnotationHandler.parseSheetHeadData(clx);
		Map<String, String> sheetHeadMapField = ParseReaderAnnotationHandler.parseSheetHeadMapField(clx);
		Integer headerIndex=ParseReaderAnnotationHandler.parseSheetHeadIndex(clx);
		AnnotationExcelReaderConfig<T> annotationExcelReaderConfig=new AnnotationExcelReaderConfig<T>(clx,filePath);
		annotationExcelReaderConfig.setSheetHeadMapField(sheetHeadMapField);
		annotationExcelReaderConfig.setExcelFormatterMap(excelFormatterMap);
		annotationExcelReaderConfig.setSheetHeadIndex(headerIndex);
		if(headerIndex==null||headerIndex<0){
			annotationExcelReaderConfig.setSheetHeadData(sheetHeadData);
		}
		return annotationExcelReaderConfig;
	}

}
