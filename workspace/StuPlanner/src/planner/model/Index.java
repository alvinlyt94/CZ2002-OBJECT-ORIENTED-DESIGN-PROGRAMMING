package planner.model;

public class Index {
	
	private int indexNumber;
	private String courseCode;
	private String tutorialGroup;
	private int vacancy;
	private int waitingList;
	
	/**
	 * Boolean attributes to indicate whether or not
	 * the index has tutorial/ lecture/ lab
	 */
	
	public Index (String courseCode, int indexNumber, String tutorialGroup, int vacancy, int waitingList){
		this.courseCode = courseCode;
		this.indexNumber = indexNumber;
		this.tutorialGroup = tutorialGroup;
		this.vacancy = vacancy;
		this.waitingList = waitingList;
	}
	//Course Code
	public String getCourseCode()
	{
		return courseCode;
	}
	public void setCourseCode(String courseCode)
	{
		this.courseCode = courseCode;
	}
	//Index Number
	public int getIndexNumber()
	{
		return indexNumber;
	}
	public void setIndexNumber(int indexNumber)
	{
		this.indexNumber = indexNumber;
	}
	// Tutorial Group
	public String getTutorialGroup()
	{
		return tutorialGroup;
	}
	public void setTutorialGroup(String tutorialGroup)
	{
		this.tutorialGroup = tutorialGroup;
	}
	//Vacancy
	public int getVacancy()
	{
		return vacancy;
	}
	public void setVacancy(int vacancy)
	{
		this.vacancy = vacancy;
	}
	//Waiting List
	public int getWaitingList()
	{
		return waitingList;
	}
	public void setWaitingList(int waitingList)
	{
		this.waitingList = waitingList;
	}
	
	public boolean equals(int o) {
		return getIndexNumber() == o;
	}

}