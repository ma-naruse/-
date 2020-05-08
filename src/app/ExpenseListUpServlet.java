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
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class ExpenseListUpServlet
 */
@WebServlet("/ExpenseListUpServlet")
public class ExpenseListUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExpenseListUpServlet() {
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
		response.setContentType("text/html; charset=Windows-31J");
		System.out.println("全経費表示");

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

			String sql = "select   \n" + "MS.SHAIN_ID SHAIN_ID,  \n" + "MK.EXPENSE_ID ID,   \n" + "MK.EXPENSE_YMD YMD,  \n"
					+ "MK.UPDATE_YMD UPDATEYMD,  \n" + "MU.SHAIN_NAME UPDATER,  \n" + "MK.ITEMNAME NAME,  \n" + "MK.PRICE PRICE,  \n"
					+ "MK.STATUS STATUS,  \n" + "MS.SHAIN_NAME INPUTER, \n" + "MK.DESCRIPTION DESCRIPTION \n" + "from MS_KEIHI MK,  \n"
					+ "MS_SHAIN MS ,  \n" + "MS_SHAIN MU  \n" + "where MK.INPUT_USER_ID = MS.SHAIN_ID  \n"
					+ "and MU.SHAIN_ID(+) = MK.UPDATE_USER_ID  \n" + "order by EXPENSE_ID ";
			ExpenseListInformation info = new ExpenseListInformation();
			info.setLoginId(loginId);
			info.setRole(role);
			List<Expense> expenseList = new ArrayList<>();
			try (Connection con = DriverManager.getConnection(url, user, pass);
					Statement stmt = con.createStatement();
					ResultSet rs1 = stmt.executeQuery(sql);) {
				while (rs1.next()) {
					Expense expense = new Expense();
					expense.setExpenseId(rs1.getString("ID"));
					expense.setDate(rs1.getString("YMD"));
					expense.setUpdateDate(rs1.getString("UPDATEYMD"));
					expense.setUpdateUser(rs1.getString("UPDATER"));
					expense.setItemName(rs1.getString("NAME"));
					expense.setPrice(rs1.getInt("PRICE"));
					expense.setStatus(rs1.getString("STATUS"));
					expense.setInputUserName(rs1.getString("INPUTER"));
					expense.setInputUserId(rs1.getString("SHAIN_ID"));
					expense.setDescription(rs1.getString("DESCRIPTION"));
					expenseList.add(expense);
				}

				// SQL実行後の処理内容

			} catch (Exception e) {

				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);

			}
			info.setExpenseList(expenseList);
			pw.append(new ObjectMapper().writeValueAsString(info));
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
