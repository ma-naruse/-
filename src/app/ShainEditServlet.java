package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ShainEditServlet
 */
@WebServlet("/ShainEditServlet")
public class ShainEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShainEditServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=Windows-31J");
		String shainId = request.getParameter("shainId");
		System.out.println("GET:"+shainId);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String pass = "kiso";

		String sql = "select \n" +
				"MS.SHAIN_ID ID \n" +
				", MS.SHAIN_NAME NAME \n" +
				", MS.SHAIN_AGE AGE \n" +
				", MS.SHAIN_SEX SEX \n" +
				", MS.SHAIN_POSTCD POSTCD \n" +
				", MS.SHAIN_PREFECTURE PRE \n" +
				", MS.SHAIN_ADDRESS ADDRESS \n" +
				", MB.BUSHO_NAME BN \n" +
				"from \n" +
				"MS_SHAIN MS \n" +
				", MS_BUSHO MB \n" +
				"where 1=1 \n" +
				"and MS.SHAIN_BUSHOID = MB.BUSHO_ID \n" +
				"and MS.SHAIN_ID = '" + shainId + "'";

		List<Shain> shainList = new ArrayList<>();

		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				Statement stmt = con.createStatement();
				ResultSet rs1 = stmt.executeQuery(sql);) {
			while (rs1.next()) {
				Shain shain = new Shain();
				shain.setShainId(rs1.getString("ID"));
				shain.setShainName(rs1.getString("NAME"));
				shain.setShainAge(rs1.getInt("AGE"));
				shain.setShainSex(rs1.getString("SEX"));
				shain.setShainPostCd(rs1.getString("POSTCD"));
				shain.setShainPrefecture(rs1.getString("PRE"));
				shain.setShainAddress(rs1.getString("ADDRESS"));
				shain.setBushoName(rs1.getString("BN"));
				shainList.add(shain);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(shainList));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String shainId = request.getParameter("shainId");
		String shainName = request.getParameter("shainName");
		String shainAge = request.getParameter("shainAge");
		String shainSex = request.getParameter("shainSex");
		String shainPostCd = request.getParameter("shainPostCd");
		String shainPrefecture = request.getParameter("shainPrefecture");
		String shainAddress = request.getParameter("shainAddress");
		String bushoName = request.getParameter("bushoName");
		System.out.println("POST:"+shainName);
		System.out.println("POST:"+shainId);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバーのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String password = "kiso";

		String sqlForBushoId = "select BUSHO_ID from MS_BUSHO MB where MB.BUSHO_NAME = '" + bushoName + "'";
		String bushoId = "";
		try (
				Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs1 = stmt.executeQuery(sqlForBushoId);) {
			while (rs1.next()) {
				bushoId = rs1.getNString(1);
				System.out.println(bushoId);
			}

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実行中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
		}
		String deleteSql = "delete MS_SHAIN where SHAIN_ID = '" + shainId + "'";
		String insertSql = "INSERT into MS_SHAIN \n" +
				"(SHAIN_ID, SHAIN_NAME, SHAIN_AGE, SHAIN_SEX, SHAIN_POSTCD, \n" +
				"	SHAIN_PREFECTURE, SHAIN_ADDRESS, SHAIN_BUSHOID) \n" +
				"values ('" + shainId + "','" + shainName + "','" + shainAge + "','" + shainSex + "','" + shainPostCd
				+ "','" + shainPrefecture + "','" + shainAddress + "','" + bushoId + "')";
		String commitSql = "commit";

		try (
				Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();) {
			@SuppressWarnings("unused")
			int resultCount1 = stmt.executeUpdate(deleteSql);
			@SuppressWarnings("unused")
			int resultCount2 = stmt.executeUpdate(insertSql);
			@SuppressWarnings("unused")
			int resultCount3 = stmt.executeUpdate(commitSql);

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実行中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
		}
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

}