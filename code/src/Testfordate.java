import java.sql.Date;
import java.text.SimpleDateFormat;

public class Testfordate {

	public static void main(String[] args) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(sDateFormat.format(new java.util.Date()));

		sDateFormat = new SimpleDateFormat("MM");
		int month = Integer.parseInt(sDateFormat.format(new java.util.Date()));

		Date startDate = new Date(year - 1900, month - 1, 14);
		Date endDate;

		if (month == 12)
			endDate = new Date(year - 1900 + 1, 1, 1);
		else
			endDate = new Date(year - 1900, month, 1);

		System.out.println(startDate.toString() + " " + endDate.toString());
	}
}
