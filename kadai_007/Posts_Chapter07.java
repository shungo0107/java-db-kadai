package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		
		Connection con=null;
		Statement statement = null;
		PreparedStatement pstatement = null;
		
        String[][] addList = {
                { "1003", "2023-02-08", "昨日の夜は徹夜でした・・","13"},
                { "1002", "2023-02-08", "お疲れ様です！","12"},
                { "1003", "2023-02-09", "今日も頑張ります！","18"},
                { "1001", "2023-02-09", "無理は禁物ですよ！","17"},
                { "1002", "2023-02-10", "明日から連休ですね！","20"}
            };

		try {
			
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/challenge_java",
					"root",
					"BlackJack@3604"
			);			
			System.out.println("データベース接続成功：" + con);
		
		// データ追加　ここから
	        String sql = "INSERT INTO posts (user_id,posted_at,post_content,likes) VALUES (?, ?, ?, ?);";
	        pstatement = con.prepareStatement(sql);

			System.out.println("レコード追加を実行します");	       
	       
			int rowCnt = 0;
			for (int i = 0; i < addList.length ; i++) {
                pstatement.setString(1, addList[i][0]); 
                pstatement.setString(2, addList[i][1]); 
                pstatement.setString(3, addList[i][2]); 
                pstatement.setString(4, addList[i][3]); 
 				
				rowCnt += pstatement.executeUpdate();
			}

			System.out.println(rowCnt + "件のレコードが追加されました");				

			sql = null;
			// データ追加　ここまで
			
			//データ検索　ここから
			statement = con.createStatement();
			sql = "select * from posts where user_id = '1002';";

	        ResultSet result = statement.executeQuery(sql);

			System.out.println("ユーザーIDが1002のレコードを検索しました");				
	        
	        while (result.next()) {
	        	Date postedAt = result.getDate("posted_at");
	        	String postContent = result.getString("post_content");
	        	int likesCnt = result.getInt("likes");
	        	System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + likesCnt);
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
