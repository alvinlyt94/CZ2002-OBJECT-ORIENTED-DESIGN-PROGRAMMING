package planner.manager;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import planner.model.Index;
import planner.model.Student;
import planner.model.StudentCourse;

public class NotificationMgr {
	
	public static void main (String[] args)
	{
		sendEmail(null, null);
	}

	/**
	 * Send both email and SMS for wait list notification
	 * @param studentToNotify
	 */
	public static void sendBoth (Student studentToNotify, String courseCode)
	{
		sendEmail(studentToNotify, courseCode);
	    sendSMS(studentToNotify);
	}
	
	/**
	 * Send email for wait list notification
	 * @param studentToNotify
	 */
	public static void sendEmail(Student studentToNotify, String courseCode)
	{
		//String email = studentToNotify.getEmail();
		String email = "bliu008@e.ntu.edu.sg";
		
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				//email and password of smtp server
				return new PasswordAuthentication("cz2002.fsp2.group5@gmail.com","cz2002@fsp2group5");
			}
		};

	    Session session = Session.getDefaultInstance(props, auth);
	    
	    try {
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom("cz2002.fsp2.group5@gmail.com");
	        msg.setRecipients(Message.RecipientType.TO,email);
	        msg.setSubject("Waitlist Notification");
	        //msg.setText("You have been registered to " + courseCode);
	        msg.setText("You have been registered to 777");
	        Transport.send(msg);
	    } catch (MessagingException mex) {
	        System.out.println("send failed, exception: " + mex);
	    }
	}
	
	/**
	 * Send SMS for wait list notification
	 * @param studentToNotify
	 */
	public static void sendSMS(Student studentToNotify)
	{
		int mobileNo = studentToNotify.getMobileNo();
		System.out.println("A SMS is sent to " + mobileNo);
	}
	
	public static void sendAlertWaitlist(int indexNumber) throws IOException, ParseException{
		ArrayList<Index> indexList = DataListMgr.getIndexes();
		ArrayList<StudentCourse> studentCourseList = DataListMgr.getStudentCourses();
		ArrayList<Student> studentList = DataListMgr.getStudents();
		
		for(Index i: indexList){
			if (i.getVacancy() > 0 && i.getWaitingList() > 0){
				for(StudentCourse sc : studentCourseList){
					if ((sc.getIndexNumber() == indexNumber) && sc.getRegisterStatus().equals("On Waiting List")){
						for (Student s : studentList){
							if (s.getUserName().equals(sc.getUserName())){
								int vacancy = i.getVacancy();
								int waitingList = i.getWaitingList();
								
								if (vacancy > 0){
									// save to studentCourses.txt
									studentCourseList.remove(sc);
									StudentCourse newSc = new StudentCourse(sc.getUserName(), sc.getCourseCode(), indexNumber, "Registered");
									DataListMgr.writeObject(newSc);
									
									// decrement vacancy and waitinglist by 1
									vacancy--;
									waitingList--;
									
									// save to indexes.txt
									indexList.remove(i);
									Index newIndex = new Index(i.getCourseCode(), indexNumber, i.getTutorialGroup(), vacancy, waitingList);
									DataListMgr.writeObject(newIndex);

									// Set new updated vacancy and waitinglist
									i.setVacancy(vacancy);
									i.setWaitingList(waitingList);
									
									if (s.getNotiMode() == 1){
										// Sending Fake SMS
										sendSMS(s);
									}
									else if (s.getNotiMode() == 2){
										// Sending Actual Email
										sendEmail(s, i.getCourseCode());
									}
									else if (s.getNotiMode() == 3){
										sendBoth(s, i.getCourseCode());
									}									
								}
								else{
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}