package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;
 
/*
 *  test for git pull--09-02-15:50 could be update
 */
public interface SuccessKilledDao {

	int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
