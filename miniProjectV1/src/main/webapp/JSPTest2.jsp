<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%

try
{
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","admin");
    Statement st=con.createStatement();
    System.out.println("connection established successfully...!!");     

    ResultSet rs=st.executeQuery("Select * from z_jp_messages");

    //out.println("<table border=1>");
    while(rs.next())
    {
%>

<div class="card shadow  p-2 mb-3">
 	<div class="card-header text-primary"><h5><%= rs.getInt(1) +" : "+ rs.getString(2) %></h5></div>
  	<div class="card-body">
    <p class="card-text"><%=rs.getString(3) %></p>
   	</div>
   	<p class="card-text text-right"><small class="text-muted"><%= rs.getString(4)%></small></p>
</div>

       			
<%   
/*
out.print("<tr><td>"+rs.getInt(1)+
        		"</td><td>"+rs.getString(2)+
        		"</td><td>"+ rs.getString(3)+ 
        		"</td><td>"+rs.getString(4)+
        		"</td></tr>");
*/
    }
    //out.println("</table>");
    con.close();
}
catch (Exception e){
    e.printStackTrace();
}

%>

</body>
</html>