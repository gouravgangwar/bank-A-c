package gourav;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FourthServlet1
 */
@WebServlet("/FourthServlet1")
public class FourthServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FourthServlet1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection dbcon=null;
		ServletContext context=getServletContext();
		Object obj=context.getAttribute("AccountNumber");
		String accnum=obj.toString();
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		out.println("<html><body><h1>Last 20 transactions...</h1>");
		int totalrow=0;
		
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
		try
		{	String query="select count(*) from account_tr where account_no=?";
			PreparedStatement s1=dbcon.prepareStatement(query);
			s1.setInt(1,Integer.parseInt(accnum));
			ResultSet r=s1.executeQuery();
			r.next();
			totalrow=r.getInt(1);
		//	int norow=totalrow;
			if(totalrow >20)
				totalrow -=20;
			else
				totalrow=0;
		}
		catch(Exception e)
		{
			System.out.println("error no 1...");
			e.printStackTrace();
		}
		
		try
		{
			String query1="select * from account_tr where account_no=?";
			PreparedStatement s2=dbcon.prepareStatement(query1,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			s2.setString(1,accnum);
			ResultSet rs=s2.executeQuery();
			out.println("welcome user...."+accnum);
			out.println("<br><br>");
			rs.absolute(totalrow +1);
			out.println(rs.getString(2)+"\t\t\t\t"+rs.getString(3)+"\t\t\t\t"+rs.getString(4)+"\t\t\t\t"+rs.getDouble(5));
			out.println("<br><br>");
			while(rs.next())
				{
				out.println(rs.getString(2)+"\t\t\t\t"+rs.getString(3)+"\t\t\t\t"+rs.getString(4)+"\t\t\t\t"+rs.getDouble(5));
				out.println("<br><br>");
				}
			out.println("</body></html>");
			out.close();
			dbcon.close();
			}
		catch(Exception e)
		{
			System.out.println("error no ...2");
		}
		
	}

}
