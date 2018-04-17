package numberTest;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.hadoop.io.MD5Hash;
import org.junit.Test;

public class TestNumber {

	@Test
	public void bigIntegerTest() {
		BigInteger b1 = new BigInteger("123000000000000000000000000000000000000000000",16);
		BigInteger b2 = new BigInteger("123123000000000000000000000000000000000000000",16);
		BigInteger subtract = b2.subtract(b1);
		System.out.println(subtract.toString());
	}
	
	@Test
	public void testByte1() throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		String str="123123jkdfsdfsdfjklsdfljkl";
		MessageDigest md = MessageDigest.getInstance("MD5");
		System.out.println(md.digest(str.getBytes("UTF-8")));
		System.out.println(MD5Hash.digest(str));
		System.out.println(org.apache.hadoop.io.MD5Hash.digest(str));
	}
	
	@Test
	public void testString() {
		
		System.out.println(String.format("0x16","hello"));
	}
	
	
}
