package insertion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DBUtil;

public class Insertion {

	private Connection conn;

	private String sql;
	

	public Insertion(Connection conn,String sql) {
		this.conn=conn;
		this.sql=sql;
	}	

	/**
	 * 单行插入
	 * @param tuple
	 * @return 影响行数
	 * @throws SQLException
	 */
	public int insert(Object...tuple) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		DBUtil.setParams(ps, tuple);
		int effectedRows = ps.executeUpdate();
		ps.close();
		return effectedRows;
	}
	
	/**
	 * 批量插入
	 * @param tuples
	 * @return 影响行数集
	 * @throws SQLException
	 */
	public int[] batch(List<Object[]> tuples) throws SQLException {
		PreparedStatement ps= conn.prepareStatement(sql);
		for (Object[] tuple : tuples) {
			DBUtil.setParams(ps, tuple);
			ps.addBatch();
		}
		int[] executeBatch = ps.executeBatch();
		ps.close();
		return executeBatch;
	}
}
