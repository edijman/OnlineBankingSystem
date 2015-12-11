package org.dao;
import org.apache.commons.codec.binary.Base64;
import org.bank.Payee;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DBConnector {
	
	public static void main(String arg[])
	{
		System.out.println(isvalid("sss","123456"));
	}
	
//------------------------------------------------------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------------------------------------------------------
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
   
//------------------------------------------------------------------------------------------------------------------------------
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
//------------------------------------------------------------------------------------------------------------------------------	
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

//------------------------------------------------------------------------------------------------------------------------------
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
					return "Sender account number is invalid";
				}
				else if(!cheqing_account_no.equals(""+sender))
				{
					return "The sender account number is not yours";
				}
				else
				{
					sender_balance = Integer.parseInt(cheqing_balance);
					if(sender_balance < amount)
					{
						return "Sender account balnce is not enough";
					}else{
						sender_balance = sender_balance-(int)amount;
					}
				}
			}
			else
			{
				System.out.println("account code :: "+sender_account_code);
				return "You are not authorise to perform this operation";
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
//------------------------------------------------------------------------------------------------------------------------------	
	public Integer isAccExist(int payee_account_no)  {
		
		
		java.sql.Connection con = connectToDB();

		PreparedStatement payeExist = null;
		try
		{
			payeExist = con.prepareStatement("select * from  accounts where  accounts.savings_account_no = "+payee_account_no+" OR accounts.cheqings_account_no = "+payee_account_no+"");
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
	
//------------------------------------------------------------------------------------------------------------------------------
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
	
//------------------------------------------------------------------------------------------------------------------------------	
	private static java.sql.Connection connectToDB() 
	{
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
	
//------------------------------------------------------------------------------------------------------------------------------
	public String processLoan(int account_number, int loan_amount, double interest, int interval, int paybackAmount) 
	{
		java.sql.Connection con = connectToDB();
		PreparedStatement loanQuery = null;
		double loan_balance = loan_amount ;
		int bank = 11111;
		int recipient = 20000 + account_number;
		if(!isUnbalanceLoan(account_number))
		{
			try 
			{	
				loanQuery = con.prepareStatement("insert into loans (userid, interest, loan_balance, loan_payment, intervals) values ("+account_number+", "+interest+", "+loan_balance+", "+paybackAmount+", "+interval+")");
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
				e.printStackTrace();
			}
			if(r==0)
			{
				return "Loan cannot be processed, you have existing unbalance loan";
			}else
			{
				String transfer_status = transferFund(5, bank, recipient, loan_amount);
				return transfer_status;
			}
		}
		else
		{
			System.out.println("hey I am here");
			return "loan can not be processes, You have unbalance loan";
		}
	}
	
//------------------------------------------------------------------------------------------------------------------------------	
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
			if(r.next())
			{
				System.out.println("unbalance true");
				return true;
			}else
			{
				System.out.println("unbalance false");
				return false;
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return false;
		
	}
	
//------------------------------------------------------------------------------------------------------------------------------
	public String applyForCreditCard(int account_number) {
		java.sql.Connection con = connectToDB();
		int cheqings_account = 20000 + account_number;
		int savings_account = 10000 + account_number;
		PreparedStatement creditRequest = null;
		PreparedStatement creditStatus = null;
		try
		{
			creditRequest = con.prepareStatement("select count(*) from transactions where transactions.deposited_from = "+cheqings_account+" OR transactions.deposited_from = "+savings_account+";");
		}
		catch(SQLException e)
		{
			System.out.print("Payee accounts does not exist");
		}
		ResultSet r = null;
			try
			{
				r = creditRequest.executeQuery();
				
				
			} catch (SQLException e) 
			{
			
				e.printStackTrace();
			}
			int row = 0;
			try 
		   {
			r.next();
			row = r.getInt(1);
			System.out.println(row);
		   } catch (SQLException e) 
		   {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
	       System.out.println("the number of row ::"+row);

			if (row == 0) {
			    System.out.println("No records found");
			}
			else if(row > 3)
			{
				try {
					creditStatus = con.prepareStatement("update credit_card_status SET status = 'Accepted' where userid = '"+account_number+"'");
							
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					creditStatus.executeUpdate();
					return "Credit Card Request Accepted";
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			System.out.println("number of rows" + row);
			return "Credit Card Request Declined";
	}

//------------------------------------------------------------------------------------------------------------------------------
	public String LoanStatus(int account_number) 
	{
		java.sql.Connection con = connectToDB();
		PreparedStatement loan_status = null;
		float interest = 0;
	    int loan_balance = 0;;
	    int loan_payment = 0;
	    int intervals = 0;
		try
		{
			loan_status = con.prepareStatement("SELECT * FROM loans where userid= '"+account_number+"';");
		}
		catch(SQLException e)
		{
			System.out.print("");
		}
		ResultSet r = null;
			try
			{
				r = loan_status.executeQuery();
				if(r.next()){
					r.last();
					interest   = r.getFloat("interest");
					loan_balance = r.getInt("loan_balance");
					loan_payment = r.getInt("loan_payment");
					intervals = r.getInt("intervals");
				}
			} catch (SQLException e) {
			
				//e.printStackTrace();
			}
			if(r != null){
				if(intervals == 7)
				{
					return "Interest rate: " + interest + "\r\n " + "Loan balance of ::" +loan_balance + "\r\n " + "Loan Payment of :: " + loan_payment + "\r\n " + "Intervals :: Weekkly";
				}
				else
				{
					return "Interest rate: " + interest + "\r\n " + "Loan balance of ::" +loan_balance + "\r\n " + "Loan Payment of :: " + loan_payment + "\r\n " + "Intervals :: Monthly";
				}
			}else{
				return "Not Accepted PLEASE RE-APPLY AFTER MEETING THE REQUIREMENT";
			}
	}

//------------------------------------------------------------------------------------------------------------------------------
	public String creditCardStatus(int account_number) 
	{
		
		java.sql.Connection con = connectToDB();
		PreparedStatement card_status = null;
		String status = "";
		try
		{
			card_status = con.prepareStatement("select status from credit_card_status where userid = "+account_number+"");
		}
		catch(SQLException e)
		{
			System.out.print("");
		}
		ResultSet r = null;
			try
			{
				r = card_status.executeQuery();
				if(r.next())
					status = r.getString(1);
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			if(status!= null && status.equals("Accepted")){
				return "Accepted";
			}else{
				return "Not Accepted PLEASE RE-APPLY AFTER MEETING THE REQUIREMENT";
			}
	}

	public String displayStatement(int account_number) {
		java.sql.Connection con = connectToDB();
		PreparedStatement card_status = null;
		String statement = "";
		Format df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			card_status = con.prepareStatement("SELECT * FROM transactions, accounts where (accounts.userid = "+account_number+") and ((accounts.savings_account_no = deposited_from or accounts.cheqings_account_no= deposited_from) OR(accounts.savings_account_no = withdraw_to or accounts.cheqings_account_no = withdraw_to));");
		}
		catch(SQLException e)
		{
			System.out.print("");
		}
		
		String reportDate ="";
		int debited_to =0;
		int withdraw_to=0;
		String amount ="";
		String balance ="";
		String result = "";
		ResultSet r = null;
			try
			{
				r = card_status.executeQuery();
				while(r.next()){
					Date transactionDate = r.getDate("transaction_date");
					reportDate = df.format(transactionDate);
					account_number   = r.getInt("cheqings_account_no");
					debited_to = r.getInt("deposited_from");
					withdraw_to = r.getInt("withdraw_to");
					amount = r.getString("amount");
					System.out.println(reportDate + account_number + debited_to);
					result += "<tr> <td>" + reportDate + "</td>" + "<td>"  + debited_to + "</td>" + "<td>"  + withdraw_to + "</td>"  + "<td>"  + amount + "</td> </tr>";
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
			if(r != null){
				//System.out.println(reportDate + account_number + debited_to);
				return result;
			}else{
				return "Not Accepted PLEASE RE-APPLY AFTER MEETING THE REQUIREMENT";
			}
	}

	public String payBills(int account_number, String debit_account, float amount, int recipient) {

		int sender =0;
		if(debit_account.equals("Cheqings"))
		{
			sender = 20000 + account_number; 
		}
		else if(debit_account.equals("Savings"))
		{
			sender = 10000 + account_number;
		}
		String status = transferFund(account_number, sender, recipient, amount);
		if(status.equals("complete"))
		{
			return "Payment Successful";
		}
		else
		{
			return "Payment not successful, please check recipient account number and your account balance";
		}
	}

	public int getLoanAmount(int account_number) {
		java.sql.Connection con = connectToDB();
		PreparedStatement loan_amount = null;
		try
		{
			loan_amount = con.prepareStatement("SELECT * FROM loans where userid = '"+account_number+"'");
		}
		catch(SQLException e)
		{
			System.out.print("HIIIII");
		}
		ResultSet r = null;
		int amount =0;
		try
		{
			r = loan_amount.executeQuery();
			System.out.println("performed query");
			   if(r.next())
			   {
				   r.last();
					amount = r.getInt("loan_balance");
					System.out.println("balance loan" + amount);
			   }
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		System.out.println("the amount is" + amount);
		return amount;
	}
 
	
	public int getMonthlyPayment(int account_number) 
	{
		java.sql.Connection con = connectToDB();
		PreparedStatement monthly_payment = null;
		System.out.println(account_number);
		try
		{
			monthly_payment = con.prepareStatement("SELECT loan_payment FROM loans where userid = '"+account_number+"';");
		}
		catch(SQLException e)
		{
			System.out.print("HIIIII");
		}
		ResultSet r = null;
		int monthly_amount =0;
		try
		{
			r = monthly_payment.executeQuery();
			if(r.next())
			{
				monthly_amount = r.getInt("loan_payment");
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		System.out.println("monthly" + monthly_amount);
		return monthly_amount;
	}
	
	public int getInterval(int account_number) 
	{
		java.sql.Connection con = connectToDB();
		PreparedStatement intervals = null;
		System.out.println(account_number);
		try
		{
			intervals = con.prepareStatement("SELECT intervals FROM loans where userid = '"+account_number+"';");
		}
		catch(SQLException e)
		{
			System.out.print("HIIIII");
		}
		ResultSet r = null;
		int intervalNum=0;
		try
		{
			r = intervals.executeQuery();
			if(r.next())
			{
				intervalNum = r.getInt("Intervals");
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		System.out.println("intervals " + intervalNum);
		return intervalNum;
	}
	public String transferFundSim(int id, int sender, int recipient, float amount, int month, int months) {
		java.sql.Connection con = connectToDB();
		PreparedStatement transfer = null;
		ResultSet r = null;
		int num_r = 0;
		try 
		{
			char sender_account_code = (""+sender).charAt(0);
			
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
					return "Sender account number is invalid";
				}
				else if(!cheqing_account_no.equals(""+sender))
				{
					return "The sender account number is not yours";
				}
				else
				{
					sender_balance = Integer.parseInt(cheqing_balance);
					if(sender_balance < amount)
					{
						return "Sender account balnce is not enough";
					}else{
						sender_balance = sender_balance-(int)amount;
					}
				}
			}
			else
			{
				System.out.println("account code :: "+sender_account_code);
				return "You are not authorise to perform this operation";
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
			
				String sql = "insert into transactions (transaction_date, deposited_from, withdraw_to, amount, creationdate) VALUES (DATE_ADD(now(), INTERVAL '"+months+"' MONTH), "+sender+", "+recipient+", "+amount+", now())";
				transfer = con.prepareStatement(sql);
				System.out.println(sql);
			try 
			{
				num_r = transfer.executeUpdate();
			} catch (SQLException e)
			{
			
				e.printStackTrace();
				System.out.println("This account does not exist");
			}

			
		} catch (SQLException e)
		{
			System.out.println("this is no account");
		}
		if(num_r==month){
			return "This account does not exist";
		}else{
			return "complete";
		}
			

	}

	public void updateBalance(int id, double balance) {
		
		java.sql.Connection con = connectToDB();
		PreparedStatement updateLoanBalance = null;
		//check if payee account number and name exists before adding
		int amount = (int) balance;
			try 
			{
				updateLoanBalance = con.prepareStatement("update loans SET loan_balance = '"+amount+"' where userid = '"+id+"';");
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			int r = 0;
			try 
			{
				r = updateLoanBalance.executeUpdate();
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println("could not execute update");
			}
		
	}




	
}
