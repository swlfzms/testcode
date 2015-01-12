
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservationDao {

	private Connection con;
	private PreparedStatement stmt;
	private ResultSet rs;
	private String dbUrl;
	private String dbUser;
	private String dbPwd;
	private Map<String, ArrayList<String[]>> data;

	public ReservationDao() throws URISyntaxException, IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, SQLException {
		// TODO Auto-generated constructor stub

		Class jdbcDriver = Class.forName("com.mysql.jdbc.Driver");
		java.sql.DriverManager
				.registerDriver((Driver) jdbcDriver.newInstance());
		con = java.sql.DriverManager.getConnection(
				"jdbc:mysql://localhost:3307/trainingroom", "root", "123");
		data = new HashMap<String, ArrayList<String[]>>();
	}

	public Map<String, ArrayList<String[]>> getReservationData()
			throws SQLException {
		// TODO Auto-generated method stub

		// to get the room_name
		String sql = "select room_name from room";
		stmt = con.prepareStatement(sql);
		rs = stmt.executeQuery(sql);
		ArrayList<String> roomnamelist = new ArrayList<String>();

		while (rs.next()) {
			roomnamelist.add(rs.getString("room_name"));
		}

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy");
		String year = sDateFormat.format(new java.util.Date());
		sDateFormat = new SimpleDateFormat(
				"MM");
		String month = sDateFormat.format(new java.util.Date());		
		String startDate = year+"-"+month+"-14";
		String endDate="";
		if(month=="12") endDate = ""+(Integer.parseInt(year)+1)+"-01-01";
		else endDate=year+"-"+(Integer.parseInt(month)+1)+"-01";
		
		
		// to get the reservation data while reservation was submit in this month:14 to next month:01;
		sql = "select * from reservation where book_date>'"+startDate
				+"' and book_date<'"+endDate+"' and ticket_status=1 order by book_date";
		stmt = con.prepareStatement(sql);	
		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String[] temp;
			String plan_date = rs.getDate("plan_date").toString();
			String room_name = rs.getString("room_name");
			String team_name = rs.getString("team_name");
			String user_name = rs.getString("user_name");
			String user_telline = "" + rs.getInt("user_telline");
			String reason = rs.getString("reason");
			temp = new String[] { plan_date, room_name, team_name, user_name,
					user_telline, reason };			
			
			if (data.containsKey(plan_date)) {
				ArrayList<String[]> infomation = data.get(plan_date);
				infomation.add(temp);
				data.put(plan_date, infomation);
			} else {
				ArrayList<String[]> infomation = new ArrayList<String[]>();
				infomation.add(temp);
				data.put(plan_date, infomation);
			}
		}
		return data;
	}

}
