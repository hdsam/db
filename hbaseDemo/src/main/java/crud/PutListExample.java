package crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PutListExample {
	Connection conn;

	@Before
	public void setUp() throws IOException {
		// 创建HBase客户端配置对象
		Configuration conf = HBaseConfiguration.create();
		// 获得数据库连接
		conn = ConnectionFactory.createConnection(conf);
	}

	@Test
	public void doPutList() throws IOException {
		// 创建表实例
		Table table = conn.getTable(TableName.valueOf("testtable"));

		// 关键字
		byte[] row1 = Bytes.toBytes("row1");
		byte[] row2 = Bytes.toBytes("row2");
		byte[] cf1 = Bytes.toBytes("colfam1");
		byte[] qual1 = Bytes.toBytes("qual1");
		byte[] qual2 = Bytes.toBytes("qual2");
		// put列表
		List<Put> puts = new ArrayList<>();

		Put put1 = new Put(row1);
		put1.addColumn(cf1,qual1,Bytes.toBytes("v1"));
		puts.add(put1);
		
		Put put2 = new Put(row1);
		put2.addColumn(cf1, qual2, Bytes.toBytes("v2"));
		puts.add(put2);
		
		Put put3 = new Put(row2);
		put3.addColumn(cf1, qual1, Bytes.toBytes("v3"));
		puts.add(put3);
		
		Put put4 = new Put(row2);
		put4.addColumn(cf1, qual2, Bytes.toBytes("v4"));
		puts.add(put4);
		
		
		// 执行put操作
		table.put(puts);
		table.close();
	}

	@After
	public void destory() throws IOException {
		conn.close();
	}
}
