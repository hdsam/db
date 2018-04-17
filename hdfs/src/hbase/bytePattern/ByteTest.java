package hbase.bytePattern;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import java.lang.System;

public class ByteTest {
	
	
	@Test
	public void test1() throws UnsupportedEncodingException {
		String ce="1";
//		System.out.println(Bytes.toByte);
		System.out.println(ce.getBytes("GBK").length); //1
		System.out.println(ce.getBytes("UTF-8").length); //1

		
		String cz="中";
		System.out.println(cz.getBytes("GBK").length); //2
		System.out.println(cz.getBytes("UTF-8").length); //3
	}
	
	
	
	
	//各种数据形式的字节长度区别
	@Test
	public void test() throws NoSuchAlgorithmException {
		//long
		long l=1234567890L; //10位数字的long类型，
		byte[] lb = Bytes.toBytes(l);
		System.out.println("long bytes length: "+ lb.length);
		
		String s = String.valueOf(l);
		byte[] sb = Bytes.toBytes(s);
		System.out.println("long as string length: "+ sb.length);
		
		//hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = md.digest(Bytes.toBytes(s));
		System.out.println("md5 digest bytes length: "+digest.length);
		
		String sDigest = new String(digest);
		byte[] sbDigest = Bytes.toBytes(sDigest);
		System.out.println("md5 digest as string length: "+sbDigest.length);
	}
	
}
