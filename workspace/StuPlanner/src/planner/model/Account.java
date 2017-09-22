package planner.model;
import java.io.*;

public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username ;
	private String password ;
	private String accountType ;
	private String salt;

	public Account(String username, String password, String accountType, String salt)  {
		this.username = username ;
		this.password = password ;
		this.accountType = accountType ;
		this.salt = salt;
	}
	
	public String getUsername() { 
		return username; 
	}
	
	public String getPassword() { 
		return password; 
	}
	
	public String getAccountType() { 
		return accountType; 
	}
	
	public String getSalt() { 
		return salt; 
	}

	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account acc = (Account)obj;
			return (getUsername().equals(acc.getUsername()));
		}
		return false;
	}
}