package org.seckill.dao.cache;

import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private JedisPool jedisPool;
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	public RedisDao(String ip,int port){
		jedisPool = new JedisPool(ip,port);
	}
	
	public Seckill getSeckill(long seckillId){
		//redis操作缓存
		try {
			Jedis jedis = jedisPool.getResource();
			try {
			  String key = "seckill:"+seckillId;
			  //内部并没有实现序列化
			  // https://github.com/eishay/jvm-serializers/wiki
			  byte[] bytes = jedis.get(key.getBytes());
			  if(bytes != null){
				  Seckill seckill = schema.newMessage();
				  ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
				  //seckill被反序列化，此法效率甚高
				  return seckill;
			  }
			} finally{
				jedis.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	//set方法
	public String putSeckill(Seckill seckill){
		try {
			//序列化
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();
				//对象特别大时，有一个缓冲的过程
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//超时缓存
				int timeOut = 60 * 60;	
				//返回OK 一致性建立在超时的基础上
				String result = jedis.setex(key.getBytes(), timeOut, bytes);				
				return result;
			 }finally{
				jedis.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}	
		return null;
	}
}
