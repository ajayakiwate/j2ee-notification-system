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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();

		LoginJSON J=new LoginJSON();
		
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String emailid = request.getParameter("emailid");
		String mobile = request.getParameter("mobilenumber");
		String dob = request.getParameter("dob");
		String password = request.getParameter("password1");
		
		try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","admin");
            Statement st=con.createStatement();
            System.out.println("connection established successfully...!!");     

            ResultSet rs=st.executeQuery("select id from z_jp_user where rownum = 1 order by id desc");
            
            
            int n_id=0;
            
            if(rs.next()) {
            	  n_id = rs.getInt(1) + 1;
            }

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO z_jp_user(id, fname, lname, emailid, mobile, dob, password) VALUES(?, ?, ?, ?, ?, ?, ?)");
            	
            pstmt.setInt(1, n_id);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, emailid);
            pstmt.setString(5, mobile);
            pstmt.setString(6, dob);
            pstmt.setString(7, password);

            int i = pstmt.executeUpdate();
            
            if(i==1) {
            	J.success=true;
            }

            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
		
		out.print(new Gson().toJson(J));
		out.flush();
	}

}
