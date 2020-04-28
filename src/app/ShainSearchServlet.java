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
 * Servlet implementation class ShainSearchServlet
 */
@WebServlet("/ShainSearchServlet")
public class ShainSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShainSearchServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=Windows-31J");
		System.out.println("社員検索");
		String shainId = request.getParameter("shainId");
		System.out.println(shainId);
		String shainName = request.getParameter("shainName");
		System.out.println(shainName);
		String bushoName = request.getParameter("bushoName");
		System.out.println(bushoName);

		try { //JDBCの準備
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String pass = "kiso";

		List<Shain> shainList = new ArrayList<>();

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
				"and MS.SHAIN_BUSHOID = MB.BUSHO_ID \n";

		if (!("".equals(shainId))) {
			sql += "and MS.SHAIN_ID = '" + shainId + "' \n";
		}
		if (!("".equals(shainName))) {
			sql += "and MS.SHAIN_NAME like '%" + shainName + "%' \n";
		}
		if (!("".equals(bushoName))) {
			sql += "and MB.BUSHO_NAME = '" + bushoName + "' \n";
		}
		sql += "order by MB.BUSHO_ID";

		System.out.println(sql);
		try ( // データベースへ接続します 
				Connection con = DriverManager.getConnection(url, user, pass); // SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement(); // SQLの命令文を実行し、その結果をResultSet型のrsに代入します 
				ResultSet rs1 = stmt.executeQuery(sql);) {
			// SQL実行後の処理内容 
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
