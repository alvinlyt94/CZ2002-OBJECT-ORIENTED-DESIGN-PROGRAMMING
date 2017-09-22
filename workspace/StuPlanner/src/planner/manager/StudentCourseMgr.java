package planner.manager;

import java.io.*;
import java.text.*;
import java.util.*;

import planner.db.StudentCourseData;
import planner.model.Index;
import planner.model.Student;
import planner.model.StudentCourse;

public class StudentCourseMgr {
	/**
	 * Create a new course with the necessary information
	 * @throws ParseException 
	 * @throws IOException 
	 */	
	public static void registerCourse(Student s, int indexNumber) throws IOException, ParseException {
		ArrayList<Index> indexList = DataListMgr.getIndexes();
		for (Index i : indexList){
			if (i.getIndexNumber() == indexNumber){
				int vacancy = i.getVacancy();
				int waitingList = i.getWaitingList();
				String registerStatus = "On Waiting List";
				String courseCode = i.getCourseCode();
				
				if (i.getVacancy() <= 0){
					waitingList++;
				}
				else if (i.getVacancy() > 0){
					vacancy--;
					registerStatus = "Registered";
				}
				
				// Adding
				StudentCourse newStudentCourse = new StudentCourse(s.getUserName(), courseCode, indexNumber, registerStatus);
				DataListMgr.writeObject(newStudentCourse);
			    
				// Update new vacancy & waiting list
				indexList.remove(i); 
			    Index newIndex = new Index(i.getCourseCode(), indexNumber, i.getTutorialGroup(), vacancy, waitingList);
			    DataListMgr.writeObject(newIndex);
				
				System.out.println();
				if (registerStatus.equals("On Waiting List")){
					System.out.println("Due to lack of vacancy, your Index " + indexNumber + " (" + courseCode + ") will be put into waiting list.");
				}
				else if (registerStatus.equals("Registered")){
					System.out.println("Index " + indexNumber + " (" + courseCode + ") has been successfully added!");
				}
				
				return;
			}
		}
	}
	
	public static void removeCourse(Student s, int indexNumber) throws IOException, ParseException{
		ArrayList<StudentCourse> studentCourseList = DataListMgr.getStudentCourses();
		ArrayList<Index> indexList = DataListMgr.getIndexes();
		
		for(StudentCourse course : studentCourseList){
			if (course.getIndexNumber() == indexNumber && course.getUserName().equals(s.getUserName())){
				studentCourseList.remove(course);
				StudentCourseData.saveStudentCourses(studentCourseList);

				System.out.println("Index " + course.getIndexNumber() + " (" + course.getCourseCode() + ") has been removed!");
				
				for (Index i : indexList){
					int vacancy = i.getVacancy();
					int waitingList = i.getWaitingList();
					
					if (course.getRegisterStatus().equals("Registered")){
						vacancy++;
					}
					else if (course.getRegisterStatus().equals("On Waiting List")){
						waitingList--;
					}
					
					if (i.getIndexNumber() == indexNumber){
						// Update new vacancy & waiting list
						indexList.remove(i); 
					    Index newIndex = new Index(i.getCourseCode(), indexNumber, i.getTutorialGroup(), vacancy, waitingList);
					    DataListMgr.writeObject(newIndex);
						
						return;
					}
				}
			}
		}
	}
}