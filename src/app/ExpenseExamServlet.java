package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ExpenseExamServlet
 */
@WebServlet("/ExpenseExamServlet")
public class ExpenseExamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExpenseExamServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=Windows-31J");
		String expenseId = request.getParameter("expenseId");
		String status = request.getParameter("status");
		String reason = request.getParameter("reason");
		System.out.println(expenseId);
		System.out.println(status);
		System.out.println(reason);

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String ymd = format.format(date);

		HttpSession session = request.getSession(true);
		String role = (String) session.getAttribute("role");
		String loginId = (String) session.getAttribute("loginId");
		System.out.println(role);
		System.out.println(loginId);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバーのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String password = "kiso";

		String sql = "update MS_KEIHI set UPDATE_YMD = '" + ymd + "', UPDATE_USER_ID = '" + loginId + "' ,STATUS = '" + status + "'";
		if (reason != null) {
			sql += ", DESCRIPTION = '" + reason + "'";
		}
		sql += "  where EXPENSE_ID = '" + expenseId + "'";

		try (Connection con = DriverManager.getConnection(url, user, password); Statement stmt = con.createStatement();) {
			@SuppressWarnings("unused")
			int resultCount = stmt.executeUpdate(sql);

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実行中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
		}
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

}
