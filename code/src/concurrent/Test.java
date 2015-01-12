package concurrent;

public class Test {
	
	/**
	 * main(这里用一句话描述这个方法的作用)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @Title: main
	 * @Description: TODO
	 * @param @param args    设定文件
	 * @return void    返回类型
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