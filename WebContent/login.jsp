<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"      
       %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/OnlineBankingSystem/static/css/login.css" />
<link rel="stylesheet" href="/css/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script type="text/javascript" src="/OnlineBankingSystem/static/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="/OnlineBankingSystem/static/js/loginSliders.js"></script>

<title>Insert title here</title>
</head>
<body>

 <form  id="loginForm" name="login-form" class="login-form" action="Controller" method="post">
		
		    <div class="header">
			    <h1>Online Banking System</h1>
			    <span>Please enter your User Name and Password to Login</span>
			    <!-- Print out error message if there is one. -->
				
		    </div>

			<input type="hidden" id="command" name="command" value="user.Login"/>
		    <div class="content">
				<input id="username" name="User" type="text" class="input username" value="Username" onfocus="this.value=''" required>
				<input id="password" name="Pass" type="password" class="input password" value="Password" onfocus="this.value=''" required>
				<input id="name" name="name" type="hidden" value="login">
		    </div>
		   
		    <div class="footer">
		    
		   		<input type="submit" name="submit" value="Login" class="button" />
		    <%
		    if(request.getAttribute("user")!=null)
		    	out.println("<b style=\"color:red\">invalid username or password</b>");
		    %>
		    </div>
		</form>
 
 
</body>
</html>