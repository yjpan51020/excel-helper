package com.mi_girl.excel.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mi_girl.excel.common.ExcelVersion;
import com.mi_girl.excel.common.utils.Tools;
import com.mi_girl.excel.model.ClazzObject;
import com.mi_girl.excel.reader.ExcelReadConfig;

public class ClazzObjectExcelReaderConfig implements ExcelReadConfig<ClazzObject> {
	
	private String filePath;

	public ClazzObjectExcelReaderConfig(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public ClazzObject convert(Map<String, String> rowData) {
		ClazzObject clazz=new ClazzObject();
		clazz.setId(rowData.get("id"));
		clazz.setName(rowData.get("name"));
		return clazz;
	}

	@Override
	public Integer sheetHeadIndex() {
		return 0;
	}

	@Override
	public Map<String, String> sheetHeadMapField() {
		Map<String,String> headMapField=new HashMap<String,String>();
		headMapField.put("编号", "id");
		headMapField.put("名称", "name");
		return headMapField;
	}

	@Override
	public List<String> sheetHeadData() {
		return null;
	}

	@Override
	public String filePath() {
		return filePath;
	}

	@Override
	public ExcelVersion excelVersion() {
		return Tools.getExcelVersionByFilePath(filePath);
	}

}
