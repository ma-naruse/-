package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {

	public static void driverLoad() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("JDBCドライバのロードに失敗しました。詳細:[%s]", e.getMessage()), e);
		}
	}

	public static void executeSql(String url, String user, String pass, String sql) {
		try (Connection con = DriverManager.getConnection(url, user, pass); Statement stmt = con.createStatement();) {
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw new RuntimeException(String.format("検索処理の実施中にエラーが発生しました。	詳細:[%s]", e.getMessage()), e);
		}
	}

}
