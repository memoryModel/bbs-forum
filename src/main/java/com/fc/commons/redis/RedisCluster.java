package com.fc.commons.redis;

import com.fc.commons.util.SerializeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mac on 18/12/31.
 */
@Component
public class RedisCluster {
    private final Logger logger = LoggerFactory.getLogger(RedisCluster.class);

    private static JedisCluster jedisCluster=null;

    public RedisCluster() {
        Set<HostAndPort> clusterNodes = new HashSet<HostAndPort>();
        clusterNodes.add(new HostAndPort("47.94.158.71", 9001));
        clusterNodes.add(new HostAndPort("47.94.158.71", 9002));
        clusterNodes.add(new HostAndPort("47.94.158.71", 9003));
        clusterNodes.add(new HostAndPort("47.94.158.71", 9004));
        clusterNodes.add(new HostAndPort("47.94.158.71", 9005));
        clusterNodes.add(new HostAndPort("47.94.158.71", 9006));

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);//最大连接个数
        jedisPoolConfig.setMaxIdle(10);//最大空闲连接个数
        jedisPoolConfig.setMaxWaitMillis(-1);//获取连接时的最大等待毫秒数，若超时则抛异常。-1代表不确定的毫秒数
        jedisPoolConfig.setTestOnBorrow(true);//获取连接时检测其有效性

        jedisCluster = new JedisCluster(clusterNodes,15000,100,
                jedisPoolConfig);//第二个参数：超时时间     第三个参数：最大尝试重连次数
    }

    /**
     * 添加对象
     * */
    public boolean set(String key, Object value) {
        try {
            byte[] obj = SerializeUtil.serialize(value);
            if (obj == null) return false;
            jedisCluster.set(key.getBytes(),obj);
            return true;
        }
        catch (Exception ex) {
            logger.error("redis set error.", ex);
        }
        return false;
    }

    /**
     * 取得对象
     * */
    public Object get(String key) { return get(key,null); }

    public Object get(String key,Object defaultValue) {
        try {
            byte[] value = jedisCluster.get(key.getBytes());
            if (null==value)return defaultValue;
            return SerializeUtil.unserialize(value);
        }
        catch (Exception ex) {
            logger.error("redis get error.", ex);
        }
       /* finally {
            try {
                jedisCluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return defaultValue;
    }

    /**
     * 删除对象
     * */
    public boolean del(String key) {
        try {
            return jedisCluster.del(key) >0 ;
        }
        catch (Exception ex) {
            logger.error("redis del error.", ex);
        }
        return false;
    }

    /**
     * 删除对象
     * */
    public boolean remove(String key) {
        try {
            return jedisCluster.del(key.getBytes()) >0 ;
        }
        catch (Exception ex) {
            logger.error("redis del error.", ex);
        }
        return false;
    }

    /**
     * 计数器-增加
     * */
    public long incr(String key) {
        try {
            return jedisCluster.incr(jedisCluster.get(key));
        } catch (Exception ex) {
            logger.error("redis incr error.", ex);
        }
        return 0;
    }

    /**
     * 计数器-减少
     * */
    public long decr(String key) {
        try {
            return jedisCluster.decr(jedisCluster.get(key));
        } catch (Exception ex) {
            logger.error("redis decr error.", ex);
        }
        return 0;
    }

    /**
     * value是否是列表成员
     * */
    public boolean sismember(String key,String value) {
        if (null==value)return false;
        try {
            return jedisCluster.sismember(key,value);
        } catch (Exception ex) {
            logger.error("redis sismember error.", ex);
        }
        return false;
    }

    /**
     * 返回给定所有集合的交集
     * */
    public Set<String> sinter(String key1, String key2) {
        try {
            Set<String> result = new HashSet<String>();
            Set<String> set1 = jedisCluster.smembers(key1);
            Set<String> set2 = jedisCluster.smembers(key2);
            if (set1== null || set2 == null) return null;
            result.clear();
            result.addAll(set1);
            result.retainAll(set2);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("redis sinter error.", ex);
        }
        return null;
    }

    /**
     * key是否存在
     * */
    public boolean exists(String key) {
        try {
            return jedisCluster.exists(key);
        } catch (Exception ex) {
            logger.error("redis exists error.", ex);
        }
        return false;
    }

    /**
     * 设置key过期时间
     * */
    public long expire(String key, int seconds) {
        try {
            return jedisCluster.expire(key, seconds);
        } catch (Exception ex) {
            logger.error("redis expire error.", ex);
        }
        return 0;
    }

    /**
     * 添加到Set列表中
     */
    public boolean sadd(String key, String... value) {
        try {
            jedisCluster.sadd(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("redis sadd error.", ex);
        }
        return false;
    }

    /**
     * 移除Set列表中对象
     */
    public boolean srem(String key, String... value) {
        try {
            jedisCluster.srem(key, value);
            return true;
        } catch (Exception ex) {
            logger.error("redis srem error.", ex);
        }
        return false;
    }

    /**
     * 获取Set列表
     */
    public Set<String> smembers(String key){
        try {
            return jedisCluster.smembers(key);
        } catch (Exception ex) {
            logger.error("redis smembers error.", ex);
        }
        return null;
    }

    /**
     * 查找keys*
     */
    public Set<String> keys(String pattern) {
        Set<String> retList = new HashSet<>();
        try {
            //获取所有连接池节点
            Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
            //遍历所有连接池，逐个进行模糊查询
            for(String k : nodes.keySet()){
                logger.debug("从【{}】获取keys", k);
                JedisPool pool = nodes.get(k);
                //获取Jedis对象，Jedis对象支持keys模糊查询
                Jedis connection = pool.getResource();
                try {
                    retList.addAll(connection.keys(pattern));
                } catch(Exception e){
                    logger.error("获取key异常", e);
                } finally{
                    logger.info("关闭连接");
                    //一定要关闭连接！
                    connection.close();
                }
            }
        } catch (Exception ex) {
            logger.error("redis keys error.", ex);
        }
        return retList;
    }

    /**
     * 分布式锁 加锁
     * @param acquireTimeout 获取锁之前的超时时间 单位：毫秒
     * @param timeOut 获取锁之后的超时时间 单位：毫秒
     * @param lockKey 加锁的key
     * @param lockValue 加锁的value
     * */
    public boolean getLock(Long acquireTimeout,Long timeOut,String lockKey,String lockValue){
        if(StringUtils.isBlank(lockKey) || StringUtils.isBlank(lockValue)){
            return false;
        }
        try{
            //获取锁后的超时时间转换为秒
            int expireLockTime = (int)(timeOut / 1000);
            Long endTime = System.currentTimeMillis() + acquireTimeout;
            byte[] obj = SerializeUtil.serialize(lockValue);
            //保证在设置acquireTimeout时间内如果没有获得到锁的话，进行不断的获取
            while(endTime >= System.currentTimeMillis()){
                String result = jedisCluster
                        .set(lockKey.getBytes(), obj, "nx".getBytes(), "ex".getBytes(), expireLockTime);
                if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(result)) return true;
                //使用sexnx命令插入key为lockKey，成功返回1，失败返回0
                /*if(jedisCluster.setnx(lockKey,lockValue) == 1){

                    //获取成功后，要设置锁的超时时间，防止死锁
                    jedisCluster.expire(lockKey,expireLockTime);
                    return true;
                }*/
            }
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 分布式锁 解锁
     * @param lockKey 加锁的key
     * @param lockValue 加锁的value
     * */
    public boolean unLock(String lockKey,String lockValue){
        if(StringUtils.isBlank(lockKey) || StringUtils.isBlank(lockValue)){
            return false;
        }
        try{
            if(lockValue.equals(jedisCluster.get(lockKey))){
                jedisCluster.del(lockKey);
                return true;
            }
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 计数器 用于MQ去重
     * */
    public Long incrMQ(String key,Long timeOut){
        if(StringUtils.isBlank(key))return null;
        try{
            Long res = jedisCluster.incr(key);
            if(res == 1){
                jedisCluster.expire(key,(int)(timeOut / 1000));
            }
            return res;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Long getMQ(String key){
        if(StringUtils.isBlank(key))return null;
        try{
            return Long.parseLong(jedisCluster.get(key));
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加sortedSet类型(单个添加)
     * */
    public Long zadd(String key, double score, String member){
        try {
            return jedisCluster.zadd(key,score,member);
        }
        catch (Exception ex) {
            logger.error("redis zadd error.", ex);
        }
        return 0L;
    }

    /**
     * 添加sortedSet类型(批量添加)
     * */
    public Long zadd(String key,
                     Map<String, Double> scoreMembers){
        try {
            return jedisCluster.zadd(key,scoreMembers);
        }
        catch (Exception ex) {
            logger.error("redis zadd error.", ex);
        }
        return 0L;
    }

    /**
     *查看sortedSet类型,按score 值递减(从大到小)来排列
     * */
    public Set zrevrange(String key, long start, long end){
        try {
            return jedisCluster.zrevrange(key,start,end);
        }
        catch (Exception ex) {
            logger.error("redis zrevrange error.", ex);
        }
        return null;
    }

    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员。
     * 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内
     * */
    public Long zremrangebyrank(String key, long start, long end){
        try {
            return jedisCluster.zremrangeByRank(key,start,end);
        }
        catch (Exception ex) {
            logger.error("redis zremrangebyrank error.", ex);
        }
        return null;
    }

    /**
     * 返回有序集 key的数量
     * */
    public Long zcard(String key){
        try {
            return jedisCluster.zcard(key);
        }
        catch (Exception ex) {
            logger.error("redis zcard error.", ex);
        }
        return 0L;
    }

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     * */
    public Long zrem(String key, String... members){
        try {
            return jedisCluster.zrem(key,members);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            logger.error("redis zrem error.", ex);
        }
        return 0L;
    }

    /**
     * 对有序集合中指定成员的分数加上增量
     * */
    public Double zincrby(String key, Double score, String member) {
        try {
            return  jedisCluster.zincrby(key, score, member);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("redis zincrby error.", ex);
        }
        return 0D;
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value
     * */
    public Long hset(String key, String field, String value){
        try {
            return jedisCluster.hset(key,field,value);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            logger.error("redis hset error.", ex);
        }
        return 0L;
    }

    /**
     * 重载：将哈希表 key 中的域 field 的值设为 value
     * */
    public Long hset(String key, String field, Object value){
        try {
            byte[] bytes = SerializeUtil.serialize(value);
            if (bytes == null) return 0L;
            return jedisCluster.hset(key.getBytes(),field.getBytes(),bytes);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            logger.error("redis hset error.", ex);
        }
        return 0L;
    }

    /**
     * 返回哈希表 key 中给定域 field 的值
     * */
    public String hget(String key, String field){
        try {
            return jedisCluster.hget(key,field);
        }
        catch (Exception ex) {
            logger.error("redis hget error.", ex);
        }
        return null;
    }

    /**
     * 获取在哈希表中指定 key 的所有字段和值
     * */
    public Map<String, String> hgetall(String key) {
        try {
            return jedisCluster.hgetAll(key);
        } catch (Exception ex) {
            logger.error("redis hgetall error.", ex);
        }
        return null;
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * */
    public String hmset(String key, Map<String, String> hash){
        try {
            return jedisCluster.hmset(key,hash);
        }
        catch (Exception ex) {
            logger.error("redis hmset error.", ex);
        }
        return null;
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值。
     * */
    public List<String> hmget(String key, String... fields){
        try {
            return jedisCluster.hmget(key,fields);
        }
        catch (Exception ex) {
            logger.error("redis hmget error.", ex);
        }
        return null;
    }

    /**
     * 为哈希表中的字段值加上指定增量值
     * */
    public Long hincrBy(String key, String field, long number) {
        try {
            return jedisCluster.hincrBy(key, field, number);
        } catch (Exception ex) {
            logger.error("redis hincrBy error.", ex);
        }
        return null;
    }

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
     * */
    public Long hdel(String key, String... fields){
        try {
            return jedisCluster.hdel(key,fields);
        }
        catch (Exception ex) {
            logger.error("redis hdel error.", ex);
        }
        return 0L;
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     * */
    public boolean hexists(String key, String field) {
        try {
            return jedisCluster.hexists(key,field);
        } catch (Exception ex) {
            logger.error("redis hexists error.", ex);
        }
        return false;
    }

    /**
     * 集合的基数(元素的数量)，或者返回0如果键不存在
     * */
    public Long scard(String key){
        try {
            return jedisCluster.scard(key.getBytes());
        }
        catch (Exception ex) {
            logger.error("redis scard error.", ex);
        }
        return 0L;
    }

    /**
     * 将一个或多个值插入到列表左侧
     * */
    public Long lpush(String key, String... values) {
        try {
            return jedisCluster.lpush(key, values);
        }
        catch (Exception ex) {
            logger.error("redis lpush error.", ex);
        }
        return 0L;
    }

    /**
     * 将一个或多个值插入到列表右侧
     * */
    public Long rpush(String key, String... values) {
        try {
            return jedisCluster.rpush(key, values);
        }
        catch (Exception ex) {
            logger.error("redis rpush error.", ex);
        }
        return 0L;
    }

    /**
     * 获取列表指定范围内的元素
     * */
    public List<String> lrange(String key, long start, long end) {
        try {
            return jedisCluster.lrange(key,start,end);
        } catch (Exception ex) {
            logger.error("redis lrange error.", ex);
        }
        return null;
    }

    /**
     * 获取指定列表长度
     * */
    public Long llen(String key) {
        try {
            return jedisCluster.llen(key);
        }catch (Exception ex) {
            logger.error("redis lphshx error.", ex);
        }
        return 0L;
    }

}