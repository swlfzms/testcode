package concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import sun.launcher.resources.launcher;
import sun.launcher.resources.launcher_zh_TW;

public class Memoizerl<A, V> implements Computable<A, V> {
	
	private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;
	
	public Memoizerl(Computable<A, V> c) {
		// TODO Auto-generated constructor stub
		this.c = c;
	}
	
	@Override
	public V compute(final A args) throws InterruptedException {
		// TODO Auto-generated method stub
		while (true) {
			Future<V> f = cache.get(args);
			if (f == null) {
				Callable<V> eval = new Callable<V>() {
					
					@Override
					public V call() throws Exception {
						// TODO Auto-generated method stub
						return c.compute(args);
					}
					
				};
				FutureTask<V> ft = new FutureTask<V>(eval);
				f = cache.putIfAbsent(args, ft);
				if (f == null) {
					f = ft;
					ft.run();
				}				
			}
			try {
				return f.get();
			} catch(CancellationException cancellationException){
				cache.remove(args, f);
			}catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
}
