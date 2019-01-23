package com.mi_girl.excel.reader.filereader;

import java.util.List;

import com.mi_girl.excel.common.utils.AssertUtil;



public abstract class ExcelFileReader {
	
	protected String  filePath;
	
	protected List<String> sheetHeadData;
	
	protected Integer sheetHeadIndex;
   
	public ExcelFileReader(ExcelFileReadConfig excelFileReadConfig) {
		this.sheetHeadIndex=excelFileReadConfig.getSheetHeadIndex();
		this.sheetHeadData=excelFileReadConfig.getSheetHeadData();
		this.filePath=excelFileReadConfig.getFilePath();
		validateTableHeadConfig();
	}

	//验证表头配置信息
	protected void validateTableHeadConfig() {
		if(this.sheetHeadIndex!=null&&this.sheetHeadIndex>=0){
			return;
		}
		AssertUtil.collectionNotEmpty(this.sheetHeadData, "未传入表头信息下标且未传入表头初始信息");
	}
	
	/**
	 * 读取EXCEL
	 * @param sheetIndex   表下标
	 * @param startIndex   数据开始下标
	 * @param endIndex     数据结束下标
	 * @return 文件读取结果
	 */
	public abstract FileReadResult readExcel(int sheetIndex,int startIndex,int endIndex);

	//验证参数信息
	protected void validateParam(int sheetIndex, int startIndex, int endIndex) {
		AssertUtil.equalOrMoreThan(sheetIndex, 0, "要读取的sheetIndex不允许小于0");
		validateReadIndex(startIndex, endIndex);
	}
	
	//验证读取数据下标是否符合
	protected void validateReadIndex(int startIndex, int endIndex){
		AssertUtil.equalOrMoreThan(startIndex, 0, "读取数据的开始下标必须大于等于0");
		if(endIndex>=0){
			AssertUtil.equalOrMoreThan(endIndex, startIndex, "当结束下标大于等于0时，开始下标不允许大于结束下标");
		}
		if(this.sheetHeadIndex!=null){
			AssertUtil.moreThan(startIndex, this.sheetHeadIndex, "当表头下标大于等于0时,开始下标不允许小于表头下标");
		}
	}
}
