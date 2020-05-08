package shain;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ShainAllListup
 */
@WebServlet("/ShainAllListupServlet")
public class ListupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListupServlet() {
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
		System.out.println("全社員表示");

		HttpSession session = request.getSession(true);
		String role = (String) session.getAttribute("role");
		String loginId = (String) session.getAttribute("loginId");
		System.out.println(role);
		System.out.println(loginId);

		PrintWriter pw = response.getWriter();

		if (role == null) {
			pw.append(new ObjectMapper().writeValueAsString(role));
		} else {
			try { // JDBCの準備
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
			}
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kiso";
			String pass = "kiso";

			String sql = "select \n" + "MS.SHAIN_ID ID \n" + ", MS.SHAIN_NAME NAME \n" + ", MS.SHAIN_AGE AGE \n" + ", MS.SHAIN_SEX SEX \n"
					+ ", MS.SHAIN_POSTCD POSTCD \n" + ", MS.SHAIN_PREFECTURE PRE \n" + ", MS.SHAIN_ADDRESS ADDR \n" + ", MB.BUSHO_NAME BUSHONAME \n"
					+ "from \n" + "MS_SHAIN MS \n" + ", MS_BUSHO MB \n" + "where 1=1 \n" + "and MS.SHAIN_BUSHOID = MB.BUSHO_ID \n"
					+ "order by MS.SHAIN_ID ";
			Map<String, Object> data = new HashMap<>();
			data.put("loginId", loginId);
			data.put("role", role);
			List<Shain> shainList = new ArrayList<>();
			try (Connection con = DriverManager.getConnection(url, user, pass);
					Statement stmt = con.createStatement();
					ResultSet rs1 = stmt.executeQuery(sql);) {
				while (rs1.next()) {
					Shain shain = new Shain();
					shain.setShainId(rs1.getString("ID"));
					shain.setShainName(rs1.getString("NAME"));
					shain.setBushoName(rs1.getString("BUSHONAME"));
					/*
					 * shain.setShainAge(rs1.getInt("SHAIN_AGE"));
					 * shain.setShainPostCd(rs1.getString("SHAIN_POSTCD"));
					 * shain.setShainPrefecture(rs1.getString("SHAIN_PREFECTURE"
					 * ));
					 * shain.setShainAddress(rs1.getString("SHAIN_ADDRESS"));
					 */
					shainList.add(shain);

				}

			} catch (Exception e) {
				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);
			}
			data.put("shainList", shainList);
			pw.append(new ObjectMapper().writeValueAsString(data));
		}
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
