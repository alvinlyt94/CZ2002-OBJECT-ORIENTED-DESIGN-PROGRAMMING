package planner.manager;

import java.io.*;
import java.text.*;
import java.util.*;

import planner.model.Course;
import planner.model.Index;
import planner.model.Lesson;
import planner.model.Student;
import planner.model.StudentCourse;

/**
 * Manages the printing of the student list and staff list
 * @version 1.0
 * @since 2017-03-21
 */

public class PrintMgr {

	/**
	 * Print out all the student names on the Student List
	 * from index 0 to the end of the array
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void printStudentList(int indexNumber) throws IOException, ParseException{
		ArrayList<Student> studentList = DataListMgr.getStudents();
		ArrayList<StudentCourse> studentCourseList = DataListMgr.getStudentCourses();
		
		System.out.println();
		System.out.println("Index Number: " + indexNumber);
		System.out.println("Username\t Matric Number\t Full Name");
		System.out.println("-----------------------------------------------------------------");
		for (StudentCourse sc : studentCourseList){
			if(sc.getIndexNumber() == indexNumber){
				for (Student s: studentList){
					if(sc.getUserName().equals(s.getUserName())){
						System.out.print(s.getUserName() + "\t\t ");
						System.out.print(s.getMatricNumber() + "\t ");
						System.out.print(s.getFirstName() + " " + s.getLastName());
						System.out.println();
					}
				}
			}
		}
	}
	
	public static void printStudentList(String courseCode) throws IOException, ParseException{
		ArrayList<Student> studentList = DataListMgr.getStudents();
		ArrayList<StudentCourse> studentCourseList = DataListMgr.getStudentCourses();
		
		System.out.println();
		System.out.println("Course Code: " + courseCode);
		System.out.println("Username\t Matric Number\t Full Name");
		System.out.println("-----------------------------------------------------------------");
		for (StudentCourse sc : studentCourseList){
			if(sc.getCourseCode().equals(courseCode)){
				for (Student s: studentList){
					if(sc.getUserName().equals(s.getUserName())){
						System.out.print(s.getUserName() + "\t\t ");
						System.out.print(s.getMatricNumber() + "\t");
						System.out.print(s.getFirstName() + " " + s.getLastName());
						System.out.println();
					}
				}
			}
		}
	}
	
	public static void printStudentList() throws IOException, ParseException{
		ArrayList<Student> studentList = DataListMgr.getStudents();
		
		System.out.println();
		System.out.println("Matric Number\tFull Name");
		System.out.println("---------------------------------------------------");
		for (Student s: studentList){
			System.out.print(s.getMatricNumber() + "\t");
			System.out.print(s.getFirstName() + " " + s.getLastName());
			System.out.println();
		}
	}
	
	public static void printIndexInfo(int i) throws IOException, ParseException{
		ArrayList<Index> indexList = DataListMgr.getIndexes();
		ArrayList<Lesson> lessonList = DataListMgr.getLessons();
		ArrayList<Course> courseList = DataListMgr.getCourses();
		
		for (Index in : indexList) {
			if (in.equals(i)) {
				System.out.println("Index Number: " + i);
				for (Course c : courseList) {
					if (c.getCourseCode().equals(in.getCourseCode())) {
						System.out.println("Course: " + in.getCourseCode());
						System.out.println("Course Type: " + c.getCourseType());
					}
				}
				
				System.out.println();
				System.out.println("Type\t Group\t\t Day\t\t Time\t\t Venue");
				System.out.println("-----------------------------------------------------------------");
				for (Lesson le : lessonList)
				{
					if(le.equals(i)){
						System.out.print(le.getLessonType() + "\t ");
						System.out.print(in.getTutorialGroup() + "\t\t ");
						System.out.print(le.getLessonDay() + "\t\t ");
						System.out.print(le.getLessonTime() + "\t ");
						System.out.print(le.getLessonVenue() + "\t ");
						System.out.println();
					}
				}
			}
		}
	}
	
	/**
	 * Print out all the courses on the Course List
	 * from index 0 to the end of the array
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void printCourseList() throws IOException, ParseException{
		ArrayList<Course> courses = DataListMgr.getCourses();
		if(courses.size() <= 0){
			System.out.println("There are no course in the list.");
			return;
		}
		else{
			System.out.println("Course Code\t Course Name");
			System.out.println("----------------------------------------");
			for (Course course : courses)
			{
				System.out.print(course.getCourseCode() + "\t\t ");
				System.out.print(course.getCourseName());
				System.out.println();
			}
		}
	}
	
	/**
	 * Print out all the indexes of a course
	 * from index 0 to the end of the array
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void printIndexList(String courseCode) throws IOException, ParseException{
		ArrayList<Index> indexList = DataListMgr.getIndexes();
		if(indexList.size() <= 0){
			System.out.println("There is no index for this course.");
			return;
		}
		else{
			System.out.println();
			System.out.println("Course Code\t Index Number");
			System.out.println("----------------------------------------");
			for(Index index : indexList) {
				if (index.getCourseCode().equals(courseCode)){
					System.out.print(index.getCourseCode() + "\t\t ");
					System.out.print(index.getIndexNumber());
					System.out.println();
				}
			}
		}
	}
	
	public static void printRegisteredCourses(Student s) throws IOException, ParseException{
		ArrayList<StudentCourse> studentCourseList = DataListMgr.getStudentCourses();
		ArrayList<Course> courseList = DataListMgr.getCourses();
		
		if(studentCourseList.size() <= 0){
			System.out.println("There is no registered course.");
			return;
		}
		else{
			int totalAURegistered = 0;
			System.out.println();
			System.out.println("Course Code\t AU\t Course Type\t Index Number\t Status");
			System.out.println("-------------------------------------------------------------------");
			for(StudentCourse sc  : studentCourseList) {
				if (sc.getUserName().equals(s.getUserName())){
					for(Course c : courseList){
						if (c.getCourseCode().equals(sc.getCourseCode())){
							System.out.print(sc.getCourseCode() + "\t\t ");
							System.out.print(c.getAU() + "\t ");
							System.out.print(c.getCourseType() + "\t\t ");
							System.out.print(sc.getIndexNumber() + "\t\t ");
							System.out.print(sc.getRegisterStatus());
							System.out.println();
							
							if (sc.getRegisterStatus().equals("Registered")){
								totalAURegistered += c.getAU();
							}
						}
					}
				}
			}			
			System.out.println("Total AU Registered: " + totalAURegistered);
		}
	}
}
