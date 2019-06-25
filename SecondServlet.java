package gourav;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SecondServlet
 */
@WebServlet("/SecondServlet")
public class SecondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SecondServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		ServletContext context=getServletContext();
		Object obj=context.getAttribute("AccountNumber");
		String value=obj.toString();
		out.println("<html><body><center>Earnest Bank</center>");
		out.println("<form method=Post action=http://localhost:8080/bankdeposit/ThirdServlet>");
		out.println("<b>click the deposit button to deposit your money</b>");
		out.println("<table><tr><td>Account no:</td><td>"+value+"</td></tr>");
		out.println("<tr><td>cheque no:</td><td><input type=text name=chaquename></td></tr>");
		out.println("<tr><td>amount</td><td><input type=text name=amount value=0></td></tr></table>");
		out.println("<input type=submit value=deposit>");
		out.println("</form></body></html>");
	}

	}

