package org.seckill.dao.cache;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SecKillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
	private long id = 1;
	@Autowired
    private RedisDao redisDao;
	@Resource
    private SecKillDao secKillDao;
	
	@Test
	public void testSeckill() {
	   Seckill seckill = redisDao.getSeckill(id);
	   if(seckill == null){
		   seckill = secKillDao.queryById(id);
		   if(seckill != null){
			   String result = redisDao.putSeckill(seckill);
			   System.out.println(result);
			   seckill = redisDao.getSeckill(id);
			   System.out.println(seckill);
		   }
	   }
		
	}
}
