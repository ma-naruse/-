package busho;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.DatabaseConnection;

/**
 * Servlet implementation class BushoDeleteServlet
 */
@WebServlet("/BushoDeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=Windows-31J");
		String bushoId = request.getParameter("bushoId");
		DatabaseConnection.driverLoad();
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String pass = "kiso";
		String sql = "delete from MS_BUSHO where BUSHO_ID ='" + bushoId + "'";
		DatabaseConnection.executeSql(url, user, pass, sql);
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
