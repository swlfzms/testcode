package excel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class FillName {
	
	private Map<String, String[]> map = new HashMap<String, String[]>();
	
	public void init() throws BiffException, IOException {
		File file = new File("./name_2011_2014.xls");
		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);
		
		int rowCount = sheet.getRows();
		
		for (int i = 1; i < rowCount; i++) {
			Cell snCell = sheet.getCell(0, i);
			Cell zhCell = sheet.getCell(1, i);
			Cell enCell = sheet.getCell(2, i);
			
			String sn = snCell.getContents();
			String[] name = new String[] { zhCell.getContents(), enCell.getContents() };
			
			map.put(sn, name);
		}
		
		workbook.close();
	}
	
	public void map() throws BiffException, IOException, RowsExceededException, WriteException {
		init();
		
		File sourseFile = new File("./20112014.xls");
		File destinationFile = new File("./20112014bk.xls");
		
		Workbook readWorkbook = Workbook.getWorkbook(sourseFile);
		Sheet readSheet = readWorkbook.getSheet(0);
		
		WritableWorkbook writableWorkbook = Workbook.createWorkbook(destinationFile);
		WritableSheet writableSheet = writableWorkbook.createSheet("data", 0);
		
		int rowCount = readSheet.getRows();
		int colCount = readSheet.getColumns();
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				Cell cell = readSheet.getCell(j, i);
				Label label = new Label(j, i, cell.getContents());
				writableSheet.addCell(label);
			}
			
			Cell cell = readSheet.getCell(0, i);
			String sn = cell.getContents();
			
			if (map.containsKey(sn)) {
				String[] value = map.get(sn);
				Label zhLabel = new Label(10, i, value[0]);
				writableSheet.addCell(zhLabel);
				Label enLabel = new Label(11, i, value[1]);
				writableSheet.addCell(enLabel);
			}
		}
		writableWorkbook.write();
		readWorkbook.close();
		writableWorkbook.close();
	}
	
	public static void main(String[] args) throws BiffException, IOException, RowsExceededException, WriteException {
		FillName fillName = new FillName();
		fillName.map();
	}
}
