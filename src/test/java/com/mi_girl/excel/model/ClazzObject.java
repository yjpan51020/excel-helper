package com.mi_girl.excel.model;

import java.util.Date;

import com.mi_girl.excel.common.annotation.ExcelField;
import com.mi_girl.excel.common.annotation.ReaderHeadIndex;
import com.mi_girl.excel.formatter.OpenTimeFormatter;
import com.mi_girl.excel.formatter.StatusFormatter;

@ReaderHeadIndex(0)
public class ClazzObject {

	@ExcelField(column = "编号", index = 1)
	private String id;

	@ExcelField(column = "名称", index = 2)
	private String name;

	@ExcelField(column = "开班时间", index = 3, formatter = OpenTimeFormatter.class)
	private Date openTime;

	@ExcelField(column = "状态", index = 5, formatter = StatusFormatter.class)
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	@Override
	public String toString() {
		return "ClazzObject [id=" + id + ", name=" + name + ", openTime=" + openTime + ", status=" + status + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
