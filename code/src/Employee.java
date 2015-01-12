import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Employee {
	private String id;// ��Ա�ı�ʶ����
	private String name;// ��Ա����
	private String department;// �ù�Ա���ڲ���
	private String phone;// �ù�Ա��ϵ�绰
	private int salary;// �ù�Աн��
	private String origin;// �ù�Ա��Ϣ����Դ

	public Employee(String id) {
		this.id = id;
		getDataFromlnfoCenter();
	}

	// ģ��� database ��ȡ��
	private void getDataFromlnfoCenter() {
		// �����ݿ⽨�����Ӿ���ѯ�ù�Ա����Ϣ������ѯ�����ֵ
		// ��name��department��plone��salary�ȱ���
		// ͬʱ��origin��ֵΪ"From DataBase"
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
