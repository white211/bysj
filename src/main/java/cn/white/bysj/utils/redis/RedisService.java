package cn.white.bysj.utils.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Create by @author white
 *
 * @date 2017-12-28 22:17
 */
public interface RedisService {

    public void set(String key,Object value,long expire);

    public void set(String key, Object value);

    public Object get(String key);

    public <T> T get(String key, Class<T> clazz);

    public void delete(String key);

    public RedisTemplate<String,Object> getRedisTemplate();

    public void setRedisTemplate(RedisTemplate<String,Object> redisTemplate);
}
