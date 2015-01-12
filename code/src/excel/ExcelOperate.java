package excel;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.SQLStatement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sun.org.apache.xml.internal.utils.res.IntArrayWrapper;

import dao.DataDao;
import database.ConnectionPool;

import service.TyphoonYearService;

public class ExcelOperate {
	private Map<String, List<String>> yearMap = new HashMap<String, List<String>>();
	private List<String> list = new ArrayList<String>();
	private List<String> newYear = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		ExcelOperate excelOperate = new ExcelOperate();
		excelOperate.reduce("./20112014bk.xls");
	}
	
	public void reduce(String filePath) throws Exception {
		ExcelOperate excelOperate = new ExcelOperate();
		TyphoonYearService typhoonYearService = new TyphoonYearService();
		excelOperate.list = typhoonYearService.getTyphoonYear();
		
		File file = new File(filePath);
		String[][] result = getData(file, 1);
		
		int rowLength = result.length;
		BufferedWriter bufferedWriter = null;
		BufferedWriter smBufferedWriter = null;
		
		String tmpYear = "";
		String tmpSN = "";
		int old = 0;
		List<String> snList = new ArrayList<String>();
		List<String[]> snNameList = new ArrayList<String[]>();
		
		for (int i = 0; i < rowLength; i++) {
			if (!snList.contains(result[i][0])) {
				snList.add(result[i][0]);
				String[] name = new String[2];
				name[0] = result[i][10];
				name[1] = result[i][11];
				snNameList.add(name);
			}
			
			String year = result[i][0].substring(0, 4);
			
			if (!year.equals(tmpYear)) {
				if (bufferedWriter != null) {
					bufferedWriter.close();
					smBufferedWriter = new BufferedWriter(new FileWriter(new File("./tmp/sn" + tmpYear + ".txt")));
					for (int j = 0; j < snList.size() - 1; j++) {
						smBufferedWriter.write(snList.get(j) + "," + snNameList.get(j)[0] + "," + snNameList.get(j)[1]
								+ "\r\n");
					}
					smBufferedWriter.close();
					snList.clear();
					snNameList.clear();
				}
				tmpYear = year;
				bufferedWriter = new BufferedWriter(new FileWriter(new File("./tmp/" + year + ".txt")));
				
			}
			for (int j = 0; j < result[i].length - 3; j++) {
				bufferedWriter.write(result[i][j] + ",");
			}
			bufferedWriter.write(result[i][result[i].length - 3] + "\r\n");
		}
		if (bufferedWriter != null) {
			// just for the last file sn1945.
			smBufferedWriter = new BufferedWriter(new FileWriter(new File("./tmp/sn" + tmpYear + ".txt")));
			for (int j = 0; j < snList.size(); j++) {
				smBufferedWriter.write(snList.get(j) + "," + snNameList.get(j)[0] + "," + snNameList.get(j)[1]
						+ "\r\n");
			}
			smBufferedWriter.close();
			bufferedWriter.close();
		}
		createTableAndImportData();
	}
	
	public void createTableAndImportData() throws SQLException {
		File directory = new File("./tmp");
		File[] files = directory.listFiles();
		DataDao dataDao = new DataDao();
		
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			if (fileName.contains("s")) {
				String year = fileName.substring(2, 6);
				String sql = SQLStatement.CREATETYPHOONSNTABLE.replace("TABLENAME", year);
				dataDao.excute(sql);
				sql = SQLStatement.INSERTTYPHOONSN.replace("FILENAME", "'./tmp/" + fileName + "'").replace("TABLENAME",
						year);
				dataDao.excute(sql);
			} else {
				String year = fileName.substring(0, 4);
				String sql = SQLStatement.CREATETYPHOONDATATABLE.replace("TABLENAME", year);
				dataDao.excute(sql);
				sql = SQLStatement.INSERTTYPHOONDATA.replace("FILENAME", "'./tmp/" + fileName + "'").replace(
						"TABLENAME", year);
				// System.out.println(sql);
				dataDao.excute(sql);
			}
			// files[i].delete();
		}
		dataDao.release();
		
	}
	
	/**
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file 读取数据的源Excel
	 * 
	 * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * 
	 * @return 读出的Excel中数据的内容
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws IOException
	 */
	
	public static String[][] getData(File file, int ignoreRows) throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum();
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "NULL");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
					String value = "NULL";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 注意：一定要设成这个，否则可能会出现乱码
						cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							value = "NULL";
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					if (value.length() > 2)
						value = value.trim();
					values[columnIndex] = value.equals("") ? "NULL" : value;
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}
	
	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str 要处理的字符串
	 * 
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
}