package otc.healthcare.dao;

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
		this.setConn(conn);
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
			pstmt = getConn().prepareStatement(sql);
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
			pstmt = getConn().prepareStatement(sql);
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

			getConn().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean execute(String sql) {
		try {
			Statement stmt = getConn().createStatement();
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return results;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
