package crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BatchExample {

	Connection conn;

	@Before
	public void setUp() throws IOException {
		conn = ConnectionFactory.createConnection(HBaseConfiguration.create());
	}

	@Test
	public void testBatch() throws IOException, InterruptedException {
		Table table = conn.getTable(TableName.valueOf("testtable"));

		final byte[] row1 = Bytes.toBytes("row1");
		final byte[] row2 = Bytes.toBytes("row2");
		final byte[] cf1 = Bytes.toBytes("colfam1");
		final byte[] cf2 = Bytes.toBytes("colfam2");
		final byte[] q1 = Bytes.toBytes("qual1");
		final byte[] q2 = Bytes.toBytes("qual2");
		
		//批处理列表
		List<Row> batch = new ArrayList<Row>();
		
		Put put1=new Put(row1);
		put1.addColumn(cf1, q1, Bytes.toBytes("11"));
		batch.add(put1);
		
		Put put2=new Put(row2);
		put2.addColumn(cf2, q2, Bytes.toBytes("22"));
		batch.add(put2);
		
		Get get1 = new Get(row1);
		get1.addColumn(cf1, q1);
		batch.add(get1);
		
		Get get2 = new Get(row2);
		get2.addColumn(cf2, Bytes.toBytes("bogus"));
		batch.add(get2);
		
		//用以存储执行结果
		Object[] results=new Object[batch.size()];
		
		table.batch(batch,results);
		
		for (int i = 0; i < results.length; i++) {
			System.out.println("result "+i+" type:"+results[i].getClass().getSimpleName()+" ; "+results[i]);
		}
	}

	@After
	public void destory() throws IOException {
		conn.close();
	}
}
