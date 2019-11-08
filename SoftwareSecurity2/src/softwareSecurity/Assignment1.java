package softwareSecurity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


public class Assignment1 {
	public static LinkedList<String> userNameList = new LinkedList<String>();//LinkedList to store list of user name
	public static LinkedList<String> passwordList = new LinkedList<String>();//LinkedList to store list of password
	public static String FILE_NAME = "accounts.txt";//account text file name
	
		
public static void main(String[] args) {
		Scanner userInput = null;// User Input Scanner
		
		try {
			userInput = new Scanner(System.in);// User Input Scanner
			//iterating for infinite time, it will terminated one user enters 3
			while (true) {
				System.out.println("Pleasee Enter 1 to Create new Account; 2 to Check Existing Account; 3 to Quite");
				int userDecission = userInput.nextInt();// Getting input for user decision
				//If user decision is 1, the account creation started,for 2, existing account checking started and 3 to quite
				if (userDecission == 1) {
					System.out.println("Account Creation Started!");
					System.out.println("Enter User Name:");
					String userName = userInput.next();// Getting input for user name to create
					System.out.println("Enter Password:");
					String password = userInput.next();// Getting input for user password to create
					writeAccountFile (userName,password);//Writing account info into file
				}else if (userDecission == 2) {
					System.out.println("Existing Account Checking!");
					readAccountFile();//Reading account info from file and updating LinkedList for both user name and password
					System.out.println("Enter User Name:");
					String userName = userInput.next();// Getting input for user names to check
					//checking weather user name entered is not blank and already exits
					if (userName!= null && !userName.equalsIgnoreCase("") && isUserNameExists(userName)) {
						System.out.println("That account exists.");
						System.out.println("Enter Password:");
						String password = userInput.next();// Getting input for user password to check
						//checking weather password entered is not blank and is matching with existing user name
						if (password!= null && !password.equalsIgnoreCase("") && isCorrectPassword(password)) {
							System.out.println("Password is matching.");
						}else {
							System.out.println("Password is not matching.");
						}
					}else {
						System.out.println("That account does not exist.");
					}
				}else if (userDecission == 3) {
					System.out.println("Goodbye.");
					System.exit(0);//Terminating the while loop and program
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			} finally {
				try {
					if (userInput!= null) {
					userInput.close();//closing scanner
					}
				}catch (Exception e) {
					e.printStackTrace();
					}
				}
			}
			//This method is to check weather user name is exits in LinkedList
			public static boolean isUserNameExists(String userName) {
				boolean isExistsFlag = false;//user name exits flag, if exits set as true else false
				if (userNameList!= null) {
					//iterating user name LinkedList
					for (int i=0;i<userNameList.size();i++) {
						//Check each user name is matching with input user name
						if (userNameList.get(i).equals(userName)) {
							isExistsFlag = true;//If it is matched flag set as true
							break;//terminating the loop as already matched
						}
					}
				}
				//returning flag
				return isExistsFlag;
			}
		//This method is to check weather password is exits in LinkedList
			public static boolean isCorrectPassword(String password) {
				boolean isCorrectFlag = false;//password checking flag, if matching set as true else false
				if (passwordList!= null) {
					//iterating password LinkedList
					for (int i=0;i<passwordList.size();i++) {
						//Check each password is matching with input password
						if (passwordList.get(i).equals(password)) {
							isCorrectFlag = true;//If it is matched flag set as true
							break;//terminating the loop as already matched
						}
					}
				}
				//returning flag
				return isCorrectFlag;
			}
		//This method is to read account text file and update user name and password LinkedList
			public static void readAccountFile() {
				Scanner inStream = null;
				try {
					userNameList.clear();//Initially clearing LinkedList for fresh update
					passwordList.clear();//Initially clearing LinkedList for fresh update
					File fileInput = new File(FILE_NAME); //Creating new file
					inStream = new Scanner(fileInput); //Creating new Scanner in stream
					// Iterating each line of file
					while (inStream.hasNext()) {
						String line = inStream.nextLine();//getting new line from text file
						String[] lineArray = line.trim().split("\\s+");//Splitting each line by space to get string array
						String userName = lineArray[0];//Getting user name from first index of string array
						userNameList.add(userName);//Adding user name into LinkedList
						String password = lineArray[1];//Getting password from second index of string array
						passwordList.add(password);//Adding password into LinkedList in same index
					}
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						if (inStream != null) {
						inStream.close();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
		}
			public static void writeAccountFile(String userName, String password) {
				FileWriter fw = null;
				BufferedWriter bw = null;
				try {
					File file = new File(FILE_NAME);//Getting file object based on file name
					//if not exits, creating new one
					if (!file.exists()) {
						file.createNewFile();//creating new file
					}
					fw = new FileWriter(FILE_NAME,true);// creating File Writer to write and append text file, true argument is to append new line
					bw = new BufferedWriter(fw);// creating Buffered Writer to write text file
					bw.write(userName+" "+password);//adding new line of user name and password separated by space
					bw.newLine();//going to next line
					bw.flush();// Flushing Buffered Writer
					fw.flush();// Flushing File Writer
				}catch (IOException ioe) {
					ioe.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						if (fw != null) {
						fw.close();// closing File Writer
						}
						if (bw != null) {
						bw.close();// closing Buffered Writer
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
}
