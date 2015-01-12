import java.text.SimpleDateFormat;
import java.util.Date;

public class TestforJson {

	public static void main(String[] args) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
		String year = sDateFormat.format(new java.util.Date());
		
		sDateFormat = new SimpleDateFormat("MM");
		String month = sDateFormat.format(new java.util.Date());
		String startDate = year + "-" + month + "-15";
		String endDate = "";

		System.out.println(year + " " + month);

		if (month.equals("12"))
			endDate = "" + (Integer.parseInt(year) + 1) + "-01-01";
		else
			endDate = year + "-" + (Integer.parseInt(month) + 1) + "-31";

		System.out.println(startDate + " " + endDate);

			
	}
}
