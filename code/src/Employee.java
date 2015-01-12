import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Employee {
	private String id;// 雇员的标识号码
	private String name;// 雇员姓名
	private String department;// 该雇员所在部门
	private String phone;// 该雇员联系电话
	private int salary;// 该雇员薪资
	private String origin;// 该雇员信息的来源

	public Employee(String id) {
		this.id = id;
		getDataFromlnfoCenter();
	}

	// 模拟从 database 读取。
	private void getDataFromlnfoCenter() {
		// 和数据库建立连接井查询该雇员的信息，将查询结果赋值
		// 给name，department，plone，salary等变量
		// 同时将origin赋值为"From DataBase"
		try {
			File file = new File("D://output.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String temp = br.readLine();
			br.close();
			String[] content = temp.split("\\*");
			name = content[0];
			department = content[1];
			phone = content[2];
			salary = Integer.parseInt(content[3]);
			origin = "from database";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getId() {
		return id;
	}
	
}
