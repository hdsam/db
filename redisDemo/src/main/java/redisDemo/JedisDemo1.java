package redisDemo;

import org.junit.Test;

import redis.clients.jedis.Jedis;

//连接
public class JedisDemo1 {

	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("server1",6379);
		jedis.set("name", "yegenyao");
		String value = jedis.get("name");
		System.out.println(value);
		jedis.close();
	}
}
