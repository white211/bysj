package cn.white.bysj.utils.redis;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


/**
 * Create by @author white
 *
 * @date 2017-12-28 22:18
 */
@Service
public class RedisServiceImpl implements RedisService{
    @Resource
    public RedisTemplate redisTemplate ;

    //设置redis数据 过期时长 单位：秒 2个小时
    public final static long DEFAULT_EXPIRE = 60*30*2;

    //不设置过去时长
    private final static long NOT_EXPIRE =-1;

   @Transactional
    public void set(String key,Object value,long expire){
        try {
            if (expire == NOT_EXPIRE){
                redisTemplate.opsForValue().set(key,value);
            }else{
                redisTemplate.opsForValue().set(key,value,expire,TimeUnit.SECONDS);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void set(String key, Object value) {
        set(key,value,DEFAULT_EXPIRE);
    }

    public Object get(String key) {
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        ValueOperations<String,T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public RedisTemplate<String,Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
