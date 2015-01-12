package jframe;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TextArea;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MServer extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static int i = 0;
	static int runTime = 0;
	static TextArea ta = new TextArea();
	static boolean regStatus = false;
	private static TrayIcon trayIcon = null;
	static JFrame mf = new JFrame();
	static SystemTray tray = SystemTray.getSystemTray();
	
	public static void myFrame() { // ����
	
		mf.setLocation(300, 100);
		mf.setSize(500, 300);
		mf.setTitle("XXXXϵͳ");
		mf.setLayout(new BorderLayout());
		
		ImageIcon ll = new ImageIcon("./ha.jpg");// ��JFrame��ʹ��ͼƬ
		JLabel i = new JLabel(ll);
		
		JLabel j = new JLabel("XXXXϵͳv2.02", SwingConstants.CENTER);// ���ñ�ǩ����ʾ�ı�����
		JLabel k = new JLabel("���������������   2010.12,All Rights Reserved", SwingConstants.CENTER);// ���ñ�ǩ����ʾ�ı�����
		j.setFont(new java.awt.Font("", 1, 18));// ���ñ�ǩJ��ʾ����
		
		Panel p1 = new Panel();// ʵ�������P1
		p1.setLayout(new BorderLayout());// �������P1�пؼ����з�ʽ
		
		final Panel p11 = new Panel();
		p11.setLayout(new BorderLayout());// ����P11�ؼ����з�ʽ
		p11.add(j, BorderLayout.NORTH);// P11�Ϸ���ʾ�ؼ�J
		p11.add(k, BorderLayout.SOUTH);// P11�·���ʾ�ؼ�K
		
		final JLabel t = new JLabel("", SwingConstants.CENTER);// ���ñ�ǩt������ʾʱ��
		t.setFont(new java.awt.Font("", 1, 26));// ���ñ�ǩt����
		t.setForeground(Color.blue);// ���ñ�ǩtǰ��ɫ��
		
		p11.add(t, BorderLayout.CENTER);// ��ǩt��ʾ���м�λ��
		
		Timer timer_date = new Timer(1000, new ActionListener() { // ��������ʱ��
					public void actionPerformed(ActionEvent evt) {
						t.setText(new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss").format(new Date()));
						
					}
				});
		timer_date.start();
		
		p1.add(i, BorderLayout.WEST);// ���ͼ��
		p1.add(p11, BorderLayout.CENTER);// p11��P1�м�����
		
		mf.add(p1, BorderLayout.NORTH);// ��p1��ʾ�ڴ����Ϸ�
		
		mf.add(ta, BorderLayout.CENTER);// ��һ�������ı�������ʾ�������м�
		
		mf.setVisible(true);// ʹ���ڿɼ�
		
		mf.addWindowListener(new WindowAdapter() { // ���ڹر��¼�
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			};
			
			public void windowIconified(WindowEvent e) { // ������С���¼�
			
				mf.setVisible(false);
				MServer.miniTray();
				
			}
			
		});
		
	}
	
	private static void miniTray() { // ������С��������������
	
		ImageIcon trayImg = new ImageIcon("./ha.jpg");// ����ͼ��
		
		PopupMenu pop = new PopupMenu(); // ���������һ��˵�
		MenuItem show = new MenuItem("��ԭ");
		MenuItem exit = new MenuItem("�˳�");
		
		show.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { // ���»�ԭ��
			
				tray.remove(trayIcon);
				mf.setVisible(true);
				mf.setExtendedState(JFrame.NORMAL);
				mf.toFront();
			}
			
		});
		
		exit.addActionListener(new ActionListener() { // �����˳���
		
			public void actionPerformed(ActionEvent e) {
				
				tray.remove(trayIcon);
				System.exit(0);
				
			}
			
		});
		
		pop.add(show);
		pop.add(exit);
		
		trayIcon = new TrayIcon(trayImg.getImage(), "xxxxϵͳ", pop);
		trayIcon.setImageAutoSize(true);
		
		trayIcon.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) { // �����˫���¼�
			
				if (e.getClickCount() == 2) {
					
					tray.remove(trayIcon); // ��ȥ����ͼ��
					mf.setVisible(true);
					mf.setExtendedState(JFrame.NORMAL); // ��ԭ����
					mf.toFront();
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
	
	public static void main(String[] args) {
		
		new MServer();
		MServer.myFrame();
		
	}
}