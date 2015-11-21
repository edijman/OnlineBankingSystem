package org.dao;
import org.apache.commons.codec.binary.Base64;
import org.balance.Payee;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DBConnector {
	
	public static void main(String arg[]){
		System.out.println(isvalid("sss","123456"));
	}
	
	
	public float getSavingsBalance(int user_id)
	{
	
		
			java.sql.Connection con = connectToDB();
			PreparedStatement savings_account = null;
	
			try 
			{
				savings_account = con.prepareStatement("select savings_balance from accounts,savings_account where userid ='"+user_id+"' and accounts.savings_account_no=savings_account.account_no");
				
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
			ResultSet r = null;
			try 
			{
				r = savings_account.executeQuery();
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
			String s=null;
				try 
				{
					while (r.next())
					{
						s=r.getString(1);
						System.out.println("saving :: "+s);
					}
				} catch (SQLException e)
				{
				
					e.printStackTrace();
				}	 
				if(s!=null)
					return Float.parseFloat(s);
				else return 0;
	
		}
   
	public float getCheqingsBalance(int user_id)
	{
	
		
			java.sql.Connection con = connectToDB();
			PreparedStatement cheqings_balance = null;
	
			try 
			{
				cheqings_balance = con.prepareStatement("select cheqing_balance from accounts,cheqing_account where userid ='"+user_id+"' and accounts.cheqings_account_no=cheqing_account.account_no");
				
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
			ResultSet r = null;
			try 
			{
				r = cheqings_balance.executeQuery();
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
			String s=null;
				try 
				{
					while (r.next())
					{
						s=r.getString(1);
						System.out.println("cheqing :: "+s);
					}
				} catch (SQLException e)
				{
				
					e.printStackTrace();
				}	 
				if(s!=null)
					return Float.parseFloat(s);
				else return 0;
	
		}
   
	
	public ArrayList<Payee> getPayeeList(int user_id)
	{
	
		
			java.sql.Connection con = connectToDB();
			PreparedStatement payee_list = null;
	
			try 
			{
				payee_list = con.prepareStatement("select payee_account_no,payee_account_name from payee where userid ='"+user_id+"'");
				
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
			ResultSet r = null;
			try 
			{
				r = payee_list.executeQuery();
			} catch (SQLException e)
			{
			
				e.printStackTrace();
			}
			String name=null;
			String account_no = null;
			ArrayList<Payee> payee_list_result = new ArrayList<Payee>();
				try 
				{
					while (r.next())
					{
						name=r.getString(1);
						account_no=r.getString(2);
						payee_list_result.add(new Payee(account_no,name));
						
					}
					System.out.println("size : "+payee_list_result.size());
				} catch (SQLException e)
				{
				
					e.printStackTrace();
				}	 
				
				return payee_list_result;
				
	
		}
	
	public static Integer isvalid(String user, String pass)  {
		
		byte[] encodedBytes = Base64.encodeBase64(pass.getBytes());
		String hashpass= new String(encodedBytes);
				
			java.sql.Connection con = connectToDB();
			PreparedStatement p = null;
			try {
				p = con.prepareStatement("select userid from users where password='"+hashpass+"' AND username='"+user+"'");
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			ResultSet r = null;
			try {
				r = p.executeQuery();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			String s=null;
				try {
					while (r.next())
					{
						s=r.getString(1);
					}
				} catch (SQLException e) {
				
					e.printStackTrace();
				}	 
     if(s!=null)
		return Integer.parseInt(s);
     else return 0;
		
	}


	@SuppressWarnings("resource")
	public String transferFund(int id, int sender, int recipient, float amount) {
		java.sql.Connection con = connectToDB();
		PreparedStatement transfer = null;

		try 
		{
			char sender_account_code = (""+sender).charAt(0);
			ResultSet r = null;
			int sender_balance=0;
			if(sender_account_code=='1'){
				transfer = con.prepareStatement("select savings_balance,savings_account_no from savings_account,accounts where account_no='"+sender+"' and account_no = accounts.savings_account_no and userid = '"+id+"'");
				r= transfer.executeQuery();
				r.next();
				String saving_balance = r.getString(1);
				String savings_account_no = r.getString(2);
				System.out.println("saving = "+savings_account_no);
				System.out.println("sender = "+sender);
				if(saving_balance==null){
					return "sender account is invalid";
				}else if(!savings_account_no.equals(""+sender)){
					return "this account is not yours";
				}else{
					sender_balance = Integer.parseInt(saving_balance);
					if(sender_balance < amount){
						return "sender account is not enough";
							
					}else{
						sender_balance = sender_balance-(int)amount;
					}
				}
			}else if(sender_account_code=='2'){
				transfer = con.prepareStatement("select cheqing_balance, cheqings_account_no from cheqing_account,accounts where account_no='"+sender+"' and account_no = accounts.cheqings_account_no and userid = '"+id+"'");
				r= transfer.executeQuery();
				r.next();
				String cheqing_balance = r.getString(1);
				String cheqing_account_no = r.getString(2);
				System.out.println("saving = "+cheqing_account_no);
				System.out.println("sender = "+sender);
				if(cheqing_balance==null)
				{
					return "sender account is invalid";
				}
				else if(!cheqing_account_no.equals(""+sender))
				{
					return "this sender account is not yours";
				}
				else
				{
					sender_balance = Integer.parseInt(cheqing_balance);
					if(sender_balance < amount)
					{
						return "sender account is not enough";
					}else{
						sender_balance = sender_balance-(int)amount;
					}
				}
			}
			else
			{
				System.out.println("account code :: "+sender_account_code);
				return "your sender account is not authorise";
			}
			
			char recipient_account_code = (""+recipient).charAt(0);
			int recipient_balance=0;
			if(recipient_account_code=='1')
			{
				transfer = con.prepareStatement("select savings_balance from savings_account where account_no='"+recipient+"'");
				r= transfer.executeQuery();
				r.next();
				String saving_balance = r.getString(1);
				if(saving_balance==null)
				{
					return "recipient account is invalid";
				}
				else
				{
					recipient_balance = Integer.parseInt(saving_balance);
					recipient_balance = recipient_balance + (int)amount;
				}
			}
			else if(recipient_account_code=='2')
			{
				transfer = con.prepareStatement("select cheqing_balance from cheqing_account where account_no='"+recipient+"'");
				r= transfer.executeQuery();
				r.next();
				String cheqing_balance = r.getString(1);
				if(cheqing_balance==null)
				{
					return "Recipient account is invalid";
				}else
				{
					recipient_balance = Integer.parseInt(cheqing_balance);
					recipient_balance = recipient_balance+(int)amount;
				}
			}
			else
			{
				System.out.println("account code :: "+recipient_account_code);
				return "your recipient account is not authorise";
			}
			
			PreparedStatement recipient_statement = null;
			
			if(recipient_account_code=='1')
			{
				recipient_statement = con.prepareStatement("UPDATE savings_account SET savings_balance='"+recipient_balance+"' WHERE account_no='"+recipient+"'");
			}
			else
			{
				recipient_statement = con.prepareStatement("UPDATE cheqing_account SET cheqing_balance='"+recipient_balance+"' WHERE account_no='"+recipient+"'");
			}
			
			int result = recipient_statement.executeUpdate();
			System.out.println("update recipient :: "+result);
			PreparedStatement sender_statement = null;
			
			if(sender_account_code=='1')
			{
				sender_statement = con.prepareStatement("UPDATE savings_account SET savings_balance='"+sender_balance+"' WHERE account_no='"+sender+"'");
			}
			else
			{
				sender_statement = con.prepareStatement("UPDATE cheqing_account SET cheqing_balance='"+sender_balance+"' WHERE account_no='"+sender+"'");
			}
			result = sender_statement.executeUpdate();
			System.out.println("update sender:: "+result);	
			transfer = con.prepareStatement("insert into transactions (transaction_date, deposited_from, withdraw_to, amount, creationdate) VALUE (now(), "+sender+", "+recipient+", "+amount+", now())");

		} catch (SQLException e)
		{
			System.out.println("this is no account");
		}
		int r = 0;
		try 
		{
			r = transfer.executeUpdate();
		} catch (SQLException e)
		{
		
			System.out.println("This account does not exist");
		}
		if(r==0){
			return "This account does not exist";
		}else{
			return "complete";
		}
			

	}
	
	public Integer isAccExist(int payee_account_no)  {
		
		
		java.sql.Connection con = connectToDB();

		PreparedStatement payeExist = null;
		try
		{
			payeExist = con.prepareStatement("select * from  accounts, payee where "+payee_account_no+" = accounts.savings_account_no OR "+payee_account_no+" = accounts.cheqings_account_no");
		}
		catch(SQLException e)
		{
			System.out.print("Payee accounts does not exist");
		}
		ResultSet r = null;
			try
			{
				r = payeExist.executeQuery();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			String s=null;
				try {
					while (r.next())
					{
						s=r.getString(1);
					}
				} catch (SQLException e) {
				
					e.printStackTrace();
				}	 
     if(s!=null)
		return Integer.parseInt(s);
     else return 0;
		
	}
	
	public String addPayee(int account_number, int payee_account_no, String payee_account_name)
	{
		java.sql.Connection con = connectToDB();
		PreparedStatement addPayeeQuery = null;
		//check if payee account number and name exists before adding
		int exist = isAccExist(payee_account_no);
		if(exist == 0)
		{
			System.out.println("Account does not exist");
			return "Account does not exist";
		}
		else
		{
			System.out.println("Account exist");
			try 
			{
				addPayeeQuery = con.prepareStatement("insert into payee (userid, payee_account_no, payee_account_name) values ('"+account_number+"', '"+payee_account_no+"', '" +payee_account_name+ "')");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			int r = 0;
			try 
			{
				r = addPayeeQuery.executeUpdate();
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println("could not execute update");
			}
			if(r==0)
			{
				return " ";
			}else
			{
				return "Payee Added successfully";
			}
		}
	}
	
	private static java.sql.Connection connectToDB() {
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) 
		{
			
			e.printStackTrace();
		}
		java.sql.Connection con = null;
		try 
		{
			//con = DriverManager.getConnection("jdbc:mysql://mysql3000.mochahost.com:3306/luxu2014_bank","luxu2014_banking","quality2015");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3333/bank","root","123456");
			
		} catch (SQLException e) {
		    System.out.println("connection failure");
			e.printStackTrace();
		}
		return con;
	}
	
	public String processLoan(int account_number, int loan_amount, double interest, double loan_payment) {
		java.sql.Connection con = connectToDB();
		PreparedStatement loanQuery = null;
		double loan_balance = loan_amount - loan_payment;
		if(isUnbalanceLoan(account_number))
		{
			try 
			{	
				int i =0;
				loanQuery = con.prepareStatement("insert into loans (userid, interest, loan_balance, loan_payment) values ("+account_number+", "+interest+", " +loan_balance+", "+loan_payment+")");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			int r = 0;
			try 
			{
				r = loanQuery.executeUpdate();
			} catch (SQLException e)
			{
				//e.printStackTrace();
				System.out.println("Loan cannot be processed, you have existing unbalance loan");
				return "Loan cannot be processed, you have existing unbalance loan";
			}
			if(r==0)
			{
				return " ";
			}else
			{
				return "loan Processed";
			}
		}
		else
		{
			return "loan can not be processes, You have unbalance loan";
		}
	}
	
	public boolean isUnbalanceLoan(int account_no) 
	{
		java.sql.Connection con = connectToDB();
		PreparedStatement payeExist = null;
		try
		{
			payeExist = con.prepareStatement("select * from  loans where "+account_no+" = userid and loan_balance >= 0");
		}
		catch(SQLException e)
		{
			System.out.print("Payee accounts does not exist");
		}
		ResultSet r = null;
			try
			{
				r = payeExist.executeQuery();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			if(r != null){
				return true;
			}else{
				return false;
			}
	}

	
}
