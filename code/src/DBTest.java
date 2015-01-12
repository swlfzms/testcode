import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DBTest {

	public static void main(String[] args) throws Exception{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs= null;
		String dbUrl = "jdbc:mysql://localhost:3307/trainingroom";
		String dbUser = "root";
		String dbPwd = "123";
		Class jdbcDriver = Class.forName("com.mysql.jdbc.Driver");
		
		java.sql.DriverManager
				.registerDriver((Driver) jdbcDriver.newInstance());
		con = java.sql.DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		
		String sql = "SELECT room_name FROM reservation WHERE ticket_status=1 AND plan_date='2014-06-27'";
		ArrayList<String> list = new ArrayList<String>();
		stmt = con.prepareStatement(sql);
		rs = stmt.executeQuery();
		while(rs.next()){
			System.out.println(rs.getString("room_name"));
			list.add(rs.getString("room_name"));
		}
		rs.close();
		stmt.close();
		con.close();
	}
}
