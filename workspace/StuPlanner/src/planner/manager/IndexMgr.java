package planner.manager;

import java.io.*;
import java.text.*;
import java.util.*;

import planner.model.Index;
import planner.model.Lesson;
import planner.model.StudentCourse;

public class IndexMgr {
	private static Scanner sc = new Scanner(System.in);
	
	public static void updateVacancy(String courseCode) throws IOException, ParseException{
		ArrayList<Index> indexList = DataListMgr.getIndexes();
		PrintMgr.printIndexList(courseCode);
		System.out.println();
		System.out.print("Please select one of the index number to modify vancancy amount: "); int indexNumber = sc.nextInt();
		sc.nextLine();
		
		for (Index i : indexList)
			{
				if(i.equals(indexNumber)){
					System.out.println("Index Name\t Vacancy");
					System.out.println("----------------------------------------");
					System.out.print(i.getIndexNumber() + "\t\t ");
					System.out.println(i.getVacancy());
					System.out.println();
					System.out.print("Please enter new amount of Vacancy: "); int vc = sc.nextInt();
					sc.nextLine();
					indexList.remove(i);
					
					Index newIndex = new Index(courseCode, i.getIndexNumber(), i.getTutorialGroup(), vc, i.getWaitingList());
					DataListMgr.writeObject(newIndex);

					NotificationMgr.sendAlertWaitlist(i.getIndexNumber());
					break;

				}
			}
		}
	
	public static void updateIndex(String courseCode) throws IOException, ParseException{
		
		ArrayList<Index> indexList = DataListMgr.getIndexes();
		ArrayList<Lesson> lessonList = DataListMgr.getLessons();
		
		PrintMgr.printIndexList(courseCode);
		System.out.println();
		System.out.print("Please enter index number that you want to modify: "); int indexNumber = sc.nextInt();
		sc.nextLine();
		System.out.print("Please enter new index number: "); int newIndexNumber = sc.nextInt();
		sc.nextLine();
		
		for(Index i : indexList){
			if(i.equals(indexNumber)){
				indexList.remove(i);
				Index newIndex = new Index(courseCode, newIndexNumber, i.getTutorialGroup(),
						i.getVacancy(), i.getWaitingList());
				DataListMgr.writeObject(newIndex);
				
				//updating studentCourses info data
				ArrayList<StudentCourse> studentCourseList = DataListMgr.getStudentCourses();
				for(StudentCourse sc : studentCourseList){
					if(sc.getIndexNumber() == indexNumber){
						studentCourseList.remove(sc);
						StudentCourse newSc = new StudentCourse(sc.getUserName(), sc.getCourseCode(), newIndexNumber, sc.getRegisterStatus());
						DataListMgr.writeObject(newSc);
					}
				}
				
				int counter = 0;
				while(counter != lessonList.size()){
					for(Lesson l : lessonList){
						if(l.getIndexNumber() == indexNumber){
							lessonList.remove(l);
							
							Lesson newLesson = new Lesson(newIndexNumber, l.getLessonType(), l.getLessonDay(),
									l.getLessonTime(), l.getLessonVenue());
							DataListMgr.writeObject(newLesson);
							
							counter = 0;
							break;
						}
						counter++;
					}
				}
				}
			}
		}
	}