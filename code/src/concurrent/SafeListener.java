package concurrent;

import java.awt.Event;
import java.util.EventListener;

public class SafeListener {
	
	private final EventListener listener;
	
	private SafeListener(){
		listener = new EventListener(){
			public void onEvent(Event e){
				//doSomething(e);
			}
		};
	}
	
//	public static SafeListener newInstance(EventSource source){
//		SafeListener safeListener = new SafeListener();
//		
//	}
}
