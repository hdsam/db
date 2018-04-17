package hdfs;

import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

//HDFS 
public class HDFSSample {
	
	@Test
	public void listFiles() throws IOException {
		Configuration conf = new Configuration();
		System.setProperty("HADOOP_USER_NAME","hadoop");
		FileSystem fs = FileSystem.get(conf);
		FileStatus[] listStatus = fs.listStatus(new Path("input"));
		for (FileStatus fileStatus : listStatus) {
			System.out.println(fileStatus.getPath().getName());
		}
		
	}
	
	@Test
	public  void createFile() throws IOException {
		Configuration conf = new Configuration();
		System.setProperty("HADOOP_USER_NAME","hadoop");
		FileSystem fs=FileSystem.get(conf);
		FSDataOutputStream fdos = fs.create(new Path("input/test.txt"),true);
		fdos.write("hello world\r\n".getBytes("utf-8"));
		fdos.flush();
		fdos.close();
		fs.close();
	}
}

