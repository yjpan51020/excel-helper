package com.mi_girl.excel.reader.filereader.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;

import com.mi_girl.excel.common.utils.AssertUtil;
import com.mi_girl.excel.reader.converter.CellValueConvert;
import com.mi_girl.excel.reader.converter.impl.CellValueToString;
import com.mi_girl.excel.reader.filereader.ExcelFileReadConfig;
import com.mi_girl.excel.reader.filereader.ExcelFileReader;
import com.mi_girl.excel.reader.filereader.FileReadResult;


public class Excel2003FileReader extends ExcelFileReader{
	
	private CellValueConvert cellValueConvert= new CellValueToString();
   
	public Excel2003FileReader(ExcelFileReadConfig excelFileReadConfig) {
		super(excelFileReadConfig);
	}
	
	private InputStream initExcelFileIn(String filePath) {
		try {
			return new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ���ݴ�������ݶ�ȡ��Ӧ�����ݷ���
	 * @param sheetIndex   Ҫ��ȡ�ı���±�
	 * @param startIndex   Ҫ��ȡ���ݵĿ�ʼ�±�
	 * @param endIndex     Ҫ��ȡ���ݵĽ�β�±�
	 * @return
	 */
	public FileReadResult readExcel(int sheetIndex,int startIndex,int endIndex){
		InputStream excelFileIn=null;
		try{
			excelFileIn=initExcelFileIn(filePath);
			validateParam(sheetIndex,startIndex,endIndex);
			//��ȡsheet����
	        Sheet sheet=initSheet(sheetIndex,excelFileIn);
	        AssertUtil.notNull(sheet, "根据下标获取sheet为空");
	        List<Map<String,String>> result= readExcel(sheet, startIndex, endIndex);
	        return new FileReadResult(result,sheet.getLastRowNum()+1);
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally {
			IOUtils.closeQuietly(excelFileIn);
		}
	}

	private List<Map<String,String>> readExcel(Sheet sheet,int startIndex,int endIndex){
        endIndex=getEndIndex(endIndex,sheet.getLastRowNum());
        //��ȡ��ͷ��Ϣ
        initsheetHeadData(sheet);
        List<Map<String,String>> result=parseSheetData(sheet, startIndex, endIndex);
        return result;
	}
	
	
	
	private int getEndIndex(int endIndex, int lastRowNum) {
		if(endIndex<0){
			return lastRowNum;
		}
		if(endIndex>=lastRowNum){
			return lastRowNum;
		}
		return endIndex;
	}

	private void initsheetHeadData(Sheet sheet) {
		if(this.sheetHeadData!=null&&this.sheetHeadData.size()>0){
			return;
		}
		 //���ݴ���ʱ���ޱ�ͷ��Ϣ��Ĭ�ϴӳ�ʼ������ʱָ���ı�ͷ
        Row row=sheet.getRow(this.sheetHeadIndex);
        if(row==null||row.getPhysicalNumberOfCells()<=0){
            throw new RuntimeException("初始化表头信息失败，无法正常获取数据");
        }
        List<String> sheetHeadData=new ArrayList<String>();
        for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
            Cell cell=row.getCell(i);
            String value=cellValueConvert.getCellData(cell);
            sheetHeadData.add(value);
        }
        this.sheetHeadData=sheetHeadData;
	}
	
	private List<Map<String,String>> parseSheetData(Sheet sheet,int startIndex,int endIndex){
		List<Map<String,String>> result=new ArrayList<Map<String,String>>();
        //��ʼ��ȡ����
        for (int i = startIndex; i <= endIndex; i++) {
            Row row = sheet.getRow(i);
            //����ÿ�����ݲ����뼯����
            Map<String,String> rowData=parseRow(row);
            if(rowData!=null){
                //�����ݲ�Ϊ�ղŸ�ֵ
                result.add(rowData);
            }
        }
        return result;
	}
	
	//����ÿ�����ݳ�ExcelData�ļ���
	private Map<String,String> parseRow(Row row){
        if(row==null){
            return null;
        }
        Map<String,String> linked = new HashMap<String,String>();
        for (int j = row.getFirstCellNum(); j < this.sheetHeadData.size(); j++) {
            Cell cell = row.getCell(j);
            //��ȡÿ����Ԫ�񲢸�ֵ
            linked.put(this.sheetHeadData.get(j), cellValueConvert.getCellData(cell));
        }
        //���������ݣ��ж��Ƿ�����Ϊ��
        return filterEmptyData(linked);
    }
    
    //�����������Ƿ�ȫΪ��(������һ������value��Ϊ�ռ���Ϊ������)�����ȫΪ���򷵻ؿ�
	private Map<String,String> filterEmptyData(Map<String,String> linked){
        if(linked==null||linked.size()==0){
            return null;
        }
        for (String excelData : linked.values()) {
            if(excelData!=null){
                return linked;
            }
        }
        return null;
    }
	
	protected Sheet initSheet(int sheetIndex,InputStream in) throws IOException {
		HSSFWorkbook hwb = new HSSFWorkbook(in);
		return hwb.getSheetAt(sheetIndex);
	}
}
