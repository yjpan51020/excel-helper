package com.mi_girl.excel.writer.filewirter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.mi_girl.excel.common.constants.ExcelHelperConstants;
import com.mi_girl.excel.common.utils.AssertUtil;
import com.mi_girl.excel.common.utils.Tools;
import com.mi_girl.excel.writer.config.ExcelWriterConfig;


/**
 * 此类做为写入excel的主入口类
 * @author panyujiang
 */
public abstract class ExcelFileWriter {
	
	protected OutputStream excelOut;
    
    private Sheet currentSheet;
    
    protected Workbook wb;
    
    protected int currentRowIndex=-1;
    
    private int sheetIndex=-1;
    
    private ExcelWriterConfig currentExcelWriterConfig;
    
    private ExcelWriterConfig[] excelWriterConfigArgs;
    
    public ExcelFileWriter(ExcelWriterConfig...excelWriterConfigArgs) {
    	this.excelWriterConfigArgs=excelWriterConfigArgs;
		validateInitParam();
	}
    
    private void validateInitParam() {
    	AssertUtil.arrayNotEmpty(excelWriterConfigArgs, "导出excel的配置对象不能为空");
    	for (ExcelWriterConfig excelWriterConfig : excelWriterConfigArgs) {
    		validateExcelWriterConfig(excelWriterConfig);
		}
	}
    
    private void validateExcelWriterConfig(ExcelWriterConfig excelWriterConfig){
    	//���maxRowCountС��0���������������
    	if(excelWriterConfig.sheetMaxRow()<0){
    		return;
    	}
    	//����б�ͷ���ݣ���ÿ���������ݱ������1
    	if(excelWriterConfig.sheetHeadData()!=null&&excelWriterConfig.sheetHeadData().length!=0){
		    AssertUtil.moreThan(excelWriterConfig.sheetMaxRow(), 1, "每个sheet表的最大数据行数不能小于等于1");
		    return;
    	}
    	//���û�б�ͷ���ݣ���ÿ���������ݱ������0
    	AssertUtil.moreThan(excelWriterConfig.sheetMaxRow(), 0, "每个sheet表的最大数据行数不能小于等于0");
    }

	/**
     * 开始写入数据
     */
    public void writeToExcel(){
    	try{
    		initWorkbook();
	    	for (ExcelWriterConfig excelWriterConfig : excelWriterConfigArgs) {
	    		this.changeConfig(excelWriterConfig);
	    		initNewSheet();
				writeDataToSheet();
			}
	    	this.excelOut=initExcelOut(currentExcelWriterConfig.filePath());
	    	wb.write(this.excelOut);
    	}catch(Exception e){
    		throw new RuntimeException("写入excel数据失败",e);
    	}finally{
    		clean();
    	}
    }
    
    private void changeConfig(ExcelWriterConfig currentExcelWriterConfig){
    	this.currentExcelWriterConfig=currentExcelWriterConfig;
    }
    
    private void writeDataToSheet(){
    	List<String[]> data=null;
    	int pageIndex=1;
    	//��ҳ��ȡ���ݣ�Ȼ��д�뵽excel��
    	do {
    		//��ȡ��ǰҳ�������
    		data=currentExcelWriterConfig.getData(pageIndex);
    		//д�뵽excel��
    		writeData(data);
    		pageIndex++;
        //����ȡ������С��pageSizeʱֹͣѭ��(˵�������Ѿ�ȡ��)
		} while (data!=null&&data.size()>=currentExcelWriterConfig.pageSize());
    }
    
    private void writeData(List<String[]> data){
    	if(data==null||data.isEmpty()){
    		return;
    	}
    	for (String[] rowData : data) {
			createNewRow(rowData);
		}
    }
    
    private OutputStream initExcelOut(String filePath) throws FileNotFoundException{
		return new FileOutputStream(filePath);
	}

	/**
     * 初始化BOOK对象
     */
    protected abstract void initWorkbook();

	/**
     * 最大行数处理
     */
    private void maxRowCountHandle(){
    	if(!isMoreThenOrEqualsMaxCount()){
    		return;
    	}
    	initNewSheet();
    }
    
    //验证是否超出最大行数
    private boolean isMoreThenOrEqualsMaxCount(){
    	return currentExcelWriterConfig.sheetMaxRow()>0&&currentRowIndex>=currentExcelWriterConfig.sheetMaxRow();
    }
    
    //创建新的表
    private void initNewSheet() {
    	this.sheetIndex++;
		this.currentSheet=wb.createSheet(currentExcelWriterConfig.SheetName(this.sheetIndex));
		initCellsWidth();
		currentRowIndex=0;//���õ�ǰ���±�
		createHeadRow();
	}

    //初始化列宽
	private void initCellsWidth() {
		int[] widths=currentExcelWriterConfig.cellsWidth();
		if(widths==null||widths.length==0){
			return;
		}
		for (int i = 0; i < widths.length; i++) {
			int width=widths[i];
			if(width>0){
				this.currentSheet.setColumnWidth(i, width);
			}
		}
	}

	private void createNewRow(String[] rowData) {
		if(rowData==null||rowData.length==0){
			return;
		}
		maxRowCountHandle();
		Row row=currentSheet.createRow(currentRowIndex);
		for (int i = 0; i < rowData.length; i++) {
			createNewCell(row,rowData[i],i);
		}
		currentRowIndex++;
	}

	/**
	 * 创建单元格
	 * @param row         行对象
	 * @param cellData    单元格数据
	 * @param cellIndex   单元格下标
	 */
	private void createNewCell(Row row, String cellData,int cellIndex) {
		if(row==null){
			return;
		}
		Cell cell=row.createCell(cellIndex);
		setCellValue(cell, cellData, cellIndex);
		//设置单元格样式
		setCellStyle(cell, cellIndex);
		//设置单元格公式
		setCellFormula(cell, cellIndex);
	}
	
	private void setCellStyle(Cell cell,int cellIndex){
		CellStyle cellStyle=currentExcelWriterConfig.getCellStyle(wb, cellIndex);
		if(cellStyle!=null){
			cell.setCellStyle(cellStyle);
		}
	}
	
	//设置单元格公式
	private void setCellFormula(Cell cell,int cellIndex){
		//因currentRowIndex是以0开始实际excel是1开始所以此处下标加1返回
		int realRowIndex=currentRowIndex+1;
		String cellFormulaString=currentExcelWriterConfig.getCellFormula(cellIndex,realRowIndex);
		if(cellFormulaString!=null){
			cell.setCellFormula(cellFormulaString);
		}
	}
	
	private void setCellValue(Cell cell,String cellData,int cellIndex){
		//判断是否要求数字,并且数据也是数字
		if(ExcelHelperConstants.CELL_TYPE_NUMBER.equals(currentExcelWriterConfig.getCellType(cellIndex))&&Tools.isNum(cellData)){
			cell.setCellValue(Double.valueOf(cellData));
			return;
		}
		cell.setCellValue(cellData);
	}
	
	
	private void createHeadRow(){
		createNewRow(currentExcelWriterConfig.sheetHeadData());
	}
	
	protected abstract void clean();
}
