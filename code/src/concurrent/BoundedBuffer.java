package concurrent;

import java.util.concurrent.Semaphore;

public class BoundedBuffer<E> {
	
	private final Semaphore availableItems;
	private final Semaphore availableSpaces;
	
	private final E[] items;
	private int putPosition;
	private int takePosition;
	
	public BoundedBuffer(Semaphore availableItems, Semaphore availableSpaces, E[] items) {
		this.availableItems = availableItems;
		this.availableSpaces = availableSpaces;
		
		this.items = items;
	}
	
	@SuppressWarnings("unchecked")
	public BoundedBuffer(int capacity) {
		this.availableItems = new Semaphore(0);
		this.availableSpaces = new Semaphore(capacity);
		
		this.items = (E[]) new Object[capacity];
	}
	
	public boolean isEmpty() {
		return this.availableItems.availablePermits() == 0;
	}
	
	public boolean isFull() {
		return this.availableSpaces.availablePermits() == 0;
	}
	
	public void put(E x) throws InterruptedException {
		this.availableSpaces.acquire();
		doInsert(x);
		this.availableItems.release();
	}
	
	public synchronized void doInsert(E x) {
		int i = this.putPosition;
		items[i] = x;
		this.putPosition = (++i == this.items.length) ? 0 : i;
	}
	
	public E take() throws InterruptedException {
		this.availableItems.acquire();
		E item = doExtract();
		this.availableSpaces.release();
		
		return item;
	}
	
	public E doExtract() {
		int i = this.takePosition;
		E x = this.items[i];
		
		this.items[i] = null;
		this.takePosition = (++i == this.items.length) ? 0 : i;
		
		return x;
	}
}
