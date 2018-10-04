package com.perfectproject.app.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Think on 2017/8/16.
 */
public interface RedisService {
    boolean execScriptTest();

    /**
     * 添加k值到hash
     * @param key
     * @param s
     * @param v
     */
    void hset(String key, String s, String v, long liveTime);
    void hset(byte[] key, byte[] s, byte[] v, long liveTime);
    void hset(String key, String s, String v);
    void hset(byte[] key, byte[] s, byte[] v);

    void hmset(String key, Map<String, String> map, long liveTime);
    void hmset(byte[] key, Map<byte[], byte[]> map, long liveTime);
    void hmset(String key, Map<String, String> map);
    void hmset(byte[] key, Map<byte[], byte[]> map);


    String hget(String key, String k1);
    String hget(byte[] key, byte[] k1);

    boolean hexists(String key, String k1);
    long hdel(String key, String field);

    /**
     * 通过key删除
     *
     * @param keys
     */
    long delete(String... keys);

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime 单位秒
     */
    void set(String key, String value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime 单位秒
     */
    Long setTrans(String key, String value, long liveTime);
    Double hincrby(final String key, final String field, final Double val);
    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    String get(final String key);

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 模糊查询key集合
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);

    /**
     *〈简述〉获取列表长度
     * @param key redis的key
     * @return 列表大小
     */
    public Long llen(String key);

    /**
     *〈简述〉列表添加一个对象
     *〈详细描述〉在列表的最开始添加
     * @param key redis的key
     * @param value 对象
     * @return 列表长度
     */
    public Long lpush(String key, Object value);

    /**
     *〈简述〉列表添加一组对象
     *〈详细描述〉在列表的最开始添加
     * @param key redis的key
     * @param values 一组对象
     * @return 列表长度
     */
    public Long lpushAll(String key, Object... values);
    public List<String> range(String key, long l, long l1);
    /**
     * 在列表尾部弹出一个元素
     * @param key
     * @param clazz
     * @return
     */
    public Object rPop(String key, Class clazz);

    /**
     * 在set中模糊查询
     * @param key
     * @param items
     * @param pattern
     * @return
     */
    ArrayList<String> sscan(String key, Long items, String pattern);

    Long addOpsForSet(String key, String... values);

    Set<String> QueryOpsForSet(String key);

    Long getOpsForSetSize(String key);


}
