package concurrent;

public class Test {
	
	/**
	 * main(������һ�仰�����������������)
	 * TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)
	 * TODO(�����������������ע������ �C ��ѡ)
	 *
	 * @Title: main
	 * @Description: TODO
	 * @param @param args    �趨�ļ�
	 * @return void    ��������
	 * @throws
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringHelper stringHelper = new StringHelper("Hello");
		
		StringHelperThread helperThread1 = new StringHelperThread(stringHelper, "num1");
		StringHelperThread helperThread2 = new StringHelperThread(stringHelper, "num2");
		
		Thread thread1 = new Thread(helperThread1);
		Thread thread2 = new Thread(helperThread2);
		
		thread1.start();
		thread2.start();
	}
	
}
class StringHelperThread implements Runnable{

	private StringHelper helper;
	private String str;
	public StringHelperThread(StringHelper helper, String str) {
		// TODO Auto-generated constructor stub
		this.helper = helper;
		this.str = str;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String tmp = this.helper.getStr();
		this.helper.setStr(tmp+" "+this.str);
		System.out.println(this.helper.getStr());
	}
	
}