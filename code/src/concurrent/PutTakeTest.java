package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutTakeTest {
	
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	private final AtomicInteger putSum = new AtomicInteger(0);
	private final AtomicInteger takeSum = new AtomicInteger(0);
	
	private final CyclicBarrier barrier;
	private final BoundedBuffer<Integer> bb;
	private final int nTrials;
	private final int nPairs;
	
	public PutTakeTest(int capacity, int npairs, int ntrials) {
		this.nPairs = npairs;
		this.nTrials = ntrials;
		this.bb = new BoundedBuffer<Integer>(capacity);
		this.barrier = new CyclicBarrier(this.nPairs * 2 + 1);
	}
	
	public static void main(String[] args) {
		
	}
	
	void test() {
		try {
			for (int i = 0; i < this.nPairs; i++) {
				pool.execute(new Producer());
				pool.execute(new Consumer());
			}
			this.barrier.await();
			this.barrier.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	class Producer implements Runnable {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int seed = (this.hashCode() ^ (int) System.nanoTime());
			int sum = 0;
			
			try {
				PutTakeTest.this.barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int i = PutTakeTest.this.nTrials; i > 0; i--) {
				try {
					PutTakeTest.this.bb.put(seed);
					sum += seed;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			putSum.getAndAdd(sum);
			try {
				PutTakeTest.this.barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	class Consumer implements Runnable {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				PutTakeTest.this.barrier.await();
				int sum = 0;
				
				for (int i = PutTakeTest.this.nTrials; i > 0; i--) {
					sum += barrier.getParties();
				}
				
				takeSum.addAndGet(sum);
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
