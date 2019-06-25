package gourav;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ThirdServlet
 */
@WebServlet("/ThirdServlet")
public class ThirdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 String accountnumber;
     Connection dbcon;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThirdServlet() {
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
			System.out.println("error 1");
		}
		ServletContext context=getServletContext();
		Object obj=context.getAttribute("AccountNumber");
		String accnum=obj.toString();
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		out.println("<html><body>");
		String amount=request.getParameter("amount");
		String chequenum=request.getParameter("chaquename");
		SimpleDateFormat ob=new SimpleDateFormat("dd/MM/yyyy");
		String date=ob.format(new Date());
		Double temp=Double.valueOf(amount);
		Double mdeposit=temp.doubleValue();
		String vparticular=new String("cheque deposit");
		PreparedStatement pstmt=null;
		try
		{		
			String query="insert into Account_tr value(?,?,?,?,?)";
			pstmt=dbcon.prepareStatement(query);
			pstmt.setInt(1,Integer.parseInt(accnum));
			pstmt.setString(2,date);
			pstmt.setString(3,vparticular);
			pstmt.setString(4,chequenum);
			pstmt.setDouble(5,mdeposit);
			
			int rows=pstmt.executeUpdate();
			if(rows==0)
			{
				System.out.println("error in inserting");
			}
			else
			{
				out.println("your transaction record has been recorded");
				out.println("<br>");
				out.println("click below to view last 20 transaction");
				out.println("<form method=post action=http://localhost:8080/bankdeposit/FourthServlet1>");
				out.println("<table><tr><td>");
				out.println("<input type=submit value=last_few_transactions></td>");
				out.println("</tr></table><form></body></html>");
			}
		}
		catch(Exception eee)
		{
			eee.printStackTrace();
		}

	}

}
