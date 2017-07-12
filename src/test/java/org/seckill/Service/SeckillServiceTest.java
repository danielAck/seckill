package org.seckill.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.Exception.RepeatSeckillException;
import org.seckill.Exception.SeckillCloseExcepion;
import org.seckill.dto.ExcutionTest;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用 logback 需要用到 logback官方文档 --> logvack.qos.ch/manual/configuration.html
 * Created by 张柏桦 on 2017/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-mybatis.xml",
        "classpath:spring/spring-service.xml"})

public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long seckillId = 1000L;
        Seckill seckill = seckillService.getById(seckillId);
        logger.info("seckill={}",seckill);
        //Exposer
        // {exposed=true,
        // md5='e98003ed39055f20aa6fc262acedfd31',
        // seckillId=1000,
        // now=0, start=0, end=0}
    }

    //集成测试代码完整逻辑，注意 可重复执行，
    @Test
    public void testSeckillLogic() throws Exception {
        long seckillId  = 1001L;
        Exposer exposer = seckillService.exposeSeckillUrl(seckillId);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long userPhone = 15368079036L;
            String md5 = exposer.getMd5();
            try {
                SeckillExcution excutionTest = seckillService.excuteSeckill(seckillId,userPhone,md5);
                logger.info("result={}",excutionTest);
            } catch (RepeatSeckillException e) {
                logger.info(e.getMessage());
            }catch (SeckillCloseExcepion e) {
                logger.info(e.getMessage());
            }
        }else{
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }


}