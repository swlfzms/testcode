package excel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.omg.PortableServer.POAPackage.WrongAdapter;

public class TyphoonName {
	
	public static void main(String[] args) {
		jxl.Workbook readwb = null;
		try {
			InputStream inputStream = new FileInputStream("./name_2011_2014.xls");
			readwb = Workbook.getWorkbook(inputStream);
			
			Sheet readSheet = readwb.getSheet(0);
			int rsColumns = readSheet.getColumns();
			int rsRows = readSheet.getRows();
			String[][] name = new String[rsRows][rsColumns];
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("./name.txt")));
			int k = 0;
			for (int i = 0; i < rsRows; i += 2) {
				for (int j = 0; j < rsColumns; j++) {
					Cell enCell = readSheet.getCell(j, i);
					Cell zhCell = readSheet.getCell(j, i + 1);
					bufferedWriter.write(zhCell.getContents() + "-" + enCell.getContents() + "\r\n");
				}
			}
			
			bufferedWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
