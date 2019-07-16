//Creates a new object for transactions: deposit, withdraw, transfer

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Transaction
{
	String username="",transaction="",reciever="";
	double amount=0,money=0,newAmount=0;
	boolean success=true;
	public Transaction(String user, double amt, String trans, String rec)
	{
		username=user;
		amount=amt;
		transaction=trans;
		reciever=rec;
		moneyCheck();
		transCheck();
	}
	//Check the type of transaction
	private void transCheck()
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
		//If transaction is a deposit...
		if(transaction.equals("deposit"))
		{
			outputStream.println("DEPOSIT");
			outputStream.println(amount);
			outputStream.println(username);
			newAmount=money+amount;
			outputStream.println(newAmount);
		}
		//If transaction is a withdrawl...
		if(transaction.equals("withdraw"))
		{
			if(amount>money)
				success=false;
			else
			{
				outputStream.println("WITHDRAW");
				outputStream.println(amount);
				outputStream.println(username);
				newAmount=(money-amount);
				outputStream.println(newAmount);
			}
		}
		//If transaction is is a transfer...
		if(transaction.equals("transfer"))
		{
			outputStream.println("TRANSFER: " +username+" --$"+amount+"--> "+reciever);
			outputStream.println(reciever);
			outputStream.println(amount);
			outputStream.println(username);
			newAmount=money-amount;
			outputStream.println(newAmount);
		}
		outputStream.close();
	}
	//Check the money amount
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
	        String line = scan.nextLine();
	        if(line.equals(username))
	        	money=Double.parseDouble(scan.nextLine());
	    }
	}
	public double getMoney()
	{
		return newAmount;
	}
	public boolean getSuccess()
	{
		return success;
	}
}
