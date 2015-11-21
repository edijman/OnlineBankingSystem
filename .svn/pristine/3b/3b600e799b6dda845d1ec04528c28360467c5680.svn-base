<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  
        %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/OnlineBankingSystem/static/css/style2.css" />


<title>Insert title here</title>
</head>
<body>

 <img src="/OnlineBankingSystem/static/images/banner.png" align="middle"></img>

 
 <form id="form" name="form" action="LogoutServlet" method="post">
 <h2 align="center">Hello <b style="color:green"> <%= request.getAttribute("user") %> </b> <input type="submit" value="logout"></input></h2> <h3 align="center">Welcome to your travel history</h3> 
  
  
 </form>

     <%

 
  //Object[] list=(Object[])request.getAttribute("list");
	
	 if((String)request.getSession().getAttribute("user")=="0"){
		 request.getRequestDispatcher("login.jsp").forward(request,response);
	}
	
  %>
  <table class="table1">
                <thead>
                    <tr>
                        
                        <th scope="col" abbr="Starter">City</th>
                        <th scope="col" abbr="Medium">Hotel</th>
                        <th scope="col" abbr="Business">Travel Duration</th>
                       
                    </tr>
                </thead>
              
                <tbody>
                   
                   <%
                 
                /*   if(list[0].getDuration()!=0){
                     for(TravelHistory mylist : list ){
                    	 */
           			
           	      %>
                   
                    <tr>
                         <td><%="city" %></td>
                        <td><%="hotel" %></td>
                        <td><%="duration" %></td>
                       
                    </tr>
                    <%
                 
                     /*                     }
                   }
                   
                   */
                       %> 
                </tbody>
            </table>
 

 
	

  
  
 <h4 align="center"> &copy; All rights reserved for Maysam, Idris  2015</h4>
</body>
</html>