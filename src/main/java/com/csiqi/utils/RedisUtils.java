package com.csiqi.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Properties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    private static final Log LOG = LogFactory.getLog(RedisUtils.class);

    private static JedisPool jedisPool;
    private static String proPath = "redisConfig.properties";
    private static ThreadLocal<Jedis> local = new ThreadLocal<Jedis>();

    private RedisUtils(){

    }

    /**
      *@ClassName 初始化连接
      *@Date 11:34 AM
      *@Author 
      */
    public static void initialPool(){
        try {
            Properties props = new Properties();
            props.load(RedisUtils.class.getClassLoader().getResourceAsStream(proPath));
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.valueOf(props.getProperty("jedis.pool.maxTotal")));
            config.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));
            config.setMinIdle(Integer.valueOf(props.getProperty("jedis.pool.minIdle")));
            config.setMaxWaitMillis(Long.valueOf(props.getProperty("jedis.pool.maxWait")));
            config.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));

            // 根据配置实例化jedis池
            jedisPool = new JedisPool(config, props.getProperty("redis.host"),
                    Integer.valueOf(props.getProperty("redis.port")),
                    Integer.valueOf(props.getProperty("redis.timeout")),
                    props.getProperty("redis.passWord"),0);
            LOG.debug("Redis线程池初始化成功");
        } catch (Exception e){
            LOG.error("Redis线程池初始化异常：",e);
        }
    }

    /**
      *@ClassName 获取连接
      *@Date 2018/12/5 11:35 AM
      *@Author 
      *@return
      */
    public static Jedis getConnection(){
        Jedis jedis = local.get();
        if(jedis == null){
            if(jedisPool == null){
                initialPool();
            }
            jedis = jedisPool.getResource();
            local.set(jedis);
        }
        return jedis;
    }

    /**
      *@ClassName 关闭连接
      *@Date 2018/12/5 11:36 AM
      *@Author 
      */
    public static void closeConnection(){
        Jedis jedis = local.get();
        if(jedis != null){
            jedis.close();
        }
        local.set(null);
    }

    /**
      *@ClassName 清理全部数据
      *@Date 2018/12/5 11:36 AM
      *@Author 
      */
    public static void cleanData(){
        Jedis jedis = getConnection();
        jedis.flushDB();
        closeConnection();
    }

    /**
      *@ClassName 存储String类型数据
      *@Date 2018/12/5 11:36 AM
      *@Author 
      *@param namespace 命名空间
      *@param key 键
      *@param val 存储值
      */
    public static void setString(String namespace, String key, String val){
        Jedis jedis = getConnection();
        jedis.set(namespace+":"+key,val);
        closeConnection();
    }

    /**
      *@ClassName 存储String数据带过期时间戳
      *@Date 2018/12/7 4:27 PM
      *@Author 
      *@param namespace 命名空间
      *@param key 键
      *@param val 存储值
      *@param unixTime 过期时间戳
      */
    public static void setStringUnix(String namespace, String key, String val, Long unixTime){
        Jedis jedis = getConnection();
        jedis.set(namespace+":"+key,val);
        jedis.expireAt(namespace+":"+key,unixTime);
        closeConnection();
    }

    /**
     * 存储String数据带生存时间
     */
    public static void setStringCountdown(String namespace, String key, String val, int unixTime){
        Jedis jedis = getConnection();
        jedis.set(namespace+":"+key,val);
        jedis.expire(namespace+":"+key,unixTime);
        closeConnection();
    }

    /**
      *@ClassName 获取String类型数据
      *@Date 2018/12/5 11:37 AM
      *@Author 
      *@param namespace 命名空间
      *@param key 键
      *@return 返回数据
      */
    public static String getString(String namespace, String key){
        Jedis jedis = getConnection();
        String stringData = jedis.get(namespace+":"+key);
        closeConnection();
        return stringData;
    }

    /**
      *@ClassName 存储list数据
      *@Date 2018/12/5 11:52 AM
      *@Author 
      *@param namespace 命名空间
      *@param key 键
      *@param val 存储值
      */
    public static void pushList(String namespace, String key, String val){
        Jedis jedis = getConnection();
        jedis.lpush(namespace+":"+key,val);
        closeConnection();
    }

    /**
     *@ClassName 存储list数据带过期时间
     *@Date 2018/12/5 4:27 PM
     *@Author 
     *@param namespace 命名空间
     *@param key 键
     *@param val 存储值
     */
    public static void pushListUnix(String namespace, String key, String val, Long unixTime){
        Jedis jedis = getConnection();
        jedis.lpush(namespace+":"+key,val);
        jedis.expireAt(namespace+":"+key,unixTime);
        closeConnection();
    }

    /**
      *@ClassName 获取List类型数据
      *@Date 2018/12/5 12:25 PM
      *@Author 
      *@param namespace 命名空间
      *@param key 键
      *@return 返回list数据
      */
    public static List<String> getList(String namespace, Object key){
    	String _key = String.valueOf(key);
        Jedis jedis = getConnection();
        List<String> listData = jedis.lrange(namespace+":"+_key,0,-1);
        closeConnection();
        return listData;
    }
}