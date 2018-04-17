package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

	private static Properties properites=new Properties();;
	private static Connection connection;

	static {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("jdbcConfig.property");
			properites.load(is);
			Class.forName(properites.getProperty("jdbc.driver"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return Connection
	 * 获得从配置文件加载的数据库连接
	 */
	public static Connection getDefaultConnection() {
		return getConnection(properites.getProperty("jdbc.url"),
							 properites.getProperty("jdbc.user"),
							 properites.getProperty("jdbc.password"));
	}
	
	/**
	 * @param url
	 * @param user
	 * @param password
	 * @return Connection
	 * 获得指定的数据库连接
	 */
	public static Connection getConnection(String url,String user,String password) {
		try {
			connection= DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * @param params
	 * @throws SQLException
	 * 设置sql参数
	 */
	public static PreparedStatement setParams(PreparedStatement ps,Object...tuple) throws SQLException {
		if(ps==null|tuple==null||tuple.length<=0) {
			throw new IllegalArgumentException("参数异常");
		}
		for (int i = 0; i < tuple.length; i++) {
			ps.setObject(i+1, tuple[i]);
		}
		return ps;
	}
	
	
	/**
	 * 关闭数据库连接
	 * @param connection
	 */
	public static void closeConnection(Connection connection) {
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
