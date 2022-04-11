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
 * Servlet implementation class CreateMessage
 */
@WebServlet("/CreateMessage")
public class CreateMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//doGet(request, response);
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
		
		String head = request.getParameter("heading");
		String mess = request.getParameter("message");
		
		try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","admin");
            Statement st=con.createStatement();
            System.out.println("connection established successfully...!!");     

            ResultSet rs=st.executeQuery("select id from z_jp_messages where rownum = 1 order by id desc");
            
            
            int n_id=0;
            
            if(rs.next()) {
            	  n_id = rs.getInt(1) + 1;
            }

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO z_jp_messages(id, title, description) VALUES(?, ?, ?)");
            	
            pstmt.setInt(1, n_id);
            pstmt.setString(2, head);
            pstmt.setString(3, mess);

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
