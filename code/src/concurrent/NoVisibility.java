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
		new ReaderThread().start();
		number = 42;
		ready = true;
	}
	
}
