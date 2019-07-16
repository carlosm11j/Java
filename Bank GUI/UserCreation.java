//Creates new object for a new user and writes to file

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;

public class UserCreation
{
	private String usern, passw;
	private double balance=0;
	boolean creation = true;
	public UserCreation(String user, String pass)
	{
		usern = user;
		passw = pass;
		check();
	}
	//Check if the username already exists...
	public void check()
	{
	    Scanner scan = null;
		try
		{
	    	scan = new Scanner(new File("Users.txt"));
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("Error, file not found.");
			System.exit(0);
		}
		//Runs through each line
		while(scan.hasNextLine())
	    {
			String line = scan.nextLine();
	        if(line.startsWith(usern))
	        {
	        	creation=false;
	        	break;
	        }
	    }
		if(creation)
			fileWrite();
	}
	//Write to the file
	public void fileWrite()
	{
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream("Users.txt",true));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error, file not found");
			System.exit(0);
		}
		outputStream.println(usern+":"+passw);
		outputStream.close();
		transWrite();
	}
	//Create a log in the transaction file to set balance to 0
	public void transWrite()
	{
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream("Transactions.txt",true));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error, file not found");
			System.exit(0);
		}
		outputStream.println(usern);
		outputStream.println("0");
		outputStream.close();
	}
	public boolean able()
	{
		return creation;
	}
	public double getBalance()
	{
		return balance;
	}
}
