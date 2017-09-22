package planner.model;

import java.util.*;

public class Course {
	/**
	 * The course code unique to each module
	 */
	private String courseCode;
	
	/**
	 * The name of the course
	 */
	private String courseName;
	
	/**
	 * The number of Academic Unit (AU) of the course
	 */
	private int AU;
	
	/**
	 * The school that offers the module
	 */
	private String school;
	
	/**
	 * The list of indexes for the course that the student can register to
	 */
	private String courseType;
	
	/** The exam date of the module
	 */
	Calendar examDate;
	
	public Calendar getExamDate() {
		return examDate;
	}
	

	/** Constructor for Course
	 */
	public Course (String courseCode, String courseName, int au, String school, String courseType, Calendar examDate) {
		this.courseCode			= courseCode;
		this.courseName			= courseName;
		this.AU					= au;
		this.school				= school;
		this.examDate			= examDate;
		this.courseType			= courseType;
	}
	
	
	

	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * Change the course code
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getAU() {
		return AU;
	}

	public void setAU(int aU) {
		AU = aU;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getCourseType() {
		return courseType;
	}
	
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	
	public void setExamDate(Calendar examDate){
		this.examDate = examDate;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Course) {
			Course st = (Course)o;
			return (getCourseCode().equals(st.getCourseCode()));
		}
		return false;
	}

}