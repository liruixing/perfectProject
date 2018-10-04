package com.perfectproject.app.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfectproject.app.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Think on 2017/8/16.
 */
@Service
public class RedisServiceImpl implements RedisService {
    /**
     * 空字符串
     */
    private static final String           NULL_STRING = "";
    /**
     * utf-8编码
     */
    private static String                 redisCode   = "utf-8";
    private final RedisSerializer<Object> objSer      = new JdkSerializationRedisSerializer();
    /**
     * redis 操作类
     */
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public boolean execScriptTest() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<Long>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/script-test.lua")));

        /*
            InputStreamReader read = new InputStreamReader(
                            ClassLoader.getSystemResourceAsStream("message.txt"),
                            encoding);
         */
        script.setResultType(Long.class);//
        System.out.println("script:" + script.getScriptAsString());
        Long result= redisTemplate.execute(script, Collections.singletonList("ttt"), (Object)new String[] {"2","2000"});

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+result);
        return false;
    }

    @Override
    public Double hincrby(final String key, final String field,final Double val) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.hIncrBy(
                        key.getBytes(),field.getBytes(),val
                );
            }
        });
    }


    /**
     * @param keys
     */
    public long delete(final String... keys) {

        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long result = null;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());

                }
                return result;
            }
        });
    }

    @Override
    public long hdel(final String key, final String field) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long result = null;
                result = connection.hDel(key.getBytes(),field.getBytes());
                return result;
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    @Override
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }
    @Override
    public void hmset(final byte[] key, final Map<byte[], byte[]> map,final  long liveTime) {
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hMSet(key, map);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);

                }
                return 1L;
            }
        });
    }


    @Override
    public void hmset(String key, Map<String, String> map, long liveTime) {
        if(map!=null) {
            Map map1 = new HashMap<byte[],byte[]>();
            for(String str:map.keySet()) {
                map1.put(str.getBytes(),map.get(str).getBytes());
            }
            this.hmset(key.getBytes(), map1,liveTime);
        }
    }



    @Override
    public void hmset(String key, Map<String, String> map) {
        if(map!=null) {
            Map map1 = new HashMap<byte[],byte[]>();
            for(String str:map.keySet()) {
                map1.put(str.getBytes(),map.get(str).getBytes());
            }
            this.hmset(key.getBytes(), map1,0L);
        }
    }

    @Override
    public void hmset(byte[] key, Map<byte[], byte[]> map) {
        this.hmset(key, map,0L);
    }

    @Override
    public void hset(final byte[] key, final byte[] s,final  byte[] v,final long liveTime) {
        redisTemplate.execute(new RedisCallback<Long>(){
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hSet(key,s,v);
                if(liveTime>0){
                    connection.expire(key,liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * set 带事务功能
     * @param key
     * @param value
     * @param liveTime
     */
    public Long setTrans(final String key, final String value, final long liveTime) {
        SessionCallback<Long> sessionCallback = new SessionCallback<Long>() {
            @Override
            public Long execute(RedisOperations operations) throws DataAccessException {
                try {
                    // 开启watch之后，如果key的值被修改，则事务失败，exec方法返回null
                    operations.watch(key);
                    operations.multi();
                    BoundValueOperations<String, String> oper = operations.boundValueOps(key);
                    oper.set(value);
                    oper.expire(liveTime, TimeUnit.SECONDS);
                    Object result = operations.exec();
                    if (result != null) {
                        return 1L;
                    }
                    operations.unwatch();
                    // 短暂休眠，nano避免出现活锁
                    Thread.sleep(3, new Random().nextInt(500));
                } catch (Exception e) {

                }
                return 0L;
            }
        };
        return redisTemplate.execute(sessionCallback);
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    @Override
    public void hset(String key, String s, String v) {
        this.hset(key.getBytes(),s.getBytes(),v.getBytes(),0L);
    }

    @Override
    public void hset(String key, String s, String v, long liveTime) {
        this.hset(key.getBytes(),s.getBytes(),v.getBytes(),liveTime);
    }

    @Override
    public void hset(byte[] key, byte[] s, byte[] v) {
        this.hset(key,s,v,0L);
    }

    /**
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    /**
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    /* (non-Javadoc)
     * @see com.lhyone.service.RedisService#get(java.lang.String)
     */
    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] bytes = connection.get(key.getBytes());
                    if (bytes == null)
                        return null;
                    return new String(bytes, redisCode);
                } catch (UnsupportedEncodingException e) {
//                    LOGGER.warn("caught 'UnsupportedEncodingException' e = {}", e.getMessage(), e);
                }
                return NULL_STRING;
            }
        });
    }

    @Override
    public String hget(String key,String k1) {
        return hget(key.getBytes(),k1.getBytes());
    }

    @Override
    public String hget(final byte[] key,final byte[] k1) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] bytes = connection.hGet(key,k1);
                    if (bytes == null)
                        return null;
                    return new String(bytes, redisCode);
                } catch (UnsupportedEncodingException e) {
//                    LOGGER.warn("caught 'UnsupportedEncodingException' e = {}", e.getMessage(), e);
                }
                return NULL_STRING;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.lhyone.service.RedisService#exists(java.lang.String)
     */
    public boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    @Override
    public boolean hexists(final String key,final String k1) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hExists(key.getBytes(),k1.getBytes());
            }
        });
    }

    /* (non-Javadoc)
     * @see com.lhyone.service.RedisService#keys(java.lang.String)
     */
    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }


    /****************************list （列表）操作开始***********************************/
    /**
     *〈简述〉获取列表长度
     *〈详细描述〉
     * @author lilin
     * @param key redis的key
     * @return 列表大小
     */
    public Long llen(String key) {
        ListOperations<String, String> opsForList = redisTemplate.opsForList();
        return opsForList.size(key);
    }

    /**
     *〈简述〉列表添加一个对象
     *〈详细描述〉在列表的最开始添加
     * @author lilin

     * @return 列表长度
     */
    public Long lpush(String key, Object value) {
        ListOperations<String, String> opsForList = redisTemplate.opsForList();
        String valueStr;
        try {
            valueStr = MAPPER.writeValueAsString(value);
            return opsForList.leftPush(key, valueStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *〈简述〉列表添加一组对象
     *〈详细描述〉在列表的最开始添加

     * @return 列表长度
     */
    public Long lpushAll(String key, Object... values) {
        List<String> valueStrs = new ArrayList<String>();
        for(Object value : values){
            try {
                valueStrs.add(MAPPER.writeValueAsString(value));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        if(valueStrs.isEmpty()){
            return null;
        }
        ListOperations<String, String> opsForList = redisTemplate.opsForList();
        return opsForList.leftPushAll(key, valueStrs);
    }

    public List<String> range(String key,long l,long l1){
        ListOperations<String, String> opsForList = redisTemplate.opsForList();
        List<String> vs =opsForList.range(key,l,l1);
        return vs;
    }
    /**
     * 在列表尾部弹出一个元素
     * @param key
     * @param clazz
     * @return
     */
    public Object rPop(String key, Class clazz) {
        ListOperations<String, String> opsForList = redisTemplate.opsForList();
        String valueStr = opsForList.rightPop(key);
        if(valueStr!=null&&!"".equals(valueStr)){
            try {
                return MAPPER.readValue(valueStr, clazz);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /****************************list （列表）操作结束***********************************/

    @Override
    public Long addOpsForSet(String key,String... values) {
        Long l= redisTemplate.opsForSet().add(key, values);
        return l;
    }
    @Override
    public Set<String> QueryOpsForSet(String key) {
        Set<String> set= redisTemplate.opsForSet().members(key);
        return set;
    }
    @Override
    public Long getOpsForSetSize(String key) {
        Long l= redisTemplate.opsForSet().size(key);
        return l;
    }
    @Override
    public ArrayList<String> sscan(String key, Long items, String pattern) {
        try {
            ArrayList<String> list = new ArrayList<String>();
            Cursor<String> cursor1 = redisTemplate.opsForSet().scan(key, ScanOptions.scanOptions().match(pattern).count(items).build());
            while (cursor1.hasNext()) {
                list.add(cursor1.next());
            }
            return list;
        }catch (Exception ce){
            return null;
        }
    }
}
