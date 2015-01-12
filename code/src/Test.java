import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class Test {
	
	/**
	 * @param args
	 */
	// -----------------------------------------------
	static int runCount = 0;
	static int[] pictureID = new int[42];
	
	public static void init() {
		int res;
		for (int i = 0; i < 21; i++) {
			res = (int) (Math.random() * 20) + 1;
			pictureID[i] = res;
		}
		for (int i = 21; i < 42; i++) {
			pictureID[i] = pictureID[i - 21];
		}
	}
	
	public static int[] rom(int[] arr) {
		int[] arr2 = new int[arr.length];
		int count = arr.length;
		int cbRandCount = 0;// 索引
		int cbPosition = 0;// 位置
		int k = 0;
		
		do {
			
			Random rand = new Random();
			int r = count - cbRandCount;
			cbPosition = rand.nextInt(r);
			arr2[k++] = arr[cbPosition];
			arr[cbPosition] = arr[r - 1];// 将最后一位数值赋值给已经被使用的cbPosition
			cbRandCount++;
		} while (cbRandCount < count);
		return arr2;
	}
	
	// ----------------------------------------------
	
	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\1418570570076.amr"));
		AudioInputStream ais = new AudioInputStream(fis, null, runCount);
		
	}
	
	public static void print(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
