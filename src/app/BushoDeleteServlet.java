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
 * Servlet implementation class BushoDeleteServlet
 */
@WebServlet("/BushoDeleteServlet")
public class BushoDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BushoDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bushoId = request.getParameter("bushoId");
		System.out.println("delete:" + bushoId);

		try { //JDBCの準備
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String password = "kiso";

		String deleteSql = "delete MS_BUSHO \n" +
				"where BUSHO_ID = '" + bushoId + "'";

		try (
				Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();) {
			@SuppressWarnings("unused")
			int resultCount = stmt.executeUpdate(deleteSql);
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実行中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
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
