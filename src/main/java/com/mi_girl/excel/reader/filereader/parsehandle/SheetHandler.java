package com.mi_girl.excel.reader.filereader.parsehandle;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mi_girl.excel.reader.converter.Cell2007ValueConvert;
import com.mi_girl.excel.reader.converter.impl.Cell2007ValueToString;


public class SheetHandler extends DefaultHandler{
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	//��ǰ�е��±�
	private int rowIndex;
	//��Ԫ������
	private String cellType;
	//��Ԫ�����ݵ�����
	private String dataType;
	//��ǰ��Ԫ�������
	private String point;
	//��Ҫ��ȡ���ݵĿ�ʼ�±�
	private int startIndex;
	//��Ҫ��ȡ���ݵĽ����±�
	private int endIndex;
	//��ͷ�������ڵ��±�(Ϊ��ʱ��ʾ����ȡ��ͷ����)
	private Integer headIndex;
	//��ȡ�ı�ͷ����
	private Map<String,String> headData;
	//��ȡ�ı�����
    private Map<String,Map<String,String>> sheetData=new HashMap<String,Map<String,String>>();
    //��ǰ�е�����
    private Map<String,String> rowData;
    //��������ת��
	private Cell2007ValueConvert cell2007ValueConvert=new Cell2007ValueToString();
	
	private int sheetLastIndex;

	public SheetHandler(ParseHandleConfig parseHandleConfig) {
		this.sst = parseHandleConfig.getSharedStringsTable();
		this.headIndex=parseHandleConfig.getHeadIndex();
		this.startIndex=parseHandleConfig.getStartIndex();
		this.endIndex=parseHandleConfig.getEndIndex();
	}

	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if("row".equals(name)){
			rowIndex=Integer.parseInt(attributes.getValue("r"));
			sheetLastIndex=rowIndex;
			rowData=new HashMap<String, String>();
		}
		if (name.equals("c")) {
			point=attributes.getValue("r");
			cellType = attributes.getValue("t");
			dataType=attributes.getValue("s");
			nextIsString=(cellType != null&&"s".equals(cellType));
		}
		lastContents = "";
	}

	public void endElement(String uri, String localName, String name) throws SAXException {
		if(rowIndex!=headIndex&&(rowIndex<startIndex||(endIndex>0&&rowIndex>endIndex))){
			return;
		}
		if (nextIsString) {
			int idx = Integer.parseInt(lastContents);
			lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
			nextIsString = false;
		}
		if (name.equals("v")) {
			rowData.put(point, cell2007ValueConvert.getCellData(lastContents, dataType));
		}
		if("row".equals(name)){
			if(headIndex!=null&&rowIndex==headIndex.intValue()){
				headData=new HashMap<String, String>();
				headData.putAll(rowData);
			}else{
				sheetData.put(String.valueOf(rowIndex), rowData);
			}
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		lastContents += new String(ch, start, length);
	}
	
	public Map<String,Map<String,String>> getSheetData(){
		return this.sheetData;
	}
	
	public Map<String,String> getHeadData(){
		return this.headData;
	}

	public int getSheetLastIndex() {
		return sheetLastIndex;
	}
}
