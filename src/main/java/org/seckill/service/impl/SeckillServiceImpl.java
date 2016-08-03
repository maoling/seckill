package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class SeckillServiceImpl implements SeckillService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SecKillDao secKillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	
	// 加入盐值,md5盐值字符串，用于混淆MD5
	private final String salt = "1234567890)(*&^%$#@!";

	@Override
	public List<Seckill> getSeckillList() {
		// TODO Auto-generated method stub
		return secKillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		// TODO Auto-generated method stub
		return secKillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		// TODO Auto-generated method stub
		Seckill seckill = secKillDao.queryById(seckillId);
		if (seckill == null)
			return new Exposer(false, seckillId);
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		// 系统当前时间
		Date nowTime = new Date();

		if (nowTime.getTime() < startTime.getTime()
				|| nowTime.getTime() > endTime.getTime()) {
			// 当前时间不在秒杀时间范围内
			return new Exposer(false, seckillId, nowTime.getTime(),
					startTime.getTime(), endTime.getTime());
		}

		// TODO 转化特定字符串的过程,不可逆
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seckill.service.SeckillService#executeSeckill(long, long,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userphone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillClosedException {

		if (md5 == null || !md5.equals(getMD5(seckillId)))
			throw new SeckillException("seckill data rewrite.");
		Date nowTime = new Date();
		try {
			int updateCount = secKillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				throw new SeckillClosedException("seckill is closed");
			} else {
				// 记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId,
						userphone);
				if (insertCount <= 0)
					throw new RepeatKillException("seckill is repeated");
				else {
					SuccessKilled successKilled = successKilledDao
							.queryByIdWithSeckill(seckillId, userphone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS);
				}
			}
		}catch(SeckillClosedException e1){
			throw e1;
		}catch(RepeatKillException e2){
			throw e2;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			//所有编译期异常转化成运行时异常，会回滚
			throw new SeckillException("seckill inner error:" + e.getMessage()); 
		}
		
	}

	private String getMD5(long seckillId) {
		String baseStr = seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(baseStr.getBytes());
		return md5;
	}
}
