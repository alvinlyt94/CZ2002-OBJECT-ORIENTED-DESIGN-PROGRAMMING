package planner.UI;

import java.io.*;
import java.text.*;
import java.util.*;

import planner.db.IndexesData;
import planner.db.LessonData;
import planner.db.StudentCourseData;
import planner.manager.CalendarMgr;
import planner.manager.DataListMgr;
import planner.manager.IndexMgr;
import planner.manager.PrintMgr;
import planner.manager.StaffCourseMgr;
import planner.model.Course;
import planner.model.Index;
import planner.model.Lesson;
import planner.model.StudentCourse;

public class CourseUI {
	
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Show a UI that prompts Staff to add/update courses
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void addUpdateCourseUI() throws ParseException, IOException{
		int choice;
		
		CourseWhileLoop:
		while(true){
			System.out.println("***Welcome to Course panel!***");
			System.out.println("Please select an action:");
			System.out.println("(1) Add a new course");
			System.out.println("(2) Update existing course/index");
			System.out.println("(3) Remove a course");
			System.out.println("(4) Print list of courses");
			System.out.println("(5) Add a new index");
			System.out.println("(6) Remove an index");
			System.out.println("(7) Back");

			System.out.print("> ");
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) {
			case 1: // Add a new course
				addNewCourseUI();
				break;
			case 2: // Update existing course
				updateCourseUI();
				break;
			case 3: // Remove a course
				removeCourseUI();
				break;
			case 4: // Print list of courses (extra feature)
				PrintMgr.printCourseList();
				System.out.println();
				break;
			case 5: // Add a new index
				addNewIndexUI();
				System.out.println();
				break;
			case 6: // Remove an Index
				removeIndexUI();
				System.out.println();
				break;
			case 7: // Back
				break CourseWhileLoop;
			default:
				System.out.println("Invalid Input! Please re-enter!");
				System.out.println();
			}
		}
	}
	
	/**
	 * Show a UI to add a new course
	 * done by the Staff
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private static void addNewCourseUI() throws IOException, ParseException{
	 	ArrayList<Course> courseList = DataListMgr.getCourses();
    	
		String courseCode = "";
		boolean flag;

		do {
			flag = false;
			System.out.print("Enter the course's code: ");
			courseCode = sc.nextLine().toUpperCase();
			for (Course c : courseList) {
				if (c.getCourseCode().equals(courseCode)) {
					System.out.println("Course Code already exists! Please re-enter.");
					flag = true;
					break;
				}
			}
		} while (flag);
		
		System.out.print("Enter the course's name: "); String courseName = sc.nextLine();
		
		int academicUnit = 0;
        while(true){
        	try{
        		System.out.print("Enter the number of AUs: "); academicUnit = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Academic Unit must be a number!");
        	}
        }
        System.out.print("Enter the school that offers the course (eg: SCE): "); String school= sc.nextLine();
		System.out.print("Enter the course's type: "); String courseType = sc.nextLine();
		Calendar examDate = CalendarMgr.getValidDateTime("an Exam Date");
	
		Course newCourse = new Course(courseCode, courseName, academicUnit, school, courseType, examDate);
		DataListMgr.writeObject(newCourse);
		
		System.out.println();
		PrintMgr.printCourseList();
		System.out.println();
	}
	
	private static void removeCourseUI() throws IOException, ParseException{
		System.out.print("Enter the course's code:"); String courseCode = sc.nextLine();
		StaffCourseMgr.removeCourse(courseCode.toUpperCase());	
	}
	
	private static void updateCourseUI() throws IOException, ParseException{
		System.out.print("Enter the course's code:"); String courseCode = sc.nextLine().toUpperCase();
		System.out.println();
		System.out.println("PLease select one of the following:");
		System.out.println("===================================");
		System.out.println("1. Update course code");
		System.out.println("2. Update school of the course");
		System.out.println("3. Update index numbers of the course");
		System.out.println("4. Update vacancy of the course");
		boolean invalidInput = false; 
		do{
			int in = sc.nextInt();
			sc.nextLine();
			
			switch(in){
			case 1		:	StaffCourseMgr.updateCourseCode(courseCode);
							break;
			case 2 		:	StaffCourseMgr.updateSchool(courseCode);
							break;
			case 3		:	IndexMgr.updateIndex(courseCode);
							break;
			case 4		:	IndexMgr.updateVacancy(courseCode);
							break;
			default		: 	System.out.println("Invalid Input. Please enter again");
							invalidInput = true; 
			}
		}while(invalidInput);
	}
	
	private static void addNewIndexUI() throws IOException, ParseException{		
		System.out.print("Enter the course's code: "); String courseCode = sc.nextLine();
		System.out.print("Enter the index number: "); int indexNumber = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter the tutorial group: "); String tutorialGroup = sc.nextLine();
		System.out.print("Enter the index vacancy: "); int vacancy = sc.nextInt();
		sc.nextLine();
		
		System.out.print("Enter number of lessons: "); int noOfLesson = sc.nextInt();
		sc.nextLine();
		
		for (int i = 0; i < noOfLesson; i++){
			System.out.println();
			System.out.println("Please enter Lesson " + (i+1));
			System.out.println("----------------------------");
			System.out.print("Enter the lesson type (LAB/TUT/LEC): "); String lessonType = sc.nextLine();
			System.out.print("Enter the lesson day (e.g. MON/TUE/WED): "); String lessonDay = sc.nextLine();
			System.out.print("Enter the lesson time (e.g. 1330-1430): "); String lessonTime = sc.nextLine();
			System.out.print("Enter the lesson venue (e.g. LT2A): "); String lessonVenue = sc.nextLine();
			
			Lesson newLesson = new Lesson(indexNumber, lessonType, lessonDay, lessonTime, lessonVenue);
			DataListMgr.writeObject(newLesson);
		}
		
		Index newIndex = new Index(courseCode, indexNumber, tutorialGroup, vacancy, 0);
		DataListMgr.writeObject(newIndex);
	}
	
	private static void removeIndexUI() throws IOException, ParseException{
		System.out.print("Enter the Course Code: "); String courseCode = sc.nextLine();
		
		PrintMgr.printIndexList(courseCode.toUpperCase());
		
		System.out.println();
		System.out.print("Enter the Index Number you want to remove: "); int indexNumber = sc.nextInt();
		sc.nextLine();
		
		ArrayList<Index> indexList = DataListMgr.getIndexes();
		ArrayList<StudentCourse> studentCourseList = DataListMgr.getStudentCourses();
		ArrayList<Lesson> lessonList = DataListMgr.getLessons();
		
		for(Index i : indexList){
			if (i.getIndexNumber() == indexNumber){
				indexList.remove(i);
				IndexesData.saveIndexes(indexList);
				
				// remove lessons
				int lessonCounter = 0;
				while(lessonCounter != lessonList.size()){
					for(Lesson l : lessonList){
						if(l.getIndexNumber() == i.getIndexNumber()){
							lessonList.remove(l);
							LessonData.saveLessons(lessonList);
							
							lessonCounter = 0;
							break;
						}
						lessonCounter++;
					}
				}
				
				//remove studentCourses
				int studCourseCounter = 0;
				while(studCourseCounter != studentCourseList.size()){
					for(StudentCourse sc : studentCourseList){
						if(sc.getIndexNumber() == indexNumber){
							studentCourseList.remove(sc);
							StudentCourseData.saveStudentCourses(studentCourseList);
							
							studCourseCounter = 0;
							break;
						}
						studCourseCounter++;
					}
				}
				break;
			}
		}
	}
}
