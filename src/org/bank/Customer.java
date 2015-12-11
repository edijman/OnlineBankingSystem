package org.bank;

import java.util.ArrayList;

import org.dao.DBConnector;

public class Customer {
	
	float balance = 0;
	float cheqings_balance = 0;
	float savings_balance = 0;
	int sender = 0;
	int recipient = 0;
	float amount =0;
	int id;
	int account_number =0;
	String payee_account_name="";
	int payee_account_no=0;
	String processLoan ="";
	double interest = 0.15;
	private static int monthsInAYear = 12;
	
	public Customer(int id)
	{
		this.id = id;	
	}
//------------------------------------------------------------------------------------------------------------------------------
	public String getBalance()
	{
		cheqings_balance=new DBConnector().getCheqingsBalance(id);
		savings_balance=new DBConnector().getSavingsBalance(id);
		balance= cheqings_balance + savings_balance;
		return "<b>Your total account balance is:</b>" +"$" +balance;
	}
	
//------------------------------------------------------------------------------------------------------------------------------	
	public String getSavingsBalance()
	{
		savings_balance=new DBConnector().getSavingsBalance(id);
		return "<b>Your savings account balance is: </b>" +"$" +savings_balance;
	}
	
//------------------------------------------------------------------------------------------------------------------------------
	public String getCheqingsBalance()
	{
		cheqings_balance=new DBConnector().getCheqingsBalance(id);
		return "<b>Your cheqings account balance is: </b>" +"$" +cheqings_balance;
	}
	
//------------------------------------------------------------------------------------------------------------------------------	
	public String transferFund(int sender, int recipient, float amount)
	{
		String result = new DBConnector().transferFund(id, sender, recipient, amount);
		return result;
	}
	public String transferFundSim(int sender, int recipient, float amount, int month, int months)
	{
		String result = new DBConnector().transferFundSim(id, sender, recipient, amount, month, months);
		return result;
	}
	
//------------------------------------------------------------------------------------------------------------------------------

	public String addPayee(int account_number,  int payee_account_no, String payee_account_name)
	{
		String result = new DBConnector().addPayee(account_number, payee_account_no, payee_account_name);
		return result;
		
	}
//------------------------------------------------------------------------------------------------------------------------------
	public ArrayList<Payee> getPayeeList(int account_number){
		return new DBConnector().getPayeeList(account_number);
		
	}
	
//------------------------------------------------------------------------------------------------------------------------------
	public String processLoanRequest(int account_number, int loanAmount, String paybackInterval, int paybackAmount) 
	{
		cheqings_balance=new DBConnector().getCheqingsBalance(id);
		savings_balance=new DBConnector().getSavingsBalance(id);
		balance = cheqings_balance+savings_balance;
		int interval=0;
		
		if (paybackInterval.equals("Weekly"))
		{
			interval = 7;
			processLoan = new DBConnector().processLoan(account_number, loanAmount, interest, interval, paybackAmount);
			//process loan
			return "loan is being processed" + "<br>"+ processLoan;
			
		}
		else if(paybackInterval.equals("Monthly"))
		{
			interval = 30;
			processLoan = new DBConnector().processLoan(account_number, loanAmount, interest, interval, paybackAmount);
			//process loan
			return "loan is being processed " + "<br>"+ processLoan;		
		}
		return "Loan declined";
		
		
	}
//------------------------------------------------------------------------------------------------------------------------------
	public String simulateLoan(int account_number, int loanAmount, int loan_term, int monthly_payment,
			double interest_rate, int intervals)
	{
		//interest_rate  = 0.15;
		//double yearly_interest_rate = getInterestRate(interest_rate);
		double yearly_interest_rate = interest_rate;
		double balance = 0;
		double require_monthly_payment =0;
		int number_of_month = loan_term/30;
		System.out.println("number of Month" +number_of_month);
		double monthly_rate = yearly_interest_rate / monthsInAYear;
		int months =0;
		require_monthly_payment = calculateMonthlyPayment(loanAmount, number_of_month, monthly_rate);
		require_monthly_payment =  Math.round(require_monthly_payment*100)/100.0d;
		balance = calculateSimpleInterest(loanAmount, yearly_interest_rate, loan_term);
		System.out.println(balance +"hdhfvn");
		if(balance < monthly_payment)
		{
			monthly_payment = (int) balance;
		}
		
		
		String loan_output = loanSimulationOutput(number_of_month, monthly_payment, interest_rate, balance, require_monthly_payment, months, account_number);
		
		return "<br><b>The required monthly payment</b>: $" +require_monthly_payment + "</br>" + "<br><b>Loan Period</b>: " +loan_term+ "year(s)" + "</br>" + "<br><b>Loan amount</b>: $" +loan_term + "</br>" +"<br><b>Interest Rate</b>: " +interest_rate + "</br>"+ loan_output;
	}

//------------------------------------------------------------------------------------------------------------------------------
	private String loanSimulationOutput(int number_of_month, int monthly_payment, double interest_rate, double balance,
			double require_monthly_payment, int months, int account_number) 
	{
		StringBuilder loan_output = new StringBuilder("");
		String output ="";	
		int account_ID =0;
		while(months < number_of_month)
		{
			months++;
			int lBalance = new DBConnector().getLoanAmount(account_number);
			System.out.println("JJJJJ" + lBalance);
			System.out.println("The balance is " +balance);
			balance =  Math.round(balance*100)/100.0d;
			Float bBalance = (float) balance;
			account_ID = 20000 + account_number;
			float monthlPay = (float) monthly_payment;
			System.out.println(account_ID);
			System.out.println("My monthly p" +monthly_payment);
			if(balance==0)
			{
				break;
			}
			else
			{
				
				transferFundSim(account_ID, 11111, monthlPay, number_of_month, months);
				DBConnector updateBalance = new DBConnector();
				updateBalance.updateBalance(id, balance);
				balance = balance - monthly_payment;
				//update Balance
			}
		}
		
		output = loan_output.toString();
		return output;
	}
//------------------------------------------------------------------------------------------------------------------------------
	private double calculateSimpleInterest(int loanAmount, double yearly_interest_rate, int loan_term) 
	{
		double amount = 0;
		double interest = (loanAmount * yearly_interest_rate * loan_term/100);
		amount = loanAmount + interest;
		return amount; 
	}
//------------------------------------------------------------------------------------------------------------------------------
	private double calculateMonthlyPayment(int loanAmount, int number_of_month, double monthly_rate) 
	{
		double require_monthly_payment = (loanAmount*monthly_rate) / (1-Math.pow(1+monthly_rate, -number_of_month));
		return require_monthly_payment;
	}
	
//------------------------------------------------------------------------------------------------------------------------------
	private double getInterestRate(String interest_rate) 
	{
		double yearly_interest_rate =0;
		if(interest_rate.equals("10%"))
		{
			yearly_interest_rate = 0.1;
			return yearly_interest_rate;
			
		}
		else if(interest_rate.equals("15%"))
		{
			yearly_interest_rate = 0.15;
			return yearly_interest_rate;
		}
		return yearly_interest_rate;
	}

//------------------------------------------------------------------------------------------------------------------------------
	public String creditCardRequest(int account_number) 
	{
		 String status = new DBConnector().applyForCreditCard(account_number);
		
		return status;
	}
//------------------------------------------------------------------------------------------------------------------------------
	public String getLoanStatus(int account_number) 
	{
		String loan_status = new DBConnector().LoanStatus(account_number);
		
		return loan_status;
	}
//------------------------------------------------------------------------------------------------------------------------------
	public String getCreditCardStatus(int account_number) 
	{
		String credit_status = new DBConnector().creditCardStatus(account_number);
		
		return "Your Credit Card Status is: " + credit_status;
	}
	public String displayStatement(int account_number) {
		StringBuilder statement_output = new StringBuilder("");
		String statement ="";	
		statement_output.append("<tr> <th> Date </th> <th> Debited from ($) </th> <th> Credited to ($) </th> <th> Amount ($) </th> </tr> ");	
		statement = new DBConnector().displayStatement(account_number);
		statement_output.append(statement);
		statement_output.append(getBalance());
		return statement_output.toString();
	}
	public String payBills(int account_number, String debit_account, float amount, int recipient) {
		// TODO Auto-generated method stub
		String result = new DBConnector().payBills(account_number, debit_account, amount, recipient);
		return result;
	}
	
	public void simulate(long day, int account_number) {
		int loan_amount = new DBConnector().getLoanAmount(account_number);
		int loan_term = (int) day;
		int monthly_payment = new DBConnector().getMonthlyPayment(account_number);
		System.out.println("HIIII" + monthly_payment);
		int intervals = new DBConnector().getInterval(account_number);
		String loan_status = new DBConnector().LoanStatus(account_number);
		double interest_rate = 0.15;
		System.out.println("again" + account_number);
		simulateLoan(account_number, loan_amount, loan_term, monthly_payment, interest_rate, intervals);
	
		
	}
	
	
	
	
}
