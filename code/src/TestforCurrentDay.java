import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class TestforCurrentDay {

	public static void main(String[] args){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = sDateFormat.format(new java.util.Date());
		String start = "2014-12-08";
		String end = "2014-12-31";
		
		
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		int day = cal.getActualMaximum(Calendar.DATE);
		System.out.println(day);
		
		String[] tmp1 = start.split("-");
		String[] tmp2 = end.split("-");
		if(tmp1[1].equals(tmp2[1])){
			int startday = Integer.parseInt(tmp1[2]);
			int endday = Integer.parseInt(tmp2[2]);
			for(int i=startday;i<=endday;i++){
				if(i<10){
					System.out.println(tmp1[0]+"-"+tmp1[1]+"-0"+i);
				}else{
					System.out.println(tmp1[0]+"-"+tmp1[1]+"-"+i);
				}
			}
		}else{
			int startday = Integer.parseInt(tmp1[2]);
			int endday = Integer.parseInt(tmp2[2]);
			for(int i=startday;i<=day;i++){
				if(i<10) System.out.println(tmp1[0]+"-"+tmp1[1]+"-0"+i);
				else System.out.println(tmp1[0]+"-"+tmp1[1]+"-"+i);
			}
			for(int i=1;i<=endday;i++){
				if(i<10) System.out.println(tmp2[0]+"-"+tmp2[1]+"-0"+i);
				else System.out.println(tmp2[0]+"-"+tmp2[1]+"-"+i);
			}
		}
	}
}
