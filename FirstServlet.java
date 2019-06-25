package gourav;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Connection dbcon=null;
	static String resulttosecond="NA"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			dbcon=DriverManager.getConnection("jdbc:mysql://localhost:3306/gourav","root","6552");
		}
		catch(ClassNotFoundException ce)
		{
			System.out.println("Driver not found");
		}
		catch(Exception e)
		{
			System.out.println("error no 1");
		}
		ServletContext context=getServletContext();
		context.setAttribute("AccountNumber","");
		String accnum=request.getParameter("acno");
		String pin=request.getParameter("pin");
		
		try
		{
			ResultSet rs=null;
			PreparedStatement pstmt=null;
			String query="select * from login_table where Account_no=? and Pin=?";
			pstmt=dbcon.prepareStatement(query);
			pstmt.setString(1,accnum);
			pstmt.setString(2,pin);
			rs=pstmt.executeQuery();
			boolean row=false;
			row=rs.next();
			if(row==true)
			{
				PrintWriter out=response.getWriter();
				response.setContentType("text/html");
				resulttosecond=rs.getString(1);
				context.setAttribute("AccountNumber",resulttosecond);
				RequestDispatcher dispatcher=context.getRequestDispatcher("/SecondServlet");
				
				if(dispatcher==null)
				{	
					out.println("hey....i am...");
					response.sendError(response.SC_NO_CONTENT);
				}
				dispatcher.forward(request,response);
				try
				{
					dbcon.close();
				}
				catch(Exception e)
				{
					System.out.println("connection close error");
				}
			}
			
			if(row==false)
			{
				PrintWriter out=response.getWriter();
				response.setContentType("text/html");
				resulttosecond="NA";
				out.println("<html><body>please check the value that you have entered");
				out.println("</body></html>");
				out.close();
			}
		}
		catch(SQLException e)
		{
			System.out.println(e.toString());
		}
		
	}
	}

