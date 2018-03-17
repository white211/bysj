package cn.white.bysj.utils.oauth;

import cn.white.bysj.utils.redis.RedisService;
import cn.white.bysj.utils.redis.RedisServiceImpl;
//import com.shimh.common.cache.RedisManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;

/**
 * 将session保存到redis
 * 
 * @author white
 *
 * 2018年3月16日
 *
 */
public class OAuthSessionDAO extends CachingSessionDAO implements InitializingBean{
	
	private static Logger logger = LoggerFactory.getLogger(OAuthSessionDAO.class); 
	
	private RedisService redisService;
	
	
	@Override
	protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        logger.info(sessionId.toString());

		redisService.set(sessionId.toString(), session, RedisServiceImpl.DEFAULT_EXPIRE);
        return sessionId;
	}
	
	@Override
	protected void doUpdate(Session session) {
		if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }
		 logger.info(session.getId().toString());
		redisService.set(session.getId().toString(), session, RedisServiceImpl.DEFAULT_EXPIRE);
	}

	@Override
	protected void doDelete(Session session) {
		redisService.delete(session.getId().toString());
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		logger.info(sessionId.toString());
		return redisService.get(sessionId.toString(), Session.class);
	}

	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(null == this.redisService){
			 logger.error("StringRedisTemplate should be not null."); 
		}
	}
}
