package org.seckill.Service.impl;

import org.seckill.Exception.RepeatSeckillException;
import org.seckill.Exception.SeckillCloseExcepion;
import org.seckill.Exception.SeckillException;
import org.seckill.Service.SeckillService;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.ExcutionTest;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 实现SeckillServise接口
 * Created by 张柏桦 on 2017/7/5.
 */
//@Component
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //对 SeckillDao 和 SuccessKilledDao 的依赖，可以用 Spring 解决
    //注入Service依赖
    @Autowired//@Resource,@Inject
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5盐值字符串，用于混淆MD5
    public final String salt = "fsdjo^^csdddwefqw>??9u2we<trj(UD()U)8908r3~~!@";

    //获得 加密后的 MD5字符串
    private String getMD5(long seckillId) {

        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5;
    }

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exposeSeckillUrl(long seckillId) {

        Seckill seckill;
        seckill = getById(seckillId);

        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();

        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转化特定字符串的过程，不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }



    @Transactional
    /**
     * 使用注解控制事务方法的优点
     * 1： 开发团队达成一致约定，明确标注事务方法的编程风格
     * 2： 保证事务方法的执行时间尽可能短，不要穿插其他的网络操作，RPC/HTTP请求,或剥离到事务方法外部
     * 3： 不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     */
    public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, SeckillCloseExcepion, RepeatSeckillException {

        if( md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑 + 记录购买行为
        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updateCount <= 0 ){
                //没有更新到数据记录，即秒杀已经结束
                throw new SeckillCloseExcepion("seckill closed");
            }else {
                //减库存成功，记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
                //唯一的验证 seckillId + userPhone
                if (insertCount <= 0 ){
                    //重复秒杀
                    throw new RepeatSeckillException("seckill repeate");
                }else{
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    //return new ExcutionTest(seckillId,SeckillStateEnum.SUCCESS);
                    return new SeckillExcution(seckillId, SeckillStateEnum.SUCCESS,successKilled);
                }
            }
        } catch (SeckillCloseExcepion e1) {
            throw e1;
        } catch (RepeatSeckillException e2) {
            throw e2;
        } catch
         (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常
            throw new SeckillException("seckill inner exception:"+e.getMessage());
        }
    }
}
