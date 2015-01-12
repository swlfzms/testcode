import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Testforreservation {

	public static void main(String[] args) throws Exception {
		ReservationDao ap = new ReservationDao();
		Map<String, ArrayList<String[]>> data = ap.getReservationData();
		Set<String> key = data.keySet();
		for (String str : key) {
			ArrayList<String[]> info = data.get(str);
			for(int i=0;i<info.size();i++){
				String[] name = info.get(i);
				for(String s:name)
					System.out.println(s);
				System.out.println();
			}
			System.out.println("**************");
		}
	}
}
