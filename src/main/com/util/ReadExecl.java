package main.com.util;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExecl {

	public ReadExecl() {
	}

	public static Object[][] initTestData(String filename, String sheetname)
			throws Exception {
		Workbook book = null;
		Sheet sheet = null;
		Row row = null;
		InputStream is = null;
		String filePath = System.getProperty("user.dir") + "\\datas\\" + filename;
		String ext = filePath.substring(filePath.lastIndexOf("."));
		try {
			is = new FileInputStream(filePath);
			if (".xls".equals(ext)) {
				book = new HSSFWorkbook(is);
			} else if (".xlsx".equals(ext)) {
				book = new XSSFWorkbook(is);
			} else {
				book = null;
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			is.close();
		}
		sheet = book.getSheet(sheetname);
		int rownum = sheet.getLastRowNum();
		int colnum = sheet.getRow(0).getPhysicalNumberOfCells();

		Object[][] caseData = new Object[rownum][colnum];
		for (int i = 0; i < rownum; i++) {
			row = sheet.getRow(i + 1);
			for (int j = 0; j < colnum; j++) {
				if (row.getCell(j) == null || "".equals(row.getCell(j))) {
					caseData[i][j] = "";
				} else {
					row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
					caseData[i][j] = row.getCell(j).getStringCellValue();
				}
			}
		}
		return caseData;
	}
}
