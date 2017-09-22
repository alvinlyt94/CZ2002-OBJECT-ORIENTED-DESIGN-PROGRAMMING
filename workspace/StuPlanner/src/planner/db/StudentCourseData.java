package planner.db;

import java.io.*;
import java.text.*;
import java.util.*;

import planner.manager.IO;
import planner.model.StudentCourse;

public class StudentCourseData {

	public static final String SEPARATOR = "|";

	public static ArrayList <StudentCourse> studentCourseList = new ArrayList<StudentCourse>() ;
	
    /** Initialise the courses before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<StudentCourse> initStudentCourses() throws IOException, ParseException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) IO.read("src/planner/db/studentCourses.txt");
			
		if (stringArray.size() == 0){
			return new ArrayList<StudentCourse>();
		}
        for (int i = 0 ; i < stringArray.size() ; i++) {
        	
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens
				String  userName = tokenizer.nextToken().trim();	
				String  courseCode = tokenizer.nextToken().trim();	
				int indexNumber = Integer.parseInt(tokenizer.nextToken().trim());
				String registerStatus = tokenizer.nextToken().trim();
				
				// create Course object from file data
				StudentCourse course = new StudentCourse(userName, courseCode, indexNumber, registerStatus);
				// add to Courses list 
				studentCourseList.add(course) ;
		}
		return studentCourseList ;
	}

	/** Save the courses that has been added during the session
	 * @param CourseToUpdate
	 * @throws IOException
	 */
	public static void saveStudentCourses(ArrayList<StudentCourse> CourseToUpdate) throws IOException {
		ArrayList <String> courseList = new ArrayList<String>() ;// to store Courses data

        for (int i = 0 ; i < CourseToUpdate.size() ; i++) {
				StudentCourse course = (StudentCourse) CourseToUpdate.get(i);
				StringBuilder stringBuild =  new StringBuilder() ;
				stringBuild.append(course.getUserName().trim());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getCourseCode().trim());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getIndexNumber());
				stringBuild.append(SEPARATOR);
				stringBuild.append(course.getRegisterStatus());
				
				courseList.add(stringBuild.toString()) ;
			}
			IO.write("src/planner/db/studentCourses.txt", courseList);
	}
}
