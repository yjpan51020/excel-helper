package com.mi_girl.excel.common.utils;

public class ExcelPointGenerater {
	
	private final static String[] COLUMN_POINT = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * ��ȡexcel������
	 * @param index
	 * @return
	 */
	public static String generaterColumnPoint(int index) {
		int current = index % 26;
		int nextIndex = index / 26;
		if (nextIndex == 0) {
			return COLUMN_POINT[current];
		}
		nextIndex--;
		return generaterColumnPoint(nextIndex) + COLUMN_POINT[current];
	}
}
