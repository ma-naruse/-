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
 * Servlet implementation class ShainAllListup
 */
@WebServlet("/ShainAllListup")
public class ShainAllListup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShainAllListup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=Windows-31J");
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("全社員表示");
		try { //JDBCの準備
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
					", MS.SHAIN_ADDRESS ADDR \n" +
					", MB.BUSHO_NAME BN \n" +
					"from \n" +
					"MS_SHAIN MS \n" +
					", MS_BUSHO MB \n" +
					"where 1=1 \n" +
					"and MS.SHAIN_BUSHOID = MB.BUSHO_ID \n" + 
					"order by MS.SHAIN_ID ";
		
		List <Shain> shainList = new ArrayList<>();
		try ( // データベースへ接続します 
			Connection con = DriverManager.getConnection(url, user, pass);// SQLの命令文を実行するための準備をおこないます
			Statement stmt = con.createStatement();	// SQLの命令文を実行し、その結果をResultSet型のrsに代入します 
			ResultSet rs1 = stmt.executeQuery(sql);
		) {
			while(rs1.next()){
				Shain shain = new Shain();
				shain.setShainId(rs1.getString("ID"));
				shain.setShainName(rs1.getString("NAME"));
				shain.setBushoName(rs1.getString("BN"));
			/*	shain.setShainAge(rs1.getInt("SHAIN_AGE"));
				shain.setShainPostCd(rs1.getString("SHAIN_POSTCD"));
				shain.setShainPrefecture(rs1.getString("SHAIN_PREFECTURE"));
				shain.setShainAddress(rs1.getString("SHAIN_ADDRESS"));*/
				shainList.add(shain);
				
			}

				// SQL実行後の処理内容 

				} catch (Exception e) { 

				throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e); 

				} 
		
		PrintWriter pw = response.getWriter();
		pw.append(new ObjectMapper().writeValueAsString(shainList));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
