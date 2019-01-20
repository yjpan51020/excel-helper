package com.mi_girl.excel.reader.converter.impl;

import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import com.mi_girl.excel.reader.converter.CellValueConvert;




/**
 * �������������ݶ���ȡ���ַ�����ʽ
 */
public class CellValueToString implements CellValueConvert {

	public String getCellData(Cell cell) {
		if (cell == null) { 
            return null; 
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK://������
                return null;
            case Cell.CELL_TYPE_BOOLEAN://����
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_STRING://�ַ���
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC://����
                // ������������ͷ������ڵĵ�ǰ������
                if (HSSFDateUtil.isCellDateFormatted(cell)) { 
                    return String.valueOf(cell.getDateCellValue().getTime()); 
                }
                //�����ǰ�����ݲ������ڣ��򷵻���ֵ���ַ���(���%1==0�������������formatһ�½�С��λȥ��)
                return cell.getNumericCellValue() % 1 == 0 ? new DecimalFormat("0").format(cell.getNumericCellValue()) : String.valueOf(cell.getNumericCellValue());
            default:
                return null;
        }
	}
}
