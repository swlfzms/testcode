package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

public class TestThreadFactory implements ThreadFactory {
	
	public final AtomicInteger numCreated = new AtomicInteger();
	private final ThreadFactory threadFactory = Executors.defaultThreadFactory();
	
	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		this.numCreated.incrementAndGet();
		return threadFactory.newThread(r);
	}
	
	public void testPoolExpansion() throws InterruptedException {
		int MAX_SIZE = 10;
		ExecutorService service = Executors.newFixedThreadPool(MAX_SIZE);
		
		for (int i = 0; i < 10 * MAX_SIZE; i++) {
			service.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(Long.MAX_VALUE);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
			});
		}
		
		for (int i = 0; i < 20 && this.numCreated.get() < MAX_SIZE; i++) {
			Thread.sleep(1000);
		}
		assertEquals(this.numCreated.get(), MAX_SIZE);
		service.shutdown();
	}

	private void assertEquals(int i, int mAX_SIZE) {
		// TODO Auto-generated method stub
		if(i==mAX_SIZE){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
}
