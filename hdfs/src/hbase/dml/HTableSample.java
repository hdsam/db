package hbase.dml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HTableSample {

	
	Connection conn=null;
	//获得操作对象
	@Before
	public void setUp() throws IOException {
//		System.setProperty("HADOOP_USER_NAME", "hadoop");
		Configuration conf= HBaseConfiguration.create();
		conf.addResource(HTableSample.class.getClassLoader().getResourceAsStream("hbase-site.xml"));
		conf.addResource(HTableSample.class.getClassLoader().getResourceAsStream("core-site.xml"));
		conn = ConnectionFactory.createConnection(conf);
		
	}
	
	@Test
	public void createTable() throws IOException {
		Admin admin = conn.getAdmin();
		TableName tableName = TableName.valueOf("userinfo");
		if(admin.tableExists(tableName)) {
			System.out.println(tableName.toString()+"表已存在");
			return ;
		}
		
		HTableDescriptor table= new  HTableDescriptor(tableName);
		HColumnDescriptor basicInfo = new HColumnDescriptor("basic");
		basicInfo.setCompactionCompressionType(Algorithm.NONE);
		basicInfo.setMaxVersions(3);
		table.addFamily(basicInfo);
		admin.createTable(table);
		admin.close();
	}
	
	@Test
	public void doPut() throws IOException {
		Table table = conn.getTable(TableName.valueOf("userinfo"));
		List<Put> puts=new ArrayList<>();
		for (int i = 0; i <100; i++) {
			Put put = new Put(('a'+i%26+RandomStringUtils.randomNumeric(4)).getBytes());
			put.addColumn("basic".getBytes(), "id".getBytes(), System.currentTimeMillis(),org.apache.hadoop.hbase.util.Bytes.toBytes(i));
			puts.add(put);
		}
		table.put(puts);
	}
	
	
	
	@Test
	public void doGet() throws IOException{
		Table table = conn.getTable(TableName.valueOf("userinfo"));
		Get get = new Get(String.valueOf(1002255).getBytes());
		get.addColumn("basic".getBytes(), "id".getBytes());
		Result result = table.get(get);
		List<Cell> cells = result.listCells();
		for (Cell cell : cells) {
			
			System.out.println(new String(cell.getRow()));
			System.out.println(new String(cell.getFamily()));
			System.out.println(new String(cell.getQualifier()));
			System.out.println(Bytes.toString(cell.getValue()));
			
		}
	}
	@After
	public void endUp() throws IOException {
		conn.close();
	}
}
