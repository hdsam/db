package crud;

import java.io.IOException;

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

public class PutExample {
	// keyValue: <<rowKey,column,timestamp>,value>
	// coordinate:{rowkey,family,qulifier}-->mutiple versions data
	Connection conn;

	@Before
	public void setUp() throws IOException {
		// 创建HBase客户端配置对象
		Configuration conf = HBaseConfiguration.create();
		// 获得数据库连接
		conn = ConnectionFactory.createConnection(conf);
	}

	@Test
	public void doPut() throws IOException {
		// 创建表实例
		Table table = conn.getTable(TableName.valueOf("testtable"));
		// 创建用以单行更新的put实例
		Put put = new Put(Bytes.toBytes("row2"));
		put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("val1"));
		put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual2"), Bytes.toBytes("val2"));

		// 执行put操作
		table.put(put);
		table.close();
	}

	@After
	public void destory() throws IOException {
		conn.close();
	}

}
