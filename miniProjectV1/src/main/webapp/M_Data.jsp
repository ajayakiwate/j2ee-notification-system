<%@ page language="java" contentType="text; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %> 
<%@ page import="java.io.*" %> 
<%@ page import="java.util.Enumeration" %>


<%
	if(request.getMethod().equalsIgnoreCase("get")){
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
	
	Enumeration<String> keys = session.getAttributeNames();
	
	int FLAG=0;
	while (keys.hasMoreElements()){
		        	             	   
	   String name = (String)keys.nextElement();
	   String value = (String)session.getAttribute(name);
	   
	   	if( name.equals("s_admin") && value.equals("true") ){
	   		FLAG=1;
	   	}
	              	   
	}
%>

<script>
	$(".delete-message").click(function(){
		var id = $(this).attr("id");
		$.ajax({ 
				async: true,
				type: "POST",
				url: 'DeleteMessage',
				data: "messageid="+id,// or fData
				success: function(response){
					if(response['success']){
						alert("Message Deleted");
						
					}
					else{
						alert("Error, Cannot Delete");
					}
					$("#login").click();
			   	}
		});
	});
</script>


<%

try
{
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","admin");
    Statement st=con.createStatement();
    System.out.println("connection established successfully...!!");     
    ResultSet rs=st.executeQuery("Select id, title, description, to_char(date_time, 'HH24:MI:SS DD-MM-YYYY') from z_jp_messages ORDER BY id DESC");

    while(rs.next())
    {
    	if(FLAG==1){
    		
    	}
    	
%>

<div  class="card shadow  p-2 mb-3">
 	<div class="card-header text-primary"><h5><%= rs.getString(2) %></h5></div>
  	<div class="card-body">
    <p class="card-text"><%=rs.getString(3) %></p>
   	</div>
   	<p class="card-text text-right"><small class="text-muted"><%= rs.getString(4)%></small></p>
   	
  	<%
  		if(FLAG==1){
  	%>		
  		<hr />
  		<div> <button type="button" class="btn btn-danger delete-message" id="<%=rs.getInt(1)%>">Delete</button></div>	
  	<%
  		}
  	%>
   	
   	
</div>

    			
<%   
    }
    con.close();
}
catch (Exception e){
    e.printStackTrace();
}

%>