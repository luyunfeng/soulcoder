package tech.soulcoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisOperation {
    private static final long DEFAULT_REDIS_KEYS_TIMEOUT = 60*60*24*7;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 字符串操作
     * @param key
     * @param value
     */
    
    public void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    
    public void setValue(String key, String value, Long timeout,TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout , unit);
    }

    /**
     * 字符串 kv 操作
     * @param key
     * @return
     */
    
    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * Hash 值设置
     * @param key
     * @param value
     */
    
    public void setHashAll(String key, Map<String,Object> value) {
        stringRedisTemplate.opsForHash().putAll(key,value);
    }

    /**
     * Hash 值设置，并设置超时时间
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    
    public void setHashAll(String key, Map<String,Object> value, Long timeout,TimeUnit unit) {
        stringRedisTemplate.opsForHash().putAll(key,value);
        stringRedisTemplate.expire(key,timeout,unit);
    }

    /**
     * 根据 key 获取 Hash 类型的 value
     * @param key
     * @return
     */
    
    public Map<Object ,Object> getHash(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }



    /**
     * 模糊查询 慎用！！！！！
     * @param prex
     * @return
     */
    
    public List<String> getValues(String prex) {
        Set<String> keys = stringRedisTemplate.keys(prex + "*");
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }




    
    public void removeV(String key) {
        stringRedisTemplate.delete(key);
    }



    
    public void setSetValue(String key, Long timeout, String... values) {

        if(timeout == null)
        {
            timeout = DEFAULT_REDIS_KEYS_TIMEOUT;
        }

        stringRedisTemplate.opsForSet().add(key,values);

        stringRedisTemplate.expire(key,timeout, TimeUnit.SECONDS);
    }

    
    public Set<String> getSetValues(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    
    public void setHashValue(String key, String hashKey, String hashValue) {
        stringRedisTemplate.opsForHash().put(key,hashKey,hashValue);
    }

    
    public String getHashValue(String key, String hashKey) {
        return String.valueOf(stringRedisTemplate.opsForHash().get(key,hashKey));
    }

    
    public List mutiGetHashValue(String key, List hashKeys) {
        return stringRedisTemplate.opsForHash().multiGet(key,hashKeys);
    }

    
    public Set getHashKeys(String key) {
        return stringRedisTemplate.opsForHash().keys(key);
    }

    
    public void removeHashEntry(String key, String... hashKey) {
        stringRedisTemplate.opsForHash().delete(key,hashKey);
    }

    
    public void removeSetEntry(String setkey, String setValue) {
        stringRedisTemplate.opsForSet().remove(setkey,setValue);
    }

    
    public boolean putIfAbsent(String key, String value)
    {
        return this.stringRedisTemplate.opsForValue().setIfAbsent(key,value);
    }

    
    public void putRedis(byte[] paramArrayOfByte1,byte[] paramArrayOfByte2) {
        final byte[] paramArrayOfByte1Tmp=paramArrayOfByte1;
        final byte[] paramArrayOfByte2Tmp=paramArrayOfByte2;
        stringRedisTemplate.execute(new RedisCallback<Object>() {
            
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.set(paramArrayOfByte1Tmp,paramArrayOfByte2Tmp);
                return null;
            }
        },true);
    }

    
    public void putRedis(  byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long timeout,TimeUnit unit) {
        final byte[] paramArrayOfByte1Tmp = paramArrayOfByte1;
        final byte[] paramArrayOfByte2Tmp = paramArrayOfByte2;
        final long timeOutTmp = timeout;
        final TimeUnit unitTmp = unit;
        final long rawTimeout = TimeoutUtils.toMillis(timeout, unit);
        stringRedisTemplate.execute(new RedisCallback<Object>() {
            
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.set(paramArrayOfByte1Tmp,paramArrayOfByte2Tmp);
                stringRedisTemplate.execute(new RedisCallback() {
                    
                    public Boolean doInRedis(RedisConnection connection) {
                        try {
                            return connection.pExpire(paramArrayOfByte1Tmp, rawTimeout);
                        } catch (Exception var3) {
                            return connection.expire(paramArrayOfByte1Tmp, TimeoutUtils.toSeconds(timeOutTmp, unitTmp));
                        }
                    }
                },true);
                return null;
            }
        },true);
    }

    
    public byte[] getRedis(byte[] paramArrayOfByte1) {
        final byte[] paramArrayOfByte1Tmp = paramArrayOfByte1;
        return (byte[]) stringRedisTemplate.execute(new RedisCallback<Object>() {
            
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.get(paramArrayOfByte1Tmp);
            }
        },true);
    }

    
    public void removeRedis(byte[] paramArrayOfByte) {
        final byte[] tmpKey = paramArrayOfByte;
        stringRedisTemplate.execute(new RedisCallback() {
            
            public Object doInRedis(RedisConnection connection) {
                connection.del(new byte[][]{tmpKey});
                return null;
            }
        }, true);
    }

    
    public long incr(String paramString){
        final String paramStringTmp = paramString;
        return (long)stringRedisTemplate.execute(new RedisCallback() {
            
            public Object doInRedis(RedisConnection connection) {
                byte[] paramBytes = stringRedisTemplate.getStringSerializer().serialize(paramStringTmp);
                connection.incr(paramBytes);
                return null;
            }
        }, true);
    }

    
    public long decr(String paramString){
        final String paramStringTmp = paramString;
        return (long)stringRedisTemplate.execute(new RedisCallback() {
            
            public Object doInRedis(RedisConnection connection) {
                byte[] paramBytes = stringRedisTemplate.getStringSerializer().serialize(paramStringTmp);
                connection.decr(paramBytes);
                return null;
            }
        }, true);
    }

    
    public long incrBy(String paramString, long paramLong){
        final String paramStringTmp = paramString;
        final long paramLongTmp = paramLong;
        return (long)stringRedisTemplate.execute(new RedisCallback() {
            
            public Object doInRedis(RedisConnection connection) {
                byte[] paramBytes = stringRedisTemplate.getStringSerializer().serialize(paramStringTmp);
                connection.incrBy(paramBytes,paramLongTmp);
                return null;
            }
        }, true);
    }

    
    public long decrBy(String paramString, long paramLong){
        final String paramStringTmp = paramString;
        final long paramLongTmp = paramLong;
        return (long)stringRedisTemplate.execute(new RedisCallback() {
            
            public Object doInRedis(RedisConnection connection) {
                byte[] paramBytes = stringRedisTemplate.getStringSerializer().serialize(paramStringTmp);
                connection.decrBy(paramBytes,paramLongTmp);
                return null;
            }
        }, true);
    }

    
    public long incrEx(String paramString, long timeOut,TimeUnit unit){
        long cnt = incr(paramString);
        stringRedisTemplate.expire(paramString,timeOut,unit);
        return cnt;
    }

    
    public long decrEx(String paramString, long timeOut,TimeUnit unit){
        long cnt = decr(paramString);
        stringRedisTemplate.expire(paramString,timeOut,unit);
        return cnt;
    }

    
    public long incrByEx(String paramString, long paramLong, long timeOut,TimeUnit unit){
        long cnt = incrBy(paramString,paramLong);
        stringRedisTemplate.expire(paramString,timeOut,unit);
        return cnt;
    }

    
    public long decrByEx(String paramString, long paramLong, long timeOut,TimeUnit unit){
        long cnt = decrBy(paramString,paramLong);
        stringRedisTemplate.expire(paramString,timeOut,unit);
        return cnt;
    }

    
    public void setTimeout(String key,long timeout){
        stringRedisTemplate.expire(key,timeout,TimeUnit.MILLISECONDS);
    }
}

