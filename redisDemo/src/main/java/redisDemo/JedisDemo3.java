package redisDemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisDemo3 {

	static Jedis jedis;

	@BeforeClass
	public static void beforeClass() {
		jedis = new Jedis("server1", 6379);
	}

	// Redis和Java字符串实例
	@Test
	public void testString() {
		jedis.set("tutorial-name", "Redis tutorial");
		System.out.println("stored string in redis:" + jedis.get("tutorial-name"));
	}

	// Redis和Java列表示例
	@Test
	public void testList() {
		jedis.lpush("tutorial-list", "redis");
		jedis.lpush("tutorial-list", "mongodb");
		jedis.lpush("tutorial-list", "mysql");

		List<String> results = jedis.lrange("tutorial-list", 0, -1);
		for (String str : results) {
			System.out.println("stored string list in redis:" + str);
		}
	}
	//Redis的keys()方法调用
	@Test
	public void testKeys() {
		Set<String> keys = jedis.keys("*");
		for (String key : keys) {
			System.out.println("key of redis :"+key);
			
		}
	}
	
	//Redis set集合
	@Test
	public void testSet() {
		String key = "myset";
		String[] members = new String[] {"a","b","c","c"};
		jedis.sadd(key, members);
		
		Set<String> smembers = jedis.smembers(key);
		for(String m:smembers) {
			System.out.println("member of set "+key+" :"+m);
		}
		
	}
	
	//Redis hash
	@Test
	public void testHash() {
		String key="user-info";
		Map<String, String> hash=new HashMap<>();
		hash.put("name","yeliangchen");
		hash.put("age", String.valueOf("23"));
		hash.put("major", "SE");
		jedis.hmset(key,hash);
		Map<String, String> map = jedis.hgetAll(key);
		Set<Entry<String,String>> entrySet = map.entrySet();
		System.out.println("user-info:");
		for (Entry<String, String> entry : entrySet) {
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
	}
	
	//Redis zsort
	@Test 
	public void testZSet() {
		String key = "rank";
		Map<String, Double> scoreMembers=new HashMap<>();
		scoreMembers.put("zs", 98.0);
		scoreMembers.put("ls", 69.5);
		scoreMembers.put("ww", 99.0);
		jedis.zadd(key, scoreMembers);
		System.out.println("rank:card="+jedis.zcard(key));
		Set<String> zrange = jedis.zrange(key,0 , 2);
		for (String t : zrange) {
			System.out.println("rank #:"+t);
		}
	}
	
	
}
