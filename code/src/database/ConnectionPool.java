package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

//����ģʽ����Ԫģʽ
public class ConnectionPool {
	
	private static Logger debugLogger = Logger.getLogger("DEBUG." + ConnectionPool.class.getName());
	
	private static Logger errorLogger = Logger.getLogger("Error." + ConnectionPool.class.getName());
	
	private static String className = ConnectionPool.class.getName();
	
	// ���ӳش�С
	private Vector<Connection> pool;
	
	// ·������
	private String ACTIONPATH = "parameter.properties";
	private Properties prop = new Properties();
	
	/* ���ݿ⹫������ */
	private Connection conn;
	private String dbUrl;
	private String dbUser;
	private String dbPwd;
	private String driverClassName = "com.mysql.jdbc.Driver";
	
	private int poolSize = 10;
	private static ConnectionPool instance = null;
	
	// ���ɱ�ʵ��
	private ConnectionPool() {		
		String methodName = "ConenctionPool";
		
		if (ConnectionPool.debugLogger.isDebugEnabled()) {
			ConnectionPool.debugLogger.debug("MethodName:" + methodName + " ClassName:" + className);
		}
		
		pool = new Vector<Connection>(poolSize);
		
		try {
			String path = ConnectionPool.class.getClassLoader().getResource("").getPath();
			File file = new File(path + ACTIONPATH);
			FileInputStream fis = new FileInputStream(file);
			
			prop.load(fis);
			dbUrl = prop.getProperty("mysql_url");
			dbUser = prop.getProperty("mysql_user");
			dbPwd = prop.getProperty("mysql_password");			
			for (int i = 0; i < poolSize; i++) {
				try {					
					Class.forName(driverClassName);					
					conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
					pool.add(conn);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (ConnectionPool.errorLogger.isDebugEnabled()) {
				ConnectionPool.errorLogger.error("error Message:" + e.getMessage() + ", below method:");
				ConnectionPool.errorLogger.error("MethodName:" + methodName + " ClassName:" + className);
			}
		}
		
	}
	
	public static ConnectionPool getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}
	
	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
	}
	
	/* �������ӵ����ӳ� */
	public synchronized void release(Connection conn) {
		pool.add(conn);
	}
	
	/* �������ӳ��е�һ�����ݿ����� */
	public synchronized Connection getConnection() {
		if (pool.size() > 0) {
			Connection conn = pool.get(0);
			pool.remove(conn);
			return conn;
		} else {
			return null;
		}
	}
	
	public int getSize() {
		return this.pool.size();
	}
}
