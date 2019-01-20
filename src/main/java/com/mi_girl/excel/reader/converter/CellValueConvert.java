package com.mi_girl.excel.reader.converter;

import org.apache.poi.ss.usermodel.Cell;

public interface CellValueConvert {
    String getCellData(Cell cell); 
}
