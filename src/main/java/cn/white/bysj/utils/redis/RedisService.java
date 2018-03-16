package cn.white.bysj.utils.redis;

/**
 * Create by @author white
 *
 * @date 2017-12-28 22:17
 */
public interface RedisService {
    public void set(String key, Object value);

    public Object get(String key);

    public void expire(String key,Integer timeout);
}
