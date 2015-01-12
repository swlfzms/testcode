package concurrent;

public class NoVisibility {
	
	private static boolean ready;
	private static int number;
	
	private static class ReaderThread extends Thread{
		
		public void run(){
			
			while(!ready){
				//System.err.println("wait");
				Thread.yield();
			}
			System.out.println(number);
		}
	}
	
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
		new ReaderThread().start();
		number = 42;
		ready = true;
	}
	
}
