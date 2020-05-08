package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.DatabaseConnection;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession(true);
		String loginId = (String) session.getAttribute("loginId");
		String role = (String) session.getAttribute("role");
		Map<String, Object> data = new HashMap<>();
		data.put("loginId", loginId);
		data.put("role", role);
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(data));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("ログインサーブレット POST");
		String loginRequest = request.getParameter("loginRequest");
		String inputLoginId = request.getParameter("loginId");
		String inputPassword = request.getParameter("password");

		HttpSession session = request.getSession(true);
		String loginId = (String) session.getAttribute("loginId");
		String role = (String) session.getAttribute("role");
		Map<String, Object> data = new HashMap<>();
		PrintWriter pw = response.getWriter();

		if (role == null) {
			System.out.println("ログイン");
			DatabaseConnection.driverLoad();
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kiso";
			String pass = "kiso";

			String safetyPassword = PasswordUtil.getSafetyPassword(inputPassword, inputLoginId);

			String sql = "select \n" + "SHAIN_ID ID, \n" + "PASSWORD PASS,\n" + "ROLE ROLE \n" + "from MS_LOGIN \n" + "where SHAIN_ID = '"
					+ inputLoginId + "' and PASSWORD = '" + safetyPassword + "'";

			try (Connection con = DriverManager.getConnection(url, user, pass);
					Statement stmt = con.createStatement();
					ResultSet rs1 = stmt.executeQuery(sql);) {
				while (rs1.next()) {
					String newLoginId = rs1.getString("ID");
					session.setAttribute("loginId", newLoginId);
					String newRole = rs1.getString("ROLE");
					session.setAttribute("role", newRole);
				}
			} catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);
			}
			role = (String) session.getAttribute("role");
			loginId = (String) session.getAttribute("loginId");
			data.put("loginId", loginId);
			data.put("role", role);
			pw.append(new ObjectMapper().writeValueAsString(data));
		} else {
			if (loginRequest != null && loginRequest.equals("logout")) {
				System.out.println("ログアウト");
				session.removeAttribute("loginId");
				session.removeAttribute("role");
				pw.append(new ObjectMapper().writeValueAsString("ログアウト完了"));
			}
		}

		System.out.println(data.get("loginId"));
		System.out.println(data.get("role"));
	}

}
