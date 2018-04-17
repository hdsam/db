package crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeleteListExample {
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
		
		//删除列表
		List<Delete> deletes = new ArrayList<>();
		
		Delete delete2 = new Delete(row1);
		delete2.addColumns(cf1, q1); //！删除所有版本的数据
		deletes.add(delete2);

		Delete delete1 = new Delete(row2);
		delete1.addColumn(cf2, q2); //！删除最新版本的数据，更精确，从而使之前存储的低版本数据能够被看到
		deletes.add(delete1);
		
		
		table.delete(deletes);
		table.close();
	}

	
	
	
	@After
	public void destory() throws IOException {
		conn.close();
	}
}
