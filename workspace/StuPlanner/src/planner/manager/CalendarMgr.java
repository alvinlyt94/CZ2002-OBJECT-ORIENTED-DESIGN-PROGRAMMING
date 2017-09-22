package planner.manager;

import java.text.*;
import java.util.*;

public class CalendarMgr {
	
	static Scanner sc = new Scanner(System.in);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public static String calendarToString(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return String.format("%02d/%02d/%4d %02d:%02d", day, month + 1, year, hour, minute);
	}
	
	public static Calendar stringToCalendar(String s) throws ParseException {
		Date date = dateFormat.parse(s);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * Prompt user to give a valid access period
	 * @return the VALID reservation date time
	 */
	public static Calendar getValidDateTime(String mode){
		
		String date = "";

	    Date parsedDate = null;
		boolean validDate = false;		
		Calendar newDate = Calendar.getInstance();
		
		do{
			System.out.print("Enter " + mode + " (dd/MM/yyyy HH:mm): ");
			date  = sc.nextLine();
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		    try {
		    	parsedDate = dateFormat.parse(date);
		    	 
		    } catch (ParseException e) {
		        System.out.println("Input is not in the correct format!");
		        continue;
		    }
		    newDate.setTime(parsedDate);

		    validDate = true;

		} while(!validDate);
				
		return newDate;
	}
	

}
