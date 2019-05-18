package com.example.demo.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

	private RedisUtils() {

	}

	@Autowired
	private static StringRedisTemplate stringRedisTemplate;
	@Autowired
	private StringRedisTemplate strRedisTemplate;

	/**
	 * 解决spring无法注入static修饰的对象
	 */
	@PostConstruct
	public void init() {
		stringRedisTemplate = strRedisTemplate;
	}

	/**
	 * 设置key的过期时间
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	public void setKeyExpire(String key, long timeout, TimeUnit unit) {
		try {
			stringRedisTemplate.expire(key, timeout, unit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("设置过期时间失败");
		}
	}
	
	/**
	 * 获取key的过期时间 单位毫秒
	 * @param key
	 * @return
	 */
	public Long getKeyExpire(String key) {
		try {
			return stringRedisTemplate.getExpire(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取key过期时间失败");
		}
	}
	
	/**
	 * 判断一个key是否存在
	 * @param key
	 * @return
	 */
	public Boolean hasKey(String key) {
		try {
			return stringRedisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取key过期时间失败");
		}
	}
	
	/**
	 * 获取key的过期时间 单位自定义
	 * @param key
	 * @param timeUnit
	 * @return
	 */
	public Long getKeyExpire(String key, TimeUnit timeUnit) {
		try {
			return stringRedisTemplate.getExpire(key, timeUnit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取key过期时间失败");
		}
	}
	
	
	/**
	 * 添加String类型的值
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		try {
			stringRedisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加String类型的值异常");
		}
	}

	/**
	 * 添加String类型的值
	 * 
	 * @param key     键名
	 * @param value   键值
	 * @param timeout 有效时间
	 * @param unit    时间单位
	 */
	public static void setString(String key, String value, long timeout, TimeUnit unit) {
		try {
			stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加String类型的值异常");
		}
	}

	/**
	 * 获取String类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return stringRedisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取String类型的值异常");
		}
	}

	/**
	 * 获取List类型的值
	 * 
	 * @param key
	 * @param start 起始位置
	 * @param end   结束位置
	 * @return	List
	 */
	public static List<String> getList(String key, long start, long end) {
		try {
			return stringRedisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取List类型的值异常");
		}
	}

	/**
	 * 从左添加List类型的值
	 * 
	 * @param key
	 * @param value
	 */
	public static void setLeftList(String key, String value) {

		try {
			stringRedisTemplate.opsForList().leftPush(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加List类型的值异常");
		}
	}

	/**
	 * 从左添加List类型的值
	 * 
	 * @param key
	 * @param value
	 */
	public static void setRightList(String key, String value) {

		try {
			stringRedisTemplate.opsForList().rightPush(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加List类型的值异常");
		}
	}

	/**
	 * 从左开始List中获取值
	 * @param key
	 * @return	String
	 */
	public static String getLeftList(String key) {

		try {
			return stringRedisTemplate.opsForList().leftPop(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("从List中获取值异常");
		}
	}
	
	/**
	 * 从右开始List中获取值
	 * @param key
	 * @return	String
	 */
	public static String getRightList(String key, String value) {

		try {
			return stringRedisTemplate.opsForList().rightPop(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("从List中获取值异常");
		}
	}
	
	/**
	 * 添加hash类型的值
	 * @param key	hash的键名
	 * @param hashKey	元素的键名
	 * @param value		元素的值
	 */
	public static void setHash(String key, Object hashKey, Object value) {
		try {
			stringRedisTemplate.opsForHash().put(key, hashKey, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建hash异常");
		}
	}
	
	/**
	 * 添加hash类型的值
	 * @param key	hash的键名
	 * @param m		要添加的元素map集合
	 */
	public static void setHash(String key, Map map) {
		try {
			stringRedisTemplate.opsForHash().putAll(key, map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建hash异常");
		}
	}
	
	/**
	 * 获取hash类型的值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public static Object getHash(String key, Object hashKey) {
		try {
			return stringRedisTemplate.opsForHash().get(key, hashKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取hash异常");
		}
		
	}
	
	/**
	 * 根据多个key获取hash类型的值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public static List getMultiHash(String key, List keys) {
		try {
			return stringRedisTemplate.opsForHash().multiGet(key, keys);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取hash异常");
		}
		
	}
	
	/**
	 * 获取hash中key对应的hash表的所有key
	 * @param key
	 * @return
	 */
	public static Set getHashKeys(String key) {
		try {
			return stringRedisTemplate.opsForHash().keys(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取hash中所有的key异常");
		}
	}
	
	/**
	 * 获取hash中key对应的hash表的所有value
	 * @param key
	 * @return
	 */
	public static List getHashValues(String key) {
		try {
			return stringRedisTemplate.opsForHash().values(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取hash中所有的value异常");
		}
	}
	
	/**
	 * 获取hash中key对应的所有的entry
	 * @param key
	 * @return	返回map集合
	 */
	public static Map getHashEntry(String key) {
		try {
			return stringRedisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取hash中所有的value异常");
		}
	}
	
	/**
	 * 添加set类型的值
	 * @param key
	 * @param values
	 */
    public static void setSet(String key, String[] values) {
    	try {
    		stringRedisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建set异常");
		}
    }
    
    /**
     * 获取set中所有的值
     * @param key
     * @return
     */
    public static Set<String> getMembers(String key){
    	try {
			return stringRedisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取set中所有的值异常");
		}
    }
    
    /**
     * 随机获取set中的一个值
     * @param key
     * @return
     */
    public static String getRandomMember(String key){
    	try {
    		return stringRedisTemplate.opsForSet().randomMember(key);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    
    /**
     * 随机获取set中的多个值
     * @param key
     * @return
     */
    public static List<String> getRandomMembers(String key, long count){
    	try {
    		return stringRedisTemplate.opsForSet().randomMembers(key, count);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    /**
     * 随机获取set中的多个不重复的值
     * @param key
     * @param count
     * @return
     */
    public static Set<String> getDistinctRandomMembers(String key, long count){
    	try {
    		return stringRedisTemplate.opsForSet().distinctRandomMembers(key, count);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    /**
     * 移除Set中指定元素的值
     * @param key
     * @param values
     * @return
     */
    public static Long getRemoveSet(String key, Object... values){
    	try {
    		return stringRedisTemplate.opsForSet().remove(key, values);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    /**
     * 将集合1中的指定元素移到集合2
     * @param key
     * @param values
     * @return
     */
    public static Boolean getRemoveSet2OtherSet(String key, String value, String destKey){
    	try {
    		return stringRedisTemplate.opsForSet().move(key, value, destKey);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    /**
     * 随机移除set中一个值
     * @param key
     * @return
     */
    public static String getRandomRemove(String key){
    	try {
    		return stringRedisTemplate.opsForSet().pop(key);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    /**
     * 随机移除set中多个值
     * @param key
     * @return
     */
    public static List<String> getRandomRemoveMore(String key, long count){
    	try {
    		return stringRedisTemplate.opsForSet().pop(key, count);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    /**
     * 求两个set集合的并集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> unionSet(String key, String otherKey){
    	try {
    		return stringRedisTemplate.opsForSet().union(key, otherKey);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    
    /**
     * 求两个set集合的差集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> differenceSet(String key, String otherKey){
    	try {
    		return stringRedisTemplate.opsForSet().difference(key, otherKey);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    
    /**
     * 求两个set集合的交集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<String> intersectSet(String key, String otherKey){
    	try {
    		return stringRedisTemplate.opsForSet().intersect(key, otherKey);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    
    /**
     * 判断set中是否存在指定的值
     * @param key
     * @return
     */
    public static boolean getIsMembers(String key, Object o){
    	try {
			return stringRedisTemplate.opsForSet().isMember(key, o);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取set中所有的值异常");
		}
    }
    
    /**
     * 获取set集合的长度
     * @param key
     * @return
     */
    public static Long getSetSize(String key){
    	try {
			return stringRedisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取set中所有的值异常");
		}
    }
    
    /**
     * 添加zset
     * @param key
     * @param value
     * @param score
     * @return
     */
    public Boolean addZset(String key, String value, double score) {
    	try {
			return stringRedisTemplate.opsForZSet().add(key, value, score);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取set中所有的值异常");
		}
    }
    
    
    /**
     * 移除zset中多个值
     * @param key
     * @param values
     * @return
     */
    public Long removeZset(String key, Object... values) {
    	try {
			return stringRedisTemplate.opsForZSet().remove(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取set中所有的值异常");
		}
    }
    
    /**
     * 为zset指定元素加分
     * @param key
     * @param value
     * @param delta
     * @return	分数	
     */
    public Double incrementScoreZset(String key, String value, double delta) {
    	try {
			return stringRedisTemplate.opsForZSet().incrementScore(key, value, delta);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取set中所有的值异常");
		}
    }
    
    /**
     * 返回zset指定成员的排名（从小到大）
     * @param key
     * @param o
     * @return
     */
    public Long reverseAscZset(String key, Object o) {
    	try {
    		return stringRedisTemplate.opsForZSet().reverseRank(key, o);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    
    /**
     * 返回集合内元素的排名，以及分数（从小到大）
     * @param key
     * @param o
     * @return
     */
    public Set<TypedTuple<String>> reverseDescZset(String key, long start, long end) {
    	try {
    		return stringRedisTemplate.opsForZSet().rangeWithScores(key, start, end);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }
    
    /**
     * 遍历zset
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> rangeZset(String key, long start, long end) {
    	try {
    		return stringRedisTemplate.opsForZSet().range(key, start, end);
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException("获取set中所有的值异常");
    	}
    }

}
