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

//检查写（check and put）,保证put操作的原子性，
public class CheckAndPutExample {
	Connection conn;

	@Before
	public void setUp() throws IOException {
		// 创建HBase客户端配置对象
		Configuration conf = HBaseConfiguration.create();
		// 获得数据库连接
		conn = ConnectionFactory.createConnection(conf);
	}

	@Test
	public void testCheckAndPut() throws IOException {
		// 创建表实例
		Table table = conn.getTable(TableName.valueOf("testtable"));
		// 关键字
		byte[] row1 = Bytes.toBytes("row1");
		byte[] row2 = Bytes.toBytes("row2");
		byte[] cf1 = Bytes.toBytes("colfam1");
		byte[] qual1 = Bytes.toBytes("qual1");
		byte[] qual2 = Bytes.toBytes("qual2");

		// 检查{row1,cf1,qual1}的值是否为空，并尝试向当前单元格写入数据
		Put put1 = new Put(row1);
		put1.addColumn(cf1, qual1, Bytes.toBytes("vv1"));
		boolean res1 = table.checkAndPut(row1, cf1, qual1, null, put1);
		System.out.println("写入成功：" + res1);

		// 检查{row2,cf1,qual2}的值是否为"v4"，并尝试向当前单元格改写数据
		Put put2 = new Put(row2);
		put2.addColumn(cf1, qual2, Bytes.toBytes("vv4"));
		boolean res2 = table.checkAndPut(row2, cf1, qual2, Bytes.toBytes("v4"), put2);
		System.out.println("写入成功：" + res2);

		// 除此之外，还可以验证某一单元格数据，然后向宁一单元格写入数据
		Put put3 = new Put(row1);
		put3.addColumn(cf1, Bytes.toBytes("qual3"), Bytes.toBytes("vv5"));
		// 验证{row1,cf1,qual2},并写入{row1,cf1,qual3}
		boolean res3 = table.checkAndPut(row1, cf1, qual2, Bytes.toBytes("v2"), put3);
		System.out.println("写入成功：" + res3);

		// 对于跨行的检查写操作，会抛出异常org.apache.hadoop.hbase.DoNotRetryIOException:Action's getRow
		// must match the passed row
		Put put4 = new Put(row2);
		put4.addColumn(cf1, Bytes.toBytes("qual3"), Bytes.toBytes("vv6"));
		// 验证{row1,cf1,qual3},并写入{row2,cf1,qual3}
		boolean res4 = table.checkAndPut(row1, cf1, Bytes.toBytes("qual3"), Bytes.toBytes("vv5"), put4);
		System.out.println("写入成功：" + res4);

		table.close();
	}

	@After
	public void destory() throws IOException {
		conn.close();
	}
}
