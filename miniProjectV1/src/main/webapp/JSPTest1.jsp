<%@ page language="java" contentType="text; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 

<%
	if(request.getMethod().equalsIgnoreCase("get")){
		String site = new String("./Home");
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

%>

<%

try
{
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","admin");
    Statement st=con.createStatement();
    System.out.println("connection established successfully...!!");     
    ResultSet rs=st.executeQuery("Select id, title, description, to_char(date_time, 'YYYY-MM-DD HH24:MI:SS') from z_jp_messages");

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
    }
    con.close();
}
catch (Exception e){
    e.printStackTrace();
}

%>