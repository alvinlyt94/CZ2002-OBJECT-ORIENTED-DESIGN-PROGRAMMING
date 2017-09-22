package planner.db;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import planner.manager.IO;
import planner.model.Account;

public class AccountData {
	public static final String SEPARATOR = "|";
	
	public static ArrayList<Account> accountList = new ArrayList<Account>();

    /** Initialise the courses before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<Account> initAccounts() throws IOException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) IO.read("src/planner/db/accounts.txt");
		
		
		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter "|"
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			String username = star.nextToken().trim(); // first token
			String password = star.nextToken().trim(); // second token
			String accountType = star.nextToken().trim(); // third token
			String salt = star.nextToken().trim(); // fourth token
			// create Account object from file data
			Account acc = new Account(username, password, accountType, salt);
			// add to Account list
			accountList.add(acc);
		}
		return accountList;
	}

	// save new entry 
	public static void saveAccounts(ArrayList<Account> al) throws IOException {
		ArrayList <String> alw = new ArrayList<String>() ;

		for (int i = 0; i < al.size(); i++) {
			Account acc = (Account) al.get(i);
			StringBuilder st = new StringBuilder();
			st.append(acc.getUsername().trim());
			st.append(SEPARATOR);
			st.append(acc.getPassword().trim());
			st.append(SEPARATOR);
			st.append(acc.getAccountType().trim());
			st.append(SEPARATOR);
			st.append(acc.getSalt().trim());
			alw.add(st.toString());
		}
		IO.write("src/planner/db/accounts.txt", alw);
	}
}