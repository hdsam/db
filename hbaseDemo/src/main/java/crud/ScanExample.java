package crud;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorImpl;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.RowMutations;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScanExample {

	private Connection conn;

	@Before
	public void setUp() throws IOException {
		// 读取配置文件并获得连接
		System.setProperty("HADOOP_USER_NAME", "hadoop");
		Configuration conf = HBaseConfiguration.create();
		conn = ConnectionFactory.createConnection(conf);
	}

	@Test
	public void testScan() throws IOException {
		Table table = conn.getTable(TableName.valueOf("testtable"));
		Scan scan = new Scan();
		// scan.addFamily(Bytes.toBytes("cf1"));
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			for (Cell cell : result.listCells()) {
				System.out.println("rowkey:" + Bytes.toString(result.getRow()) + "\t" + "column "
						+ Bytes.toString(CellUtil.cloneValue(cell)));
			}
		}
		table.close();
	}

	@Test
	public void testFilter() throws IOException {
		Table table = conn.getTable(TableName.valueOf("testtable"));
		Scan scan = new Scan();

		FilterList filters = new FilterList(Operator.MUST_PASS_ALL); //MUST PASS ALL:必须符合所有过滤条件

		//rowkey前缀过滤
		PrefixFilter prefixFilter = new PrefixFilter(Bytes.toBytes("row1"));
		filters.addFilter(prefixFilter);

		//单列值过滤
		SingleColumnValueFilter singleColumnVallueFilter = new SingleColumnValueFilter(Bytes.toBytes("colfam1"),
				Bytes.toBytes("qual1"), CompareOp.EQUAL, Bytes.toBytes("11"));
		filters.addFilter(singleColumnVallueFilter);

		scan.setFilter(filters);
		ResultScanner scanner = table.getScanner(scan);

		for (Result result : scanner) {
			for (Cell cell : result.listCells()) {
				System.out.println("rowkey:" + Bytes.toString(result.getRow()) + "\t" + "column:"
						+ Bytes.toString(CellUtil.cloneValue(cell)));
			}
		}
		table.close();
	}

	@After
	public void destory() throws IOException {
		// 关闭连接
		conn.close();
	}

}
