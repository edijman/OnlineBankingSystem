package org.balance;

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
	
	public Customer(int id){
		this.id = id;	
	}
	
	public String getBalance()
	{
		cheqings_balance=new DBConnector().getCheqingsBalance(id);
		savings_balance=new DBConnector().getSavingsBalance(id);
		balance= cheqings_balance + savings_balance;
		return "Your total account balance is equal to: " +"$" +balance;
	}
	
	public String getSavingsBalance()
	{
		savings_balance=new DBConnector().getSavingsBalance(id);
		return "Your savings account balance is equal to: " +"$" +savings_balance;
	}
	
	public String getCheqingsBalance()
	{
		cheqings_balance=new DBConnector().getCheqingsBalance(id);
		return "Your cheqings account balance is equal to: " +"$" +cheqings_balance;
	}
	
	
	public String transferFund(int sender, int recipient, float amount)
	{
		String result = new DBConnector().transferFund(id, sender, recipient, amount);
		return "Your transaction is: " + result;
	}

	public String addPayee(int account_number,  int payee_account_no, String payee_account_name)
	{
		String result = new DBConnector().addPayee(account_number, payee_account_no, payee_account_name);
		return "Your Transaction is: " + result;
		
	}
	public ArrayList<Payee> getPayeeList(int account_number){
		return new DBConnector().getPayeeList(account_number);
		
	}
	public String processLoanRequest(int account_number, int loanAmount) {
		cheqings_balance=new DBConnector().getCheqingsBalance(id);
		savings_balance=new DBConnector().getSavingsBalance(id);
		double interest = 0;
		double loan_payment =0;
		
		
		balance = cheqings_balance + savings_balance;
		if(balance > 200)
		{
			interest = 0.15;
			processLoan = new DBConnector().processLoan(account_number, loanAmount, interest, loan_payment);
			
			//process loan
			return "loan is being processed";
		}
		else
		{
			return "Loan Declined";
			//decline loan
		}
		
		
	}
	
	
	
}
