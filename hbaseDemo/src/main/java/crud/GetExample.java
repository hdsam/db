package crud;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetExample {

	private Connection conn;
	
	@Before
	public void setUp() throws IOException {
		//读取配置文件并获得连接
		 System.setProperty("HADOOP_USER_NAME", "hadoop");
		Configuration conf=HBaseConfiguration.create();
		conn = ConnectionFactory.createConnection(conf);
	}
	
	@Test
	public void testGet() throws IOException {
		//得到表对象
		Table table = conn.getTable(TableName.valueOf("testtable"));
		
		//通过rowkey实例化一个Get对象
		Get get = new Get(Bytes.toBytes("row1"));
		//添加查询的列族和列信息（或版本数）
		get.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"));
		//执行
		Result result = table.get(get);
		//得到值
		byte[] value = result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"));
		System.out.println("value of the cell {row1,colfam1,qual1} :"+Bytes.toString(value));
		
		table.close();
	}
	
	@After
	public void destory() throws IOException {
		//关闭连接
		conn.close();
	}
	
	
}
