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

	//��֤��ͷ��Ϣ
	protected void validateTableHeadConfig() {
		if(this.sheetHeadIndex!=null&&this.sheetHeadIndex>=0){
			return;
		}
		AssertUtil.collectionNotEmpty(this.sheetHeadData, "未传入表头信息下标且未传入表头初始信息");
	}
	
	/**
	 * ���ݴ�������ݶ�ȡ��Ӧ�����ݷ���
	 * @param sheetIndex   Ҫ��ȡ�ı���±�
	 * @param startIndex   Ҫ��ȡ���ݵĿ�ʼ�±�
	 * @param endIndex     Ҫ��ȡ���ݵĽ�β�±�
	 * @return
	 */
	public abstract FileReadResult readExcel(int sheetIndex,int startIndex,int endIndex);

	//��֤��ȡ����
	protected void validateParam(int sheetIndex, int startIndex, int endIndex) {
		AssertUtil.equalOrMoreThan(sheetIndex, 0, "要读取的sheetIndex不允许小于0");
		validateReadIndex(startIndex, endIndex);
	}
	
	//��֤��ȡ�±�
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
