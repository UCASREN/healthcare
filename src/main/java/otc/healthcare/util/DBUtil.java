package otc.healthcare.util;

import java.nio.channels.DatagramChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
	private Connection conn;
	private ResultSet resultSet = null;
	private PreparedStatement pstmt = null;
	private int count = 0;

	public DBUtil(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 查询方法
	 * 
	 * @param sql查询sql语句
	 * @return resultSet
	 */
	@SuppressWarnings("finally")
	public ResultSet query(String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
			/** 查询 */
			resultSet = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return resultSet;
		}
	}

	/**
	 * 更新数据
	 * 
	 * @param sql
	 *            更新sql语句
	 * @return
	 */
	public int update(String sql) {
		try {
			pstmt = conn.prepareStatement(sql);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行更新出错了");
		}

		return count;

	}

	public void close() {
		try {
			if (null != resultSet) {
				resultSet.close();
			}

			if (null != pstmt) {
				pstmt.close();
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean execute(String sql) {
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean execute(String sql,List<String> data) {
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<data.size(); i++){
				pstmt.setString(i+1, data.get(i));
			}
			return  pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<String> showListResults(String sql) {
		List<String> results = new ArrayList<String>();
		try {
			ResultSet res = query(sql);
			while (res.next()) {
				results.add(res.getString(1));
			}
			res.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//this.close();
		}
		
		return results;
	}

}
