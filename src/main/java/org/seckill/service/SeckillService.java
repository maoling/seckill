package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.*;

public interface SeckillService {

	List<Seckill> getSeckillList();
	Seckill getById(long seckillId);
    Exposer exportSeckillUrl(long seckillId);
    SeckillExecution executeSeckill(long seckillId,long userphone,String md5) throws SeckillException, RepeatKillException, SeckillClosedException;;
}
