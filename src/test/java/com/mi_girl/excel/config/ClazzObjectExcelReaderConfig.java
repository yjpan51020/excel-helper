package com.mi_girl.excel.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mi_girl.excel.common.ExcelVersion;
import com.mi_girl.excel.common.utils.Tools;
import com.mi_girl.excel.model.ClazzObject;
import com.mi_girl.excel.reader.ExcelReadConfig;

/**
 * 写一个类实现接口
 * @author Administrator
 *
 */
public class ClazzObjectExcelReaderConfig implements ExcelReadConfig<ClazzObject> {
	
	private String filePath;

	public ClazzObjectExcelReaderConfig(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 此方法用于将MAP中的数据转换成对象可以手动写 也可以自己用json工具先
	 * 转字符串然后转对象
	 * 这里的ClazzObject就是实体对象
	 */
	@Override
	public ClazzObject convert(Map<String, String> rowData) {
		ClazzObject clazz=new ClazzObject();
		clazz.setId(rowData.get("id"));
		clazz.setName(rowData.get("name"));
		clazz.setStatus(rowData.get("status"));
		return clazz;
	}

	/**
	 * 这个方法用于返回表头所在行 (sheet的行数由0开始)
	 * 如果不需要工具帮你读表头就设置成-1
	 */
	@Override
	public Integer sheetHeadIndex() {
		return  null;
	}

	/**
	 * 表头与属性名映射的MAP
	 * 其中KEY为表头中的数据  VALUE 为实体对象的属性名
	 * 此处顺序不重要
	 */
	@Override
	public Map<String, String> sheetHeadMapField() {
		Map<String,String> headMapField=new HashMap<String,String>();
		headMapField.put("编号", "id");
		headMapField.put("名称", "name");
		headMapField.put("状态", "status");
		return headMapField;
	}

	/**
	 * 表头数据(如果sheetHeadIndex()方法设置了负数 此处必须有数据 )
	 * 接上面的假设为  编号,名称
	 */
	@Override
	public List<String> sheetHeadData() {
		return Arrays.asList("编号","名称","开班时间","状态");
	}

	/**
	 * 此方法需要返回文件所在地址
	 * 如C:\\temp.xlsx
	 */
	@Override
	public String filePath() {
		return filePath;
	}

	/**
	 * 此处需要返回excel版本直接用这个工具即可
	 */
	@Override
	public ExcelVersion excelVersion() {
		return Tools.getExcelVersionByFilePath(filePath);
	}

}
