<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"      
       %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.balance.*" %>
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
<link rel="stylesheet" href="/css/bootstrap/css/bootstrap.css"">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script type="text/javascript" src="js/jquery-1.4.2.js" ></script>
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/cufon-replace.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_400.font.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_700.font.js"></script>
<script type="text/javascript" src="js/Myriad_Pro_600.font.js"></script>
<!--[if lt IE 9]>
	<script type="text/javascript" src="http://info.template-help.com/files/ie6_warning/ie6_script_other.js"></script>
	<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
</head>
<body id="page1">
<div class="main">
<!-- header -->
	<header>
		<div class="wrapper">
			<img src="/OnlineBankingSystem/images/banner.png"  align="middle"/>
			<form id="search" action="LogoutServlet" method="post">
				<div class="bg">
				    
					<input class="input" type="hidden" value="logout">
					<input type="submit" class="submit" value="log-out">
				</div>
			</form> 
		</div>
		
		<span style="font-size:160%">
			Welcome <b style="color:green"><%=request.getSession().getAttribute("user") %></b> with Account#:<b style="color:green"><%=request.getSession().getAttribute("account_number") %></b>
			</span>
			<br/>
		
		<nav>
			<ul id="menu">
				<li class="alpha" id="menu_active"><a href="/OnlineBankingSystem/Controller?req=balance"><span><span>Balance</span></span></a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=transfer"><span><span>Transfer Funds</span></span> </a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=payee"><span><span>Add Payee</span></span> </a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=bill"><span><span>Pay Bill</span></span> </a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=loanRequest"><span><span>Loan Request</span></span></a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=creditRequest"><span><span>Credit Request</span></span></a></li>
				<li><a href="/OnlineBankingSystem/Controller?req=loanSimulator"><span><span>Loan Simulator</span></span></a></li>
				<li class="omega"><a href="/OnlineBankingSystem/Controller?req=status"><span><span>Status</span></span></a></li>
			</ul>
		</nav>
		<div class="wrapper">
			
		</div>
	</header><div class="ic">More Website Templates at TemplateMonster.com!</div>
<!-- / header -->
<!-- content -->
	<section id="content">
		<div class="wrapper">
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("balance")){ %>
			<table align="center">
			<tr> 
			<ul>
				<li> <%=(String)request.getAttribute("balance_content") %> </li>
				<li> <%=(String)request.getAttribute("savings_content") %> </li>
				<li> <%=(String)request.getAttribute("cheqings_content") %> </li>
			</ul>
			</tr>
			</table>
		<%} %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("transfer")){ %> 
			<form id="form1" class="form-inline" name="form1" action="Controller" method="post">
  			<p>
			    <label for="amount">Amount to transfer</label>
			    <input type="text" name="amount" id="amount" />
  			</p>
  			<p>
			    <label for="recipient">Recipient Account Number</label>
			    <input type="text" name="recipient" id="recipient" />
  			</p>
  			<p>
			    <label for="recipient">Sender's Account Number</label>
			    <input type="text" name="sender" id="sender" />
  			</p>
  			<p>
  				<input type="hidden" name="name" value="transfer" />
    			<input type="submit" name="submit" id="submit" value="Submit Transfer" />
  			</p>
			</form>
		<%} %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("payee")){ %> 
			<form id="form1" name="form1" method="post" action="Controller">
			  <p>
			    <label for="pyAcc">Enter Payee Account Number</label>
			    <input type="text" name="pyAcc" id="pyAcc" />
			  </p>
			  <p>
			    <label for="pyAccName">Enter Payee Name</label>
			    <input type="text" name="pyAccName" id="pyAccName" />
			  </p>
			  <p>
			  	<input type="hidden" name="name" value="payee" />
			    <input type="submit" name="submit" id="submit" value="Submit" />
			  </p>
			</form>
		<%} %>
		
		
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("bill")){ %> 
			<form id="form1" name="form1" method="post" action="Controller">
			  <p>
			    <label for="amount">Enter amount</label>
			    <input type="text" name="amount" id="amount" />
			    <select name="payee_acc" >
			    <c:forEach items="${content}" var="current">
			    <option value="${current.account_no}">${current.name}</option>
			    </c:forEach>
			    </select>
			    
			  </p>
			  <input type="hidden" name="name" value="bill" />
			  <p>
			    <input type="submit" name="submit" id="submit" value="Submit" />
			  </p>
			</form>
		<% } %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("loanRequest")){ %> 
		<form id="form1" name="form1" method="post" action="Controller">
	  		<p>
		    <label for="amount">Enter amount to loan</label>
		    <input type="text" name="amount" id="amount" />
	  		</p>
	  		<p>NOTE: loan lower than $500 incurs an interest rate of 10% and higher incurs an interest rate of 20%
	  		</p>
	  		<p>
	  		<input type="hidden" name="name" value="loanRequest" />
	    	<input type="submit" name="submit" id="submit" value="Submit request" />
	  		</p>
		</form>   
		<%} %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("mortgageRequest")){ %> 
					     
		<%} %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("creditRequest")){ %> 
					     
		<%} %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("loanSimulator")){ %> 
					     
		<%} %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("mortgageSimulator")){ %> 
					     
		<%} %>
		<% if(request.getParameter("req")!=null&&request.getParameter("req").equals("status")){ %> 
					     
		<%} %>

		</div>
	</section>
<!-- / content -->
<!-- footer -->
	<footer>
		Copyright&copy; 2015, Maysam, Idris 
	</footer>
<!-- / footer -->
</div>
<script type="text/javascript"> Cufon.now(); </script>
</body>
</html>