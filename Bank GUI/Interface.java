//Create the User Interface for the program using Java built in GUI capabilities

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


public class Interface extends JFrame implements ActionListener
{
  //Variables
  JPanel homePage = new JPanel(),acctPanel = new JPanel(), depPanel = new JPanel(),tranPanel = new JPanel();
	JButton loginButton, createButton, depButton,withButton,tranButton,logoutButton,transButton;
	JLabel label1,returnLabel,transLabel,moneyLabel;
	NumberFormat fmt = NumberFormat.getCurrencyInstance();
	JTextField usernameBox,depAmt,withAmt,tranAmt,transAmt,transUser;
	JPasswordField passwordBox;
	JTabbedPane tabs = new JTabbedPane();
	ImageIcon image1;
	String username="null",password="null";
	boolean home=true;
	double balance=0;

	public static void main(String[]args)
	{
    //Create new GUI interface
		Interface obj1 = new Interface();
		obj1.setVisible(true);
	}
  //Create GUI Main Interface
	public Interface()
	{
    //Make home screen visible
		super("Bank of Manchester");
		setSize(850, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		switchPage();
	}
  //Switch Pages
	public void switchPage()
	{
    //Switch pages
		if(home)
			createHomePage();
		else
			createAccountPage();
	}
  //Create GUI Account Page
	public void createAccountPage()
	{
		panelSetup();
		//Adds Tabs
		tabs.addTab("Account Summary", acctPanel);
		tabs.addTab("Deposit/Withdraw", depPanel);
		tabs.addTab("Transfer", tranPanel);
		acctPanel.add(new JLabel(username));
		moneyLabel = new JLabel(""+fmt.format(balance));
		acctPanel.add(moneyLabel);
		//Account Panel
		logoutButton = new JButton("Logout");
		acctPanel.add(logoutButton);
		logoutButton.addActionListener(this);
		//Deposit
		depPanel.add(new JLabel("Deposit: "));
		depAmt = new JTextField(5);
		depPanel.add(depAmt);
		depButton = new JButton("Deposit");
		depPanel.add(depButton);
		depButton.addActionListener(this);
		transLabel = new JLabel();
		depPanel.add(transLabel);
		transLabel.setVisible(false);
		//Withdraw
		depPanel.add(new JLabel("Withdraw: "));
		withAmt = new JTextField(5);
		depPanel.add(withAmt);
		withButton = new JButton("Withdraw");
		depPanel.add(withButton);
		withButton.addActionListener(this);
		//Transfer
		tranPanel.add(new JLabel("Transfer: "));
		transButton = new JButton("Transfer");
		transButton.addActionListener(this);
		transAmt=new JTextField("Amount",5);
		tranPanel.add(transAmt);
		transUser=new JTextField("User to Send",15);
		tranPanel.add(transUser);
		tranPanel.add(transButton);
		//Adds Tabs
		add(tabs);
	}
  //Set up GUI Panels
	public void panelSetup()
	{
		add(acctPanel);
		add(depPanel);
		add(tranPanel);
		acctPanel.setLayout(new FlowLayout());
		depPanel.setLayout(new FlowLayout());
		tranPanel.setLayout(new FlowLayout());
	}
  //Create GUI Home Page
	public void createHomePage()
	{
		//Add Panel
		add(homePage);
		homePage.setLayout(null);
		//Image
		/*image1 = new ImageIcon(getClass().getResource("manlogo.png"));
		label1 = new JLabel(image1);
		label1.setBounds(150, 50, 200, 200);
		homePage.add(label1);*/
		//Return Label
		returnLabel = new JLabel("");
		returnLabel.setBounds(330,300,300,15);
		homePage.add(returnLabel);
		//Text Fields
		usernameBox = new JTextField("username");
		usernameBox.setBounds(650,100,150,25);
		homePage.add(usernameBox);
		passwordBox = new JPasswordField("password");
		passwordBox.setBounds(650,145,150,25);
		homePage.add(passwordBox);
		//Login Button
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		loginButton.setBounds(675,180,100,50);
		homePage.add(loginButton);
		//Create Account Button
		createButton = new JButton("New? Create Account");
		createButton.addActionListener(this);
		createButton.setBounds(633,250,175,60);
		homePage.add(createButton);
	}
  //Check for Actions (Button clicks, etc)
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String word = e.getActionCommand();
		returnLabel.setVisible(false);
		collectData();
    //Create New Account
		if(word.equals("New? Create Account"))
		{
      //Create object for new account
			UserCreation acct = new UserCreation(username,password);
      //Check if username is valid
			if(!(acct.able()))
			{
				returnLabel.setVisible(true);
				returnLabel.setText("Error. Username already exists");
			}
			else
				returnLabel.setVisible(false);
		}
    //If user clicks login button...
		else if(word.equals("Login"))
		{
      //Check login
			UserLogin account = new UserLogin(username,password);
			balance=account.getMoney();
      //If login is Successful
			if(account.returnLogin())
			{
        //Display account interface
				home=false;
				homePage.setVisible(false);
				switchPage();
			}
			else
			{
				returnLabel.setVisible(true);
				returnLabel.setText("Error. Wrong Username or Password");
			}
		}
    //If user clicks logout button
		else if(word.equals("Logout"))
		{
			home=true;
			switchPage();
		}
    //If user clicks deposit button...
		else if(word.equals("Deposit"))
		{
      //Get deposit amount
			int amount=Integer.parseInt(depAmt.getText());
      //Create new transaction object with username, password, deposit, and null
			Transaction trans = new Transaction(username,amount,"deposit",null);
      //Set balance
			balance=trans.getMoney();
			transLabel.setVisible(true);
      //If transaction went through
			if(trans.getSuccess())
			{
        //Show new balance
				transLabel.setText("Transaction: Successful");
				moneyLabel.setText(fmt.format(balance));
			}
			else
				transLabel.setText("Transaction: Failed");
			repaint();
		}
    //If user clicks withdraw button...
		else if(word.equals("Withdraw"))
		{
      //Get withdraw amount
			int amount=Integer.parseInt(withAmt.getText());
      //Create new transaction object with username, password, withdraw, and null
			Transaction trans = new Transaction(username,amount,"withdraw",null);
      //Set balance
			balance=trans.getMoney();
			transLabel.setVisible(true);
      //If transaction went through
			if(trans.getSuccess())
			{
				transLabel.setText("Transaction: Successful");
				moneyLabel.setText(fmt.format(balance));
			}
			else
				transLabel.setText("Transaction: Failed");
			repaint();
		}
    //If user clicks transfer button...
		else if(word.equals("Transfer"))
		{
      //Get transfer amount
			int amount=Integer.parseInt(transAmt.getText());
      //Create new transaction object with username, password, transfer, and username of transferee
			Transaction trans = new Transaction(username,amount,"transfer",transUser.getText());
			balance=trans.getMoney();
			transLabel.setVisible(true);
      //If transaction was successful
			if(trans.getSuccess())
			{
				transLabel.setText("Transaction: Successful");
				moneyLabel.setText(fmt.format(balance));
			}
			else
				transLabel.setText("Transaction: Failed");
			repaint();
		}
    //If user clicks logout button...
		else if(word.equals("Logout"))
		{
      //Closes program
			System.exit(0);
			acctPanel.setVisible(false);
			depPanel.setVisible(false);
			tranPanel.setVisible(false);
			homePage.setVisible(true);
		}
	}
  //Collect data for username and password from initial login
	private void collectData()
	{
		username = usernameBox.getText();
		password = new String(this.passwordBox.getPassword());
	}
}
