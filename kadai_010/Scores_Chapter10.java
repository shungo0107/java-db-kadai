package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	public static void main(String[] args) {
		
		Connection con=null;
		Statement statement = null;

		try {
			
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/challenge_java",
					"root",
					"BlackJack@3604"
			);			
			System.out.println("データベース接続成功：" + con);
		
		// データ更新　ここから
			System.out.println("レコード更新を実行します");	

	        statement = con.createStatement();
			String sql = "UPDATE scores SET score_math='95',score_english='80' WHERE id='5'";
			
			int rowCnt = 0;
			rowCnt = statement.executeUpdate(sql);
			System.out.println(rowCnt + "件のレコードが更新されました");	
		// データ更新　ここまで
			
		//データ検索　ここから
			sql = "SELECT * FROM scores ORDER BY score_math DESC,score_english DESC;";

	        ResultSet result = statement.executeQuery(sql);

			System.out.println("数学・英語の点数が高い順に並べ替えました");				
	        
	        while (result.next()) {
	        	String id = result.getString("id");
	        	String name = result.getString("name");
	        	int scoreMath = result.getInt("score_math");
	        	int scoreEnglish = result.getInt("score_english");
	        	System.out.println(result.getRow() + "件目：生徒ID=" + id + "／氏名=" + name + "／数学=" + scoreMath + "／英語=" + scoreEnglish);
	        }
			//データ検索　ここまで
		} catch(SQLException e) {
			System.out.println("エラー発生" + e.getMessage());			
		} finally {
			if(statement != null) {
				try { statement.close(); } catch (SQLException ignore) { }
		    }
			if(con != null) {
				try { con.close(); } catch (SQLException ignore) { }
			}
		}		

	}
}
