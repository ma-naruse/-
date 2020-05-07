package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		// System.out.println("ログインサーブレット GET");
		HttpSession session = request.getSession(true);
		String loginId = (String) session.getAttribute("loginId");
		String role = (String) session.getAttribute("role");
		// System.out.println(loginId);
		// System.out.println(role);
		LoginInformation info = new LoginInformation();
		info.setLoginId(loginId);
		info.setRole(role);
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(info));
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
		// System.out.println(inputLoginId);
		// System.out.println(inputPassword);
		// System.out.println(loginRequest);

		HttpSession session = request.getSession(true);
		String loginId = (String) session.getAttribute("loginId");
		String role = (String) session.getAttribute("role");
		System.out.println("role:" + role);
		LoginInformation info = new LoginInformation();
		PrintWriter pw = response.getWriter();

		if (role == null) {
			System.out.println("ログイン");
			try { // JDBCの準備
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
			}
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kiso";
			String pass = "kiso";

			String sql = "select \n" + "SHAIN_ID ID, \n" + "PASSWORD PASS,\n" + "ROLE ROLE \n" + "from MS_LOGIN \n" + "where SHAIN_ID = '"
					+ inputLoginId + "' and PASSWORD = '" + inputPassword + "'";

			try (Connection con = DriverManager.getConnection(url, user, pass);
					Statement stmt = con.createStatement();
					ResultSet rs1 = stmt.executeQuery(sql);) {

				while (rs1.next()) {
					String newLoginId = rs1.getString("ID");
					System.out.println(newLoginId);
					String newRole = rs1.getString("ROLE");
					System.out.println(newRole);
					session.setAttribute("loginId", newLoginId);
					session.setAttribute("role", newRole);
				}

			} catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);
			}
			role = (String) session.getAttribute("role");
			loginId = (String) session.getAttribute("loginId");
			info.setLoginId(loginId);
			info.setRole(role);
			pw.append(new ObjectMapper().writeValueAsString(info));
		} else {
			if (loginRequest != null && loginRequest.equals("logout")) {
				System.out.println("ログアウト");
				session.removeAttribute("loginId");
				session.removeAttribute("role");
				pw.append(new ObjectMapper().writeValueAsString("ログアウト完了"));
			}
		}

		System.out.println(info.getLoginId());
		System.out.println(info.getRole());
	}

}
