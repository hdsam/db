package crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
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

public class GetListExample {
	private Connection conn;

	@Before
	public void setUp() throws IOException {
		// 读取配置文件并获得连接
		Configuration conf = HBaseConfiguration.create();
		conn = ConnectionFactory.createConnection(conf);
	}

	@Test
	public void testGetList() throws IOException {
		// 得到表对象
		Table table = conn.getTable(TableName.valueOf("testtable"));

		//get列表
		List<Get> gets = new ArrayList<>();

		//关键字
		byte[] row1 = Bytes.toBytes("row1");
		byte[] row2 = Bytes.toBytes("row2");
		byte[] cf1 = Bytes.toBytes("colfam1");
		byte[] qual1 = Bytes.toBytes("qual1");
		byte[] qual2 = Bytes.toBytes("qual2");

		// get1
		Get get1 = new Get(row1);
		get1.addColumn(cf1, qual1);
		gets.add(get1);

		// get2
		Get get2 = new Get(row1);
		get2.addColumn(cf1, qual2);
		gets.add(get2);

		// get3
		Get get3 = new Get(row2);
		get3.addColumn(cf1, qual1);
		gets.add(get3);

		// get4
		Get get4 = new Get(row2);
		get4.addColumn(cf1, qual2);
		gets.add(get4);

		Result[] results = table.get(gets);
		
		System.out.println("total num of resutl:"+results.length);
		//第一次遍历
		System.out.println("First iteration...");
		for (Result result : results) {
			String row = Bytes.toString(result.getRow());
			byte[] val = null;
			if (result.containsColumn(cf1, qual1)) {
				val = result.getValue(cf1, qual1);
			}
			if (result.containsColumn(cf1, qual2)) {
				val = result.getValue(cf1, qual2);
			}
			System.out.println("rowkey:" + row + "  value:" + Bytes.toString(val));
		}

		//第二次遍历
		System.out.println("Second iteration...");
		for (Result result : results) {
			for ( Cell cell: result.listCells()) {
				// 一个单元格由行和列决定，并且有多个版本的数据.
				System.out.println("rowkey :"+Bytes.toString(cell.getRowArray(),cell.getRowOffset(),cell.getRowLength())
						+"  value:"+Bytes.toString(CellUtil.cloneValue(cell)));
			}
		}
		
		//第三次遍历
		System.out.println("Third iteration...");
		for (Result result : results) {
			System.out.println(result.toString());
		}
		
		table.close();
	}

	@After
	public void destory() throws IOException {
		// 关闭连接
		conn.close();
	}

}
