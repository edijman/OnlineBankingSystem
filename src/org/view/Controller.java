package org.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.balance.Customer;
import org.balance.Payee;
import org.dao.DBConnector;


/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private int account_number = 0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String request_type=(String)request.getParameter("req");
    	int account_number=(int)request.getSession().getAttribute("account_number");
    	System.out.println(request_type);
    	
    	String total_balance_content="";
    	String savings_balance_content="";
    	String cheqings_balance_content="";

    	
    	if(request_type.equals("balance")){
    		Customer customer = new Customer(account_number);
    		
    		
    		total_balance_content=customer.getBalance();
    		savings_balance_content=customer.getSavingsBalance();
    		cheqings_balance_content=customer.getCheqingsBalance();
    	}
    	
    	if(request_type.equals("bill")){
			Customer payBill = new Customer(account_number);
			ArrayList<Payee> result = payBill.getPayeeList(account_number);
			request.setAttribute("content", result);
			RequestDispatcher dispatcher;
    	}
    	
    	
		request.setAttribute("balance_content", total_balance_content);
		request.setAttribute("savings_content", savings_balance_content);
		request.setAttribute("cheqings_content", cheqings_balance_content);
		RequestDispatcher dispatcher;
		dispatcher=	request.getRequestDispatcher("/index.jsp");
	    dispatcher.forward(request, response);
    }
    
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
		    String request_type=(String)request.getAttribute("req");
		    String name=(String)request.getParameter("name");
		    
		    System.out.println("name is :: "+name + " req ::"+request_type);
		    if(name.equals("login"))
		    {
				String user=(String)request.getParameter("User");
				String pass=(String)request.getParameter("Pass");
				
				//request.setAttribute("list", list);
				request.setAttribute("user", user);
				request.setAttribute("pass", pass);
				int account_number= DBConnector.isvalid(user, pass);
			    RequestDispatcher dispatcher;
			    // if the user and pass are incorrect
				if(account_number==0){
					 dispatcher=	request.getRequestDispatcher("/index.jsp");
				
					 dispatcher.forward(request, response);
				}
				else
				{
				
					request.getSession().setAttribute( "user", user ); 
					request.getSession().setAttribute( "account_number", account_number );
					
					String content="welcome to the Online banking";
					request.setAttribute("content", content);
					dispatcher=	request.getRequestDispatcher("/index.jsp");
				    dispatcher.forward(request, response);
				 
				 }
				
			}else if(name.equals("transfer")){
				int sender=Integer.parseInt(request.getParameter("sender"));
				float amount=Float.parseFloat(request.getParameter("amount"));
				int recipient=Integer.parseInt(request.getParameter("recipient"));
				int account_number = (Integer)request.getSession().getAttribute( "account_number");
				Customer transfer = new Customer(account_number);
				
				String result = transfer.transferFund(sender,recipient,amount);
				System.out.println("transfer = "+result);
				request.setAttribute("content", result);
				RequestDispatcher dispatcher;
				dispatcher=	request.getRequestDispatcher("/index.jsp");
			    dispatcher.forward(request, response);
			}
			
			else if(name.equals("payee"))
			{
				int payee_account_no=Integer.parseInt(request.getParameter("pyAcc"));
				String payee_account_name=(String)request.getParameter("pyAccName");
				int account_number = (Integer)request.getSession().getAttribute( "account_number");
				Customer payee = new Customer(account_number);
				String result = payee.addPayee(account_number, payee_account_no, payee_account_name);
				
				
				request.setAttribute("content", result);
				RequestDispatcher dispatcher;
				dispatcher=	request.getRequestDispatcher("/index.jsp");
			    dispatcher.forward(request, response);
			}
			else if(name.equals("bill"))
			{
				String payee_account=(String)request.getParameter("payee_acc");
				System.out.println("account == "+payee_account);
				RequestDispatcher dispatcher;
				dispatcher=	request.getRequestDispatcher("/index.jsp");
			    dispatcher.forward(request, response);
			}
			else if(name.equals("loanRequest"))
			{
				int loanAmount=Integer.parseInt(request.getParameter("amount"));
				int account_number = (Integer)request.getSession().getAttribute( "account_number");
				Customer customer = new Customer(account_number);

				String result = customer.processLoanRequest(account_number, loanAmount);
				
				
				request.setAttribute("content", result);
				RequestDispatcher dispatcher;
				dispatcher=	request.getRequestDispatcher("/index.jsp");
			    dispatcher.forward(request, response);
			}
			

	}

}
