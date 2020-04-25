package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class BushoAdd
 */
@WebServlet("/BushoAdd")
public class BushoAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BushoAdd() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=Windows-31J");
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String bushoId = request.getParameter("bushoId");
		String bushoName = request.getParameter("bushoName");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String pass = "kiso";

		String insertSql = "INSERT into MS_BUSHO \n" +
				"(BUSHO_ID,BUSHO_NAME) \n" +
				"values ('" + bushoId + "','" + bushoName + "')";
		String commitSql = "commit";

		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				Statement stmt = con.createStatement();

		) {
			@SuppressWarnings("unused")
			int resultCount1 = stmt.executeUpdate(insertSql);
			@SuppressWarnings("unused")
			int resultCount2 = stmt.executeUpdate(commitSql);
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);
		}
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
