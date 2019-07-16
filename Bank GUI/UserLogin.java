//Creates a new object for logins

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserLogin
{
  //Variables
	private String usern, passw;
	private boolean login=false;
	private double money=0;
  //Constructor when username and password are sent
	public UserLogin(String user, String pass)
	{
		usern = user;
		passw = pass;
		fileCheck();
	}
  //Checking file if it exists
	public void fileCheck()
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
          //Run through each line looking for exact username and password combination
	        String line = scan.nextLine();
	        if(line.contains(usern+":"+passw))
	        {
	        	login=true;
	        	moneyCheck();
	        	break;
	        }
	    }
	}
  //Check the amount of money in the account
	public  void moneyCheck()
	{
		Scanner scan = null;
		try
		{
	    	scan = new Scanner(new File("Transactions.txt"));
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("Error, file not found.");
			System.exit(0);
		}
		//Runs through each line
		while(scan.hasNextLine())
	    {
          //Run through each line of the transaction file looking for the username, then getting the next line
	        String line = scan.nextLine();
	        if(line.equals(usern))
	        	money=Double.parseDouble(scan.nextLine());
	    }
	}
	public boolean returnLogin()
	{
		return login;
	}
	public double getMoney()
	{
		return money;
	}
}
