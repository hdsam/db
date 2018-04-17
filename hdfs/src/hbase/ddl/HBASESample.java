package hbase.ddl;

import java.io.IOException;

import java.util.Collection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.io.compress.Compression.Algorithm;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//HBASE DDL
public class HBASESample {
	private final String tableName="user";
	private String defaultCFName="username";
//	private String newCFName="uname";
	private Admin admin;
	
	//获得操作对象
	@Before
	public void getAdmin() throws IOException {
//		System.setProperty("HADOOP_USER_NAME", "hadoop");
		Configuration conf= HBaseConfiguration.create();
		conf.addResource(HBASESample.class.getClassLoader().getResourceAsStream("hbase-site.xml"));
		conf.addResource(HBASESample.class.getClassLoader().getResourceAsStream("core-site.xml"));
		Connection conn = ConnectionFactory.createConnection(conf);
		admin = conn.getAdmin();
	}
	
	
	
	
	
	//创建表
	@Test
	public void createTable() throws IOException {
		TableName tName = TableName.valueOf(tableName);
		HTableDescriptor table=new HTableDescriptor(tName);
		table.addFamily(new HColumnDescriptor(defaultCFName));
		admin.createTable(table);
		Assert.assertTrue("创建失败！", admin.tableExists(tName));
	}
	
	//查看表定义
	@Test
	public void describeTable() throws TableNotFoundException, IOException {
		HTableDescriptor user = admin.getTableDescriptor(TableName.valueOf(tableName));
		System.out.println("tableName:"+user.getTableName().toString());
		Collection<HColumnDescriptor> columns=user.getFamilies();
		for (HColumnDescriptor column : columns) {
			System.out.println("columnFamily:"+column.getNameAsString());
		}
	}
	
	
	//添加一个新列族"age"
	@Test
	public void addColumn() throws IOException {
		TableName tName= TableName.valueOf(tableName);
		if(!admin.tableExists(tName)) {
			System.out.println("表不存在！"+tName.getNameAsString());
			return ;
		}
		HColumnDescriptor newColumn=new HColumnDescriptor("age");
		newColumn.setMaxVersions(HConstants.ALL_VERSIONS);
		newColumn.setCompactionCompressionType(Algorithm.GZ);
		admin.addColumn(tName, newColumn);
	}
	
	//修改一个已存在的表，重新
	@Test
	public void modifyColumn() throws IOException{
		TableName tName= TableName.valueOf(tableName);
		if(!admin.tableExists(tName)) {
			System.out.println("表不存在！"+tName.getNameAsString());
			return ;
		}
		HTableDescriptor table=admin.getTableDescriptor(tName);
		
		HColumnDescriptor existionColumn = new HColumnDescriptor(defaultCFName);
		existionColumn.setMaxVersions(HConstants.ALL_VERSIONS);
		existionColumn.setCompactionCompressionType(Algorithm.GZ);
		table.modifyFamily(existionColumn);
		admin.modifyTable(tName,table);
		
	}
	//删除列族
	@Test
	public void deleteColumn() throws IOException {
		TableName tName= TableName.valueOf(tableName);
		if(!admin.tableExists(tName)) {
			System.out.println("表不存在！"+tName.getNameAsString());
			return ;
		}
		admin.disableTable(tName);
		admin.deleteColumn(tName, "age".getBytes("UTF-8"));
		admin.enableTable(tName);
	}
	
	//删除表
	@Test
	public void deleteTable() throws IOException {
		TableName tName= TableName.valueOf(tableName);
		if(!admin.tableExists(tName)) {
			System.out.println("表不存在！"+tName.getNameAsString());
			return ;
		}
		admin.disableTable(tName);
		admin.deleteTable(tName);
	}
	
	//关闭连接
	@After
	public void endUp() throws IOException {
		admin.close();
	}
	
	@Test
	public void test() throws IOException {
		System.out.println(this.getClass().getClassLoader().getResourceAsStream("core-site.xml").available());
	}
}
