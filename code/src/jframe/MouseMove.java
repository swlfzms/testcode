package jframe;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MouseMove extends JFrame {
	
	private TrayIcon trayIcon = null;
	private Button start;
	private Button stop;
	private int starting;
	private Mouse mouse;
	private Thread thread;
	private SystemTray tray;
	
	public MouseMove() {
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		
		buttonInit();
		container.add(start);
		container.add(stop);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
		setLocation(width, height);
		setResizable(false);
		this.setSize(150, 60);
		this.setTitle("MouseMove");
		this.setVisible(true);
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			};
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				MouseMove.this.setVisible(false);
				trayInit();
			}
		});
	}
	
	public void trayInit() {
		tray = SystemTray.getSystemTray();
		ImageIcon trayImg = new ImageIcon("./ha.jpg");;
		PopupMenu pop = new PopupMenu();
		MenuItem exit = new MenuItem("exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MouseMove.this.tray.remove(trayIcon);
				System.exit(0);
			}
		});
		pop.add(exit);
		trayIcon = new TrayIcon(trayImg.getImage(), "MouseMove", pop);
		trayIcon.setImageAutoSize(true);
		trayIcon.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					MouseMove.this.tray.remove(trayIcon);
					MouseMove.this.setVisible(true);
					MouseMove.this.setExtendedState(JFrame.NORMAL);
					MouseMove.this.toFront();
				}
			}
		});
		try {
			tray.add(trayIcon);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void buttonInit() {
		start = new Button("start");
		stop = new Button("stop");
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (starting == 0) {
					Mouse mouse = new Mouse();
					Thread thread = new Thread(mouse);
					System.out.println("start.....");
					System.err.println(thread.getName());
					thread.start();
					starting = 1;
					MouseMove.this.mouse = mouse;
					MouseMove.this.thread = thread;
				}
			}
		});
		stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("stop......");
				if (starting == 1) {
					MouseMove.this.mouse.setRunning(1);
					starting = 0;
					MouseMove.this.mouse.getTimer().setCount(10);
				}
			}
		});
	}
	
	/**
	 * main(这里用一句话描述这个方法的作用) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @Title: main
	 * @Description: TODO
	 * @param @param args 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MouseMove();
		
	}
	
}

class Mouse implements Runnable {
	
	private Robot robot;
	private int height;
	private int width;
	private long time;
	private int running;
	private int state;
	private String name;
	private Timer timer;
	
	public Mouse() {
		name = Thread.currentThread().getName();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2;
		width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
		time = 10 * 1000;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (this) {
			while (running == 0) {
				if (state == 0) {
					robot.mouseMove(width + 20, height + 20);
					System.out.println("move up");
					state = 1;
				} else {
					robot.mouseMove(width - 20, height - 20);
					System.out.println("move down");
					state = 0;
				}
				timer = new Timer((int) time / 1000);
				timer.start();
				while (timer.getCount() < timer.getTime()) {
				}
			}
		}
		
	}
	
	public int getRunning() {
		return running;
	}
	
	public void setRunning(int running) {
		this.running = running;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
}

class Timer extends Thread {
	private int time = 10;
	private int count = 1;
	
	public Timer(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (count < time) {
			try {
				System.out.println(count);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count++;
		}
	}
}