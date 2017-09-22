package planner.model;

public class StudentCourse {

	private String userName;
	
	/**
	 * The course code unique to each module
	 */
	private String courseCode;
	
	/**
	 * The list of indexes for the course that the student can register to
	 */
	private int indexNumber;
	
	private String registerStatus;
	
	public StudentCourse(String userName, String courseCode, int indexNumber, String registerStatus) {
		this.userName = userName;
		this.courseCode = courseCode;
		this.indexNumber = indexNumber;
		this.registerStatus = registerStatus;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public int getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(int indexNumber) {
		this.indexNumber = indexNumber;
	}
	
	public String getRegisterStatus() {
		return registerStatus;
	}
	
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
}