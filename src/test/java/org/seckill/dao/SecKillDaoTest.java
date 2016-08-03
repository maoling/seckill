package org.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//∏ÊÀﬂjunit spring≈‰÷√Œƒº˛
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {
	
	@Resource
    private SecKillDao secKillDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReduceNumber() {
		Date killTime = new Date();
        int updateCount = secKillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
	}

	@Test
	public void testQueryById() {
		long id = 1;
        Seckill seckill = secKillDao.queryById(id);

        System.out.println(seckill.getName());
        System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckillList = secKillDao.queryAll(0, 100);
        for (Seckill seckill : seckillList) {
            System.out.println(seckill);
        }
	}

}
