package planner.main;

import java.io.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import planner.UI.StaffUI;
import planner.UI.StudentUI;
import planner.db.AccountData;
import planner.db.CourseData;
import planner.db.IndexesData;
import planner.db.LessonData;
import planner.db.StudentCourseData;
import planner.db.StudentData;
import planner.manager.CalendarMgr;
import planner.manager.DataListMgr;
import planner.manager.UserValidationMgr;
import planner.model.Account;
import planner.model.Student;

public class MainApp {
	
	public static void main(String[] args) throws ParseException, IOException {
		
		StudentData.initStudents();
		CourseData.initCourses();
		IndexesData.initIndexes();
		LessonData.initLessons();
		StudentCourseData.initStudentCourses();
		AccountData.initAccounts();
		
		do {
			String username;
			String password;
			String accountType = "";
			int choice = 0;
			Account loggedInAcc;

            @SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			
			mainLoop: 
			while (true) {
				domainLoop:
                
				while(true){
					System.out.println("********Select Domain********");
					System.out.println("(1) Student");
					System.out.println("(2) Staff");
					System.out.print("> ");
					try {
						choice = Integer.parseInt(sc.nextLine());
						switch (choice) {
						case 1:
							accountType = "Student";
							break domainLoop;
						case 2:
							accountType = "Staff";
							break domainLoop;
						default:
							System.out.println("Invalid Input! Please re-enter!");
							System.out.println();
						}
					} catch (Exception e) {
						System.out.println("Invalid Input! Please re-enter!");
						System.out.println();
					}
				}

				System.out.print("Username: ");
				username = sc.nextLine();
				System.out.print("Password: ");
				password = sc.nextLine();

				loggedInAcc = UserValidationMgr.compareUserPass(username, password, accountType);
				if (!(loggedInAcc == null)) {
					break mainLoop;
				}

				System.out.println();
				System.out.println("Incorrect username or password! Please re-enter!");
				System.out.println();
			}

			System.out.println();

			if (loggedInAcc.getAccountType().equals("Student")) {
				ArrayList<Student> studentList = DataListMgr.getStudents();
				for(Student loggedInStudent: studentList){
					if (loggedInStudent.getUserName().equals(loggedInAcc.getUsername())){
						
						Calendar c = Calendar.getInstance();
					    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					    LocalDateTime now = LocalDateTime.now();
					    c = CalendarMgr.stringToCalendar(dtf.format(now));
					    
					    int compareAccessStart = c.compareTo(loggedInStudent.getAccessStart());
					    int compareAccessEnd = loggedInStudent.getAccessEnd().compareTo(c);
					   
					    if (compareAccessStart*compareAccessEnd < 0){
					    	System.out.println("Unable to login! Your access time is from " 
					    			+ CalendarMgr.calendarToString(loggedInStudent.getAccessStart()) + " to "
					    			+ CalendarMgr.calendarToString(loggedInStudent.getAccessEnd()));
					    	System.out.println();
					    }
					    else{
							System.out.println("Hello, " + loggedInAcc.getUsername() + "!");
							StudentUI.showStudentOption(loggedInStudent);
					    }
					}
				}
			} else if (loggedInAcc.getAccountType().equals("Staff")) {
				StaffUI.showStaffOption();
			}
		} while (true);
		
	}
}
