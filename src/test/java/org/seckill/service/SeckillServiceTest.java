package org.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�����ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> seckillList = this.seckillService.getSeckillList();
        logger.info("seckillList={}", seckillList);
    }
	@Test
    public void testGetById() throws Exception {
        long id = 1;
        Seckill seckill = this.seckillService.getById(id);
        logger.info("seckill={}", seckill);
    }
    
	@Test
    public void testExportSeckillUrl() throws Exception {
        long id = 1;
        Exposer exposer = this.seckillService.exportSeckillUrl(id);

        //exposer=Exposer{exposed=true, md5='f7fa6950f51227f8876378db4027d5ef', seckillId=1001, nowTime=0, startTime=0, endTime=0}
        logger.info("exposer={}",exposer);
    }
     
	@Test
    public void testExecuteSeckill() throws Exception {
        long id = 1001;
        long userPhone = 18798789896L;
        String md5 = "f7fa6950f51227f8876378db4027d5ef";

        try {
            SeckillExecution seckillExecution = this.seckillService.executeSeckill(id, userPhone, md5);
            //seckillExecution=SeckillExecution{seckillId=1001, state=1, stateInfo='��ɱ�ɹ�', successKilled=SuccessKilled{seckillId=1001, user_phone=0, state=0, createTime=Wed Jun 15 17:49:26 CST 2016}}
            logger.info("seckillExecution={}",seckillExecution);
        } catch (RepeatKillException e) {
            logger.error(e.getMessage());
        }catch (SeckillClosedException e){
            logger.error(e.getMessage());
        }
    }
	
	@Test
    public void testExportSeckillLogic() throws Exception {
        long id = 1;
        Exposer exposer = this.seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);

            long userPhone = 18798789897L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = this.seckillService.executeSeckill(id, userPhone, md5);
                logger.info("seckillExecution={}",seckillExecution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            }catch (SeckillClosedException e){
                logger.error(e.getMessage());
            }
        }else{
            //��ɱδ����
            logger.warn("exposer={}",exposer);
        }
    }

    
    @Test
    public void testExecuteSeckillProcedure() throws Exception {
    	 long seckillId = 1;
         long phone = 1368011101;
         Exposer exposer = seckillService.exportSeckillUrl(seckillId);
         if (exposer.isExposed()) {
             String md5 = exposer.getMd5();
             SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
             logger.info(execution.getStateInfo());
         }	
    }
}
