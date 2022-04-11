package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class VerifyLogin
 */
@WebServlet("/VerifyLogin")
public class VerifyLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();

		LoginJSON J=new LoginJSON();
		
	
		String eid = request.getParameter("emailid");
		String pwd = request.getParameter("password");
		String rme=  request.getParameter("remember");
		
		try
		{
		    Class.forName("oracle.jdbc.driver.OracleDriver");
		    Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","admin");
		    Statement st=con.createStatement();
		    System.out.println("connection established successfully...!!");     
		    ResultSet rs=st.executeQuery("Select id from z_jp_user where emailid='"+eid+"' AND password='"+pwd+"'");

		    while(rs.next())
		    {
		    	J.success=true;
		    }
		        
		    con.close();
	    }
	    catch (Exception e){
	        e.printStackTrace();
	    }
	
		//admin with remember me
		if(eid.equals("a@g.c") && pwd.equals("a") && rme!=null ) {
			
			Cookie ck1=new Cookie("c_emailid", "a@g.c");  
	        response.addCookie(ck1);  
	        
	        Cookie ck2=new Cookie("c_admin", "true");  
	        response.addCookie(ck2);
	        
	        ck1.setMaxAge(3600*24*365);	   
	        ck2.setMaxAge(3600*24*365);
	        
	        HttpSession session=request.getSession();  
	        session.setAttribute("s_emailid", "a@g.c");
	        session.setAttribute("s_admin","true"); 
	        
	        J.success=true;
		}//admin
		else if(eid.equals("a@g.c") && pwd.equals("a")) {
			

	        HttpSession session=request.getSession();  
	        session.setAttribute("s_emailid", "a@g.c");
	        session.setAttribute("s_admin","true"); 
	        
	        J.success=true;
	        
		}//user with remember me
		else if(J.success && rme!=null ) {

			Cookie ck1=new Cookie("c_emailid", eid);  
	        response.addCookie(ck1);  
	        ck1.setMaxAge(3600*24*365);	   
	        
	        HttpSession session=request.getSession();  
	        session.setAttribute("s_emailid", eid); 
	        
		}//user
		else if(J.success) {

	        HttpSession session=request.getSession();  
	        session.setAttribute("s_emailid", eid); 
	        
		}//invalid user
		

		out.print(new Gson().toJson(J));
		out.flush();
		
	}

}

