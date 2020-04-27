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
 * Servlet implementation class BushoEditServlet
 */
@WebServlet("/BushoEditServlet")
public class BushoEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BushoEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=Windows-31J");
		String bushoId = request.getParameter("bushoId");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String pass = "kiso";

		String sql = "select \n" +
				"MB.BUSHO_ID ID \n" +
				", MB.BUSHO_NAME NAME \n" +
				"from \n" +
				"MS_BUSHO MB \n"+
				"where 1=1 \n" +
				"and MB.BUSHO_ID = '" + bushoId + "'";

		List<Busho> bushoList = new ArrayList<>();

		try (
				Connection con = DriverManager.getConnection(url, user, pass);
				Statement stmt = con.createStatement();
				ResultSet rs1 = stmt.executeQuery(sql);) {
			while (rs1.next()) {
				Busho busho = new Busho();
				busho.setBushoId(rs1.getString("ID"));
				busho.setBushoName(rs1.getString("NAME"));
				bushoList.add(busho);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);
		}

		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(bushoList));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bushoId = request.getParameter("bushoId");
		String bushoName = request.getParameter("bushoName");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバーのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}

		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String password = "kiso";

		String deleteSql = "delete MS_BUSHO where BUSHO_ID = '" + bushoId + "'";
		String insertSql = "INSERT into MS_BUSHO \n" +
				"(BUSHO_ID, BUSHO_NAME) \n" +
				"values ('" + bushoId + "','" + bushoName + "')";
		try (
				Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();) {
			@SuppressWarnings("unused")
			int resultCount1 = stmt.executeUpdate(deleteSql);
			@SuppressWarnings("unused")
			int resultCount2 = stmt.executeUpdate(insertSql);

		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実行中にエラーが発生しました。詳細:[%s]", e.getMessage()), e);
		}
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString("ok"));
	}

}
