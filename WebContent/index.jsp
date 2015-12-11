<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"      
       %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.bank.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>

<%

if((String)request.getSession().getAttribute("user")=="0"||(String)request.getSession().getAttribute("user")==null){
	 request.getRequestDispatcher("login.jsp").forward(request,response);
}

%>
<title>Home</title>
<meta charset="utf-8">
<link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
<link rel="stylesheet" href="css/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<link rel="stylesheet" href="/css/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery-1.4.2.js" ></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_400.font.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_700.font.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_600.font.js"></script>
<script type="text/javascript">
</script>
<!--[if lt IE 9]>
	<script type="text/javascript" src="http://info.template-help.com/files/ie6_warning/ie6_script_other.js"></script>
	<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
</head>
<body id="page1">
<div class="main">
<!-- header -->
	<header>
		<div class="wrapper" align="center">
			<img src="/OnlineBankingSystem/images/banner.png"  align="middle"/>
			<form id="search" action="LogoutServlet" method="post">
				<div class="bg">
					<input class="input" type="hidden" value="logout">
					<input style="color: white;" type="submit" class="submit" value="log-out">
				</div>
			</form> 
		</div>
		
		<span  style="font-size:160%">
			<b style="color:white;"> Welcome </b> <b style="color:red;"><%=request.getSession().getAttribute("user") %></b> <b style="color: white;">with Account#:</b><b style="color:red"><%=request.getSession().getAttribute("account_number") %></b>
			</span>
			<br/>
		
		<nav>
			<ul id="menu">
				<li class="alpha" id="menu_active"><a href="/OnlineBankingSystem/Controller?req=balance"><span><span>Balance</span></span></a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=statement"><span><span>Statement</span></span></a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=transfer"><span><span>Transfer Funds</span></span> </a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=payee"><span><span>Add Payee</span></span> </a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=bill"><span><span>Pay Bill</span></span> </a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=loanRequest"><span><span>Loan Request</span></span></a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=creditRequest"><span><span>Credit Card Request</span></span></a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=loanSimulator"><span><span>Loan Simulator</span></span></a></li>
				<li class="omega"><a href="/OnlineBankingSystem/Controller?req=status"><span><span>Status</span></span></a></li>
			</ul>
		</nav>
		<div class="wrapper">
			
		</div>
	</header><div class="ic"></div>
<!-- / header -->
<!-- content -->
	<section id="content">
		<div class="table table-striped"> 
				<table class="table">

		<% if(request.getAttribute("content")!=null){ %>
				<p align="center"> <%=(String)request.getAttribute("content") %></p>
		<%} %>
				</table>
		</div>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("balance")){ %>
				<div class="row">
				  <div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("savings_content") %></div>
				</div>
				
				<div class="row">
				  <div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("cheqings_content") %></div>
				</div>
				
				<div class="row">
				  <div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("balance_content") %></div>
				</div>
		<%} %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("statement")){ %>
			<table class="table table-striped">
				<div class="row">
				  <div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("statement") %></div>
				</div>
			</table>
		<%} %>
		
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("transfer")){ %> 
			<form id="form1" class="form-group" name="transferForm" action="Controller" method="post">
			  <div class="form-group">
			    <label for="amount">Amount</label>
			    <input  type="number" class="form-control" name="amount" id="amount" placeholder="Enter amount to be transfered"  required="required"/> 
			  </div>
			 
			   <div class="form-group">
			     <label for="recipient">Recipient Account Number</label>
			    <input type="number" pattern=".{5,5}"  class="form-control" name="recipient" id="recipient" placeholder="Enter recipient account number"  required="required"/> 
			  </div>
			  
			   <div class="form-group">
			    <label for="recipient">Sender's Account Number</label>
			    <input type="number" pattern=".{5,5}"  class="form-control" name="sender" id="sender" placeholder="Enter amount to be transfered" required="required"/> 
			  </div>
			  
				<div align="center" class="form-group" >
					<input type="hidden" name="name" value="transfer" />
		 			<input type="submit" class="btn btn-success" name="submit" id="submit" value="Submit Transfer" />
				</div>
			</form>	
			
		<%} %>
		
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("payee")){ %> 
			<form id="form2" class="form-group" name="payeeForm" action="Controller" method="post">
			  <div class="form-group">
			    <label for="pyAcc">Account Number</label>
			    <input type="number" pattern=".{5,5}" class="form-control" name="pyAcc" id="pyAcc" required="required" placeholder="Enter Payee's account number"/>
			  </div>
			  
			  <div class="form-group">
			    <label for="pyAccName">Payee's Name</label>
			    <input type="text" class="form-control" name="pyAccName" id="pyAccName" required="required" placeholder="Enter Payee's prefered name" />
			  </div>
			  
			  <div class="form-group" align="center">
			  	<input type="hidden" name="name" value="payee" />
			    <input type="submit" class="btn btn-success" name="submit" id="submit" value="Add Payee" />
			  </div>
			</form>
			
		<%} %>
		
		
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("bill")){ %> 
			<form id="form1" class="form-group" name="billForm" method="post" action="Controller">
			  <div class="form-group">
			    <label for="amount">Enter amount</label>
			    <input type="number" class="form-control" name="amount" id="amount" required="required" placeholder="Enter Amount" />
			  </div>
			  
			  <div class="form-group">
			  	<label for="amount">Select Payee's name</label>
			    <select name="payee_acc" class="form-control" >
				    <c:forEach items="${bill_content}" var="current">
				    <option value="${current.account_no}">${current.name}</option>
				    </c:forEach>
			    </select>
			  </div>
			  <div class="form-group">
			  	<label for="amount">Pay bills from:</label>
			    <select name="debitedAcc" class="form-control" >
				    <option value="Cheqings">Cheqings</option>
				    <option value="Savings">Savings</option>
			    </select>
			  </div>
			  
			  
			  <div class="form-group" align="center">
			    <input type="hidden" name="name" value="bill" />
			    <input type="submit" class="btn btn-success" name="submit" id="submit" value="Pay Bill" />
			  </div>
			</form>
			
		<% } %>
		
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("loanRequest")){ %> 
		<form id="form1" class="form-group" name="form1" method="post" action="Controller">
	  		<div class="form-group">
			    <label for="amount">Loan Amount</label>
			    <input type="number" class="form-control" name="loanAmount" id="amount" required="required" placeholder="Enter amount you want to loan" />
	  		</div>
	  		
	  		<div class="form-group">
			    <label for="amount">Payback Amount</label>
			    <input type="number" class="form-control" name="paybackAmount" id="amount" required="required" placeholder="Enter amount you would be paying back" />
	  		</div>
	  		
	  		<div class="form-group">
	  			<label for="paybackInterval">Select Pay back interval</label>
			    <select class="form-control" name="paybackInterval">
				  <option>Monthly</option>
				</select>
	  		</div>
	  		
	  		<p> <em> <b>NOTE: The interest rate is fixed at 15%</b></em></p>
	 
	  		<div class="form-group" align="center">
		  		<input type="hidden" name="name" value="loanRequest" />
		    	<input type="submit" name="submit" class="btn btn-success" id="submit" value="Submit request" />
	  		</div>
		</form>
		<div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("content") %></div>   
		<%} %>

		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("loanSimulator")){ %> 
		<form id="form1" class="form-group" name="loanSimulatorForm" method="post" action="Controller">
			<div  align="center" class="input-group date col-md-3" >
			    <input name="loanDate" type="date" value="" />
			</div>
	  		
	  		<div class="form-group" align="center">
		  		<input type="hidden" name="name" value="loanSimulator" />
		    	<input type="submit" name="submit" class="btn btn-success" id="submit" value="Simulate Loan" />
	  		</div>
		</form>   
		<div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("content") %></div>
				     
		<%} %>
		
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("creditRequest")){ %> 
		<form id="form1" class="form-group" name="creditCardRequest" method="post" action="Controller">
			<p align="center"> <em> <b>NOTE: Credit card is only accepted if you have performed at least three transactionns</b></em></p>
			<div class="form-group" align="center">
		  		<input type="hidden" name="name" value="creditCardRequest" />
		    	<input type="submit" name="submit" class="btn btn-success" id="submit" value="Click to apply for credit card" />
	  		</div>     
	  	</form>
	  	<div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("content") %></div>
		<%} %>
		
		
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("status")){ %> 	
			
			<div class="row">
				  <div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("loan_status") %></div>
				</div>
				
				<div class="row">
				  <div class="col-xs-6 col-md-12" align="center"><%=(String)request.getAttribute("credit_card_status") %></div>
			</div>
		<%} %>
	</section>
<!-- / content -->
<!-- footer -->
	<footer>
		<p align="middle" style="color: white;">&copy; 2015, Adigun Jide Idris <p>
	</footer>
<!-- / footer -->
</div>
<script type="text/javascript"> Cufon.now(); </script>
</body>
</html>