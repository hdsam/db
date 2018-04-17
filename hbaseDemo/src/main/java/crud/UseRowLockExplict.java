package crud;

import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

//显示使用行锁
class UnlockedPut implements Runnable {

	final byte[] row1 = Bytes.toBytes("row1");
	final byte[] row2 = Bytes.toBytes("row2");
	final byte[] cf1 = Bytes.toBytes("colfam1");
	final byte[] cf2 = Bytes.toBytes("colfam2");
	final byte[] q1 = Bytes.toBytes("qual1");
	final byte[] q2 = Bytes.toBytes("qual2");

	@Override
	public void run() {
		try {
			Connection conn=ConnectionFactory.createConnection(HBaseConfiguration.create());
			Table table = conn.getTable(TableName.valueOf("testtable"));
			Put put = new Put(row1);
			put.addColumn(cf1, q1, Bytes.toBytes("v11"));
			long time = System.currentTimeMillis();
			System.out.println("Thread trying to put same row now..");
			table.put(put);
			System.out.println("Wait time: " + (System.currentTimeMillis() - time) + "ms");
			
			conn.close();
			table.close();
		} catch (IOException e) {
			System.out.println("Thread error: " + e);
		}
	}
}

public class UseRowLockExplict {
	Connection conn;
	@Test
	public void testUseRowLockExplict() throws IOException {
		System.out.println("Talking out lock...");
		conn=ConnectionFactory.createConnection(HBaseConfiguration.create());
		Table table = conn.getTable(TableName.valueOf("testtable"));

	}

}
