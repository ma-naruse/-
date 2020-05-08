package shain;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import app.DatabaseConnection;

/**
 * Servlet implementation class ShainAddServlet
 */
@WebServlet("/ShainAddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse *
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
		String shainId = request.getParameter("shainId");
		String shainName = request.getParameter("shainName");
		String shainAge = request.getParameter("shainAge");
		String shainSex = request.getParameter("shainSex");
		String shainPostCd = request.getParameter("shainPostCd");
		String shainPrefecture = request.getParameter("shainPrefecture");
		String shainAddress = request.getParameter("shainAddress");
		String bushoName = request.getParameter("bushoName");
		DatabaseConnection.driverLoad();

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String pass = "kiso";

		String sql_for_bushoId = "select BUSHO_ID from MS_BUSHO MB where MB.BUSHO_NAME = '" + bushoName + "'";
		String bushoId = "";
		try (Connection con = DriverManager.getConnection(url, user, pass);
				Statement stmt = con.createStatement();
				ResultSet rs1 = stmt.executeQuery(sql_for_bushoId);) {
			while (rs1.next()) {
				bushoId = rs1.getNString(1);
				System.out.println(bushoId);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実行中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
		}
		String sql = "INSERT into MS_SHAIN \n" + "(SHAIN_ID, SHAIN_NAME, SHAIN_AGE, SHAIN_SEX, SHAIN_POSTCD, \n"
				+ "	SHAIN_PREFECTURE, SHAIN_ADDRESS, SHAIN_BUSHOID) \n" + "values ('" + shainId + "','" + shainName + "','" + shainAge + "','"
				+ shainSex + "','" + shainPostCd + "','" + shainPrefecture + "','" + shainAddress + "','" + bushoId + "')";
		DatabaseConnection.executeSql(url, user, pass, sql);
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

}
