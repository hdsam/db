package mysqlDemo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import db.DBUtil;
import insertion.Insertion;

public class TestJDBC {
	
	@Test
	public void testConnection() {
		System.out.println(DBUtil.getDefaultConnection());
	}
	
	@Test
	public static void testInsert() throws SQLException {
		Connection conn = DBUtil.getDefaultConnection();
		String sql ="insert into emp(name,age,title) values(?,?,?)";
//		Insertion is = new Insertion(conn, sql);
		for (int i = 0; i <10; i++) {
			Thread thread=new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Connection conn = DBUtil.getDefaultConnection();
						List<Object[]> tuples=new ArrayList<>();
						Insertion is = new Insertion(conn, sql);
						int len =10000;
						for (int i = 0; i < len; i++) {
							Object[]  tuple= {UUID.randomUUID().toString().substring(0, 4),i%50,UUID.randomUUID().toString().substring(16,20)};
							tuples.add(tuple);
						}
						int[] inserted = is.batch(tuples);
						System.out.println(Thread.currentThread().getName()+"   rows:"+inserted.length);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
			});
			thread.setName("thread"+i);
			thread.start();
		}     
	}
	
	public static void main(String[] args) throws Exception {
//		testInsert();
	}
	
}
