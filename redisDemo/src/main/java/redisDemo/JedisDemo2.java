package redisDemo;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;



//使用Jedis连接池
public class JedisDemo2 {

	@Test
	public void testJedisPool() {
		JedisPoolConfig conf = new JedisPoolConfig();
		//设置最大连接数
		conf.setMaxTotal(30);
		//设置最大空闲连接数
		conf.setMaxIdle(10);
		//获得连接池
		JedisPool pool = new JedisPool(conf, "server1", 6379);
		
		//获得连接对象
		Jedis jedis = null;
		jedis = pool.getResource();
		
		//存取操作
		jedis.set("name", "hdsam");
		String value = jedis.get("name");
		System.out.println(value);
		
		//关闭连接，释放资源
		jedis.close();
		pool.close();
		
	}
}
