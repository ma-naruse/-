package busho;

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

import app.DatabaseConnection;

/**
 * Servlet implementation class BushoAllListup
 */
@WebServlet("/BushoAllListupServlet")
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
		System.out.println("全部署表示");
		DatabaseConnection.driverLoad();
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "kiso";
		String pass = "kiso";
		String sql = "select * from MS_BUSHO order by BUSHO_ID";

		PrintWriter pw = response.getWriter();
		List<Busho> bushoList = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(url, user, pass); // SQLの命令文を実行するための準備をおこないます
				Statement stmt = con.createStatement(); // SQLの命令文を実行し、その結果をResultSet型のrsに代入します
				ResultSet rs1 = stmt.executeQuery(sql);) {
			while (rs1.next()) {
				Busho busho = new Busho();
				busho.setBushoId(rs1.getString(1));
				busho.setBushoName(rs1.getString(2));
				bushoList.add(busho);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);
		}

		pw.append(new ObjectMapper().writeValueAsString(bushoList));
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
