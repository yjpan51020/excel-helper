package com.mi_girl.excel.reader.filereader.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.mi_girl.excel.common.utils.ExcelPointGenerater;
import com.mi_girl.excel.common.utils.Tools;
import com.mi_girl.excel.reader.filereader.ExcelFileReadConfig;
import com.mi_girl.excel.reader.filereader.ExcelFileReader;
import com.mi_girl.excel.reader.filereader.FileReadResult;
import com.mi_girl.excel.reader.filereader.parsehandle.ParseHandleConfig;
import com.mi_girl.excel.reader.filereader.parsehandle.SheetHandler;


public class Excel2007FileReader extends ExcelFileReader {
	
	private SheetHandler sheetHandler;

	public Excel2007FileReader(ExcelFileReadConfig excelFileReadConfig) {
		super(excelFileReadConfig);
		//��sax��ȡʱ�±���1��ʼ ���Դ˴�������1
		if(sheetHeadIndex!=null&&sheetHeadIndex>=0){
			sheetHeadIndex++;
		}
	}

	@Override
	public FileReadResult readExcel(int sheetIndex, int startIndex, int endIndex) {
		try {
			//��sax��ȡʱ�±���1��ʼ ���Դ˴�������1
			startIndex++;
			endIndex=endIndex<0?endIndex:endIndex+1;
			validateParam(sheetIndex, startIndex, endIndex);
			//��sax��ȡʱ�±���1��ʼ ���Դ˴�������1
			sheetIndex++;
			processOneSheet(sheetIndex, startIndex, endIndex);
			List<Map<String, String>> result=convert(startIndex,endIndex);
			return new FileReadResult(result, sheetHandler.getSheetLastIndex());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//����ȡ������ת���ɱ�ͷ����Ϊkey������
	private List<Map<String, String>> convert( int startIndex, int endIndex) {
		if(sheetHandler==null||Tools.mapIsEmpty(sheetHandler.getSheetData())){
			return Collections.emptyList();
		}
		if(Tools.mapIsEmpty(sheetHandler.getHeadData())&&Tools.collectionIsEmpty(sheetHeadData)){
			return Collections.emptyList();
		}
		Map<String,String> headData=createHeadData();
		List<Map<String, String>> result=new ArrayList<Map<String,String>>();
		endIndex=calculateEndIndex(endIndex);
		for (int i = startIndex; i <= endIndex; i++) {
			Map<String,String> rowData=sheetHandler.getSheetData().get(String.valueOf(i));
			if(Tools.mapIsEmpty(rowData)){
				continue;
			}
			Map<String,String> afterConversion=changeKeyToHeaddata(i, rowData, headData);
			result.add(afterConversion);
			
		}
		return result;
	}
	
	private int calculateEndIndex(int endIndex) {
		if(endIndex<0){
			return sheetHandler.getSheetLastIndex();
		}
		if(endIndex>sheetHandler.getSheetLastIndex()){
			return sheetHandler.getSheetLastIndex();
		}
		return endIndex;
	}

	private Map<String,String> changeKeyToHeaddata(int currentRowIndex,Map<String,String> rowData,Map<String,String> headData){
		Map<String,String> afterConversion=new HashMap<String, String>();
		for (Entry<String, String> entry : headData.entrySet()) {
			String key=entry.getKey()+currentRowIndex;
			afterConversion.put(entry.getValue(), rowData.get(key));
		}
		return afterConversion;
	}

	private Map<String, String> createHeadData() {
		if(!Tools.collectionIsEmpty(sheetHeadData)){
			return fromConfig();
		}
		return fromExcel();
	}
	
	private Map<String, String> fromConfig(){
		Map<String,String> headData=new HashMap<String, String>();
		for (int i = 0; i < sheetHeadData.size(); i++) {
			String value=sheetHeadData.get(i);
			if(value!=null){
				headData.put(ExcelPointGenerater.generaterColumnPoint(i), value);
			}
		}
		return headData;
	}
	
	private Map<String, String> fromExcel(){
		Map<String,String> headData=new HashMap<String, String>();
		for (Entry<String, String> entry : sheetHandler.getHeadData().entrySet()) {
			String key = entry.getKey();
			key=key.replaceAll("\\d","");
			headData.put(key, entry.getValue());
		}
		return headData;
	}

	private void processOneSheet(int sheetIndex,int startIndex, int endIndex) {
		InputStream sheet2=null;
		try {
			OPCPackage pkg = OPCPackage.open(filePath);
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();
			ParseHandleConfig parseHandleConfig = new ParseHandleConfig(sst, startIndex, endIndex, sheetHeadIndex);
			this.sheetHandler = new SheetHandler(parseHandleConfig);
			XMLReader parser = fetchSheetParser(sheetHandler);
			sheet2 = r.getSheet("rId" + sheetIndex);
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
		} catch (Exception e) {
			throw new RuntimeException("读取excel失败",e);
		} finally {
            IOUtils.closeQuietly(sheet2);
		}
	}

	private XMLReader fetchSheetParser(ContentHandler handler) throws SAXException {
		XMLReader parser =
			XMLReaderFactory.createXMLReader(
					"org.apache.xerces.parsers.SAXParser"
			);
		parser.setContentHandler(handler);
		return parser;
	}

}
