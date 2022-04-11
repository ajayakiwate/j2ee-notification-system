package p1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class VerifyUser
 */
@WebServlet("/VerifyUser")
public class VerifyUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
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

		HttpSession session=request.getSession(false);  
		
        if(session!=null){  
        	/*
        	String s_emailid = (String)session.getAttribute("s_emailid");
        	//s_emailid = s_emailid.replaceFirst("*","@");
        	
        	J.message.log += " s1 ";
        	
        	if( !s_emailid.equals("") ){
        		J.success=true;
        		J.message.session=true;
        		J.message.emailid = s_emailid;
        		J.message.log += " s2 ";
        	}
        
        	
        	String s_admin = (String)session.getAttribute("s_admin");
      
        	if( s_admin.equals("true") ){
        		J.success=true;
        		J.message.session=true;
        		J.message.admin=true;
        		J.message.log += " s3 ";
        	}
        	*/
        	
        	Enumeration<String> keys = session.getAttributeNames();
        	
        	while (keys.hasMoreElements()){
        		        	             	   
        	   String name = (String)keys.nextElement();
        	   String value = (String)session.getAttribute(name);
        	   
        	   if( name.equals("s_emailid") && !value.equals("") ){
	           		J.success=true;
	           		J.message.session=true;
	           		J.message.emailid = value;
	           		//J.message.log += " s2 ";
           		}
        	   	else if( name.equals("s_admin") && value.equals("true") ){
	           		J.success=true;
	           		J.message.session=true;
	           		J.message.admin=true;
	           		//J.message.log += " s3 ";
	           	}
        	              	   
        	}
        	
        	
        }
       
		
        Cookie ck[]=request.getCookies();  
		
		
        if( ck != null ){  
        	
        /*
        	String c_admin_value=ck[2].getValue(); 
        	String c_admin_name=ck[2].getName();
        	
        	c_admin_value = c_admin_value.replaceAll("\\s", "");
        	c_admin_name = c_admin_name.replaceAll("\\s", "");
        	
        	String c_user_value=ck[1].getValue(); 
        	String c_user_name=ck[1].getName();
        	
        	c_user_value = c_user_value.replaceAll("\\s", "");
        	c_user_name = c_user_name.replaceAll("\\s", "");
        	
        	J.message.log += " c1 ";
        	
        	if( c_admin_name.equals("c_admin") && c_admin_value.equals("true") ){
        		J.success=true;
        		J.message.admin=true;
        		J.message.cookie=true;
        		J.message.log += " c2 ";
        	}
        	J.message.log += " c2 "+c_admin_name+"="+c_admin_value;
        	
        	if( c_user_name.equals("c_emailid") && !c_user_value.equals("") ){
        		J.success=true;
        		J.message.cookie=true;
        		J.message.emailid=c_user_value;
        		J.message.log += " c3 ";
        	}
        	
        	*/        	
        	for(int i=0; i < ck.length; i++) {
        		
        		String name = ck[i].getName();
        		String value = ck[i].getValue();
        		
        		if( name.equals("c_admin") && value.equals("true") ){
            		J.success=true;
            		J.message.admin=true;
            		J.message.cookie=true;
            		//J.message.log += " c2 ";
            	}
        		else if( name.equals("c_emailid") && !value.equals("") ){
            		J.success=true;
            		J.message.cookie=true;
            		J.message.emailid=value;
            		//J.message.log += " c3 ";
            	}
        		
        	}
        	
        	
        	
        } 
        
        out.print(new Gson().toJson(J));
		out.flush();
        
        
	}

}
