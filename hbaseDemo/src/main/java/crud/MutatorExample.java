package crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MutatorExample {
	Connection conn;

	@Before
	public void setUp() throws IOException {
		// 创建HBase客户端配置对象
		Configuration conf = HBaseConfiguration.create();
		// 获得数据库连接
		conn = ConnectionFactory.createConnection(conf);
	}

	//异步批量put处理
	@Test
	public void doMutator() throws IOException {
		BufferedMutatorParams params = new BufferedMutatorParams(TableName.valueOf("testtable"));
//		long writeBufferSize=4096;
//		params.writeBufferSize(writeBufferSize);
		BufferedMutator mutator = conn.getBufferedMutator(params);
		
		List<Put> puts =new  ArrayList<Put>();
		int line = 100000;
		byte[] cf=Bytes.toBytes("info");
		byte[] cf_q1=Bytes.toBytes("name");
		byte[] cf_q2=Bytes.toBytes("age");
		for (int i = 0; i < line; i++) {
			Put put = new Put(Bytes.toBytes(i));
			put.addColumn(cf, cf_q1, RandomStringUtils.randomAlphabetic(5).toLowerCase().getBytes());
			put.addColumn(cf, cf_q2, Bytes.toBytes(RandomUtils.nextInt(80)));
			puts.add(put);
			System.out.println(i);
		}
		mutator.mutate(puts);
		mutator.flush();
		System.out.println("done");
	}

	@After
	public void destory() throws IOException {
		conn.close();
	}
}
