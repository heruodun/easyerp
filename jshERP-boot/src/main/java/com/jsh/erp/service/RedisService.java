package com.jsh.erp.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jsh.erp.constants.BusinessConstants;
import com.jsh.erp.constants.HttpConstants;
import com.jsh.erp.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Description
 *
 * @author jisheng hua
 * @Date: 2021/1/28 18:10
 */
@Component
public class RedisService {

    @Resource
    public RedisTemplate redisTemplate;



    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        this.redisTemplate = redisTemplate;
    }

    /**
     * @author jisheng hua
     * description:
     *  从session中获取信息
     *@date: 2021/1/28 18:10
     * @Param: request
     * @Param: key
     * @return Object
     */
    public Object getObjectFromSessionByKey(HttpServletRequest request, String key){
        Object obj=null;
        if(request==null){
            return null;
        }
        String token = request.getHeader(HttpConstants.ACCESS_TOKEN);
        if(token!=null) {
            //开启redis，用户数据放在redis中，从redis中获取
            if(redisTemplate.opsForHash().hasKey(token,key)){
                //redis中存在，拿出来使用
                obj=redisTemplate.opsForHash().get(token,key);
//                redisTemplate.expire(token, BusinessConstants.MAX_SESSION_IN_SECONDS, TimeUnit.SECONDS);
            }
        }
        return obj;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * @author jisheng hua
     * description:
     *  将信息放入session或者redis中
     *@date: 2021/1/28 18:10
     * @Param: request
     * @Param: key
     * @Param: obj
     * @return
     */
    public void storageObjectBySession(String token, String key, Object obj) {
        //开启redis，用户数据放到redis中
        redisTemplate.opsForHash().put(token, key, obj.toString());
        redisTemplate.expire(token, BusinessConstants.MAX_SESSION_IN_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * @author jisheng hua
     *  description:
     *  将信息放入session或者redis中
     * @date: 2024/5/28 20:10
     * @return
     */
    public void storageCaptchaObject(String verifyKey, String codeNum) {
        //把验证码放到redis中
        redisTemplate.opsForValue().set(verifyKey, codeNum, BusinessConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * @author jisheng hua
     * description:
     *  将信息从session或者redis中移除
     *@date: 2021/1/28 18:10
     * @Param: request
     * @Param: key
     * @Param: obj
     * @return
     */
    public void deleteObjectBySession(HttpServletRequest request, String key){
        if(request!=null){
            String token = request.getHeader(HttpConstants.ACCESS_TOKEN);
            if(StringUtil.isNotEmpty(token)){
                //开启redis，用户数据放在redis中，从redis中删除
                redisTemplate.opsForHash().delete(token, key);
            }
        }
    }

    /**
     * @author jisheng hua
     * 将信息从redis中移除，比对user和ip
     * @param userId
     * @param clientIp
     */
    public void deleteObjectByUserAndIp(Long userId, String clientIp){
        Set<String> tokens = redisTemplate.keys("*");
        for(String token : tokens) {
            Object userIdValue = redisTemplate.opsForHash().get(token, "userId");
            Object clientIpValue = redisTemplate.opsForHash().get(token, "clientIp");
            if(userIdValue!=null && clientIpValue!=null && userIdValue.equals(userId.toString()) && clientIpValue.equals(clientIp)) {
                redisTemplate.opsForHash().delete(token, "userId");
            }
        }
    }
}
