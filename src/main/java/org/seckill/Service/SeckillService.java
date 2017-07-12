package org.seckill.Service;

import org.seckill.Exception.RepeatSeckillException;
import org.seckill.Exception.SeckillCloseExcepion;
import org.seckill.Exception.SeckillException;
import org.seckill.dto.ExcutionTest;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;

import java.util.List;

/**
 * 业务接口：站在 ‘使用者’ 角度设计接口
 * 三个方面: 1.方法定义粒度
 * 2.参数
 * 3.返回类型（return 类型/抛出异常）
 */

public interface SeckillService {

    //查询所有秒杀记录
    List<Seckill> getSeckillList();

    //查询单个秒杀记录
    Seckill getById(long seckillId);

    //输出秒杀接口的地址，即暴露秒杀接口
    // 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀开启时间
    Exposer exposeSeckillUrl(long seckillId);

    //*执行秒杀操作
    SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, SeckillCloseExcepion, RepeatSeckillException;
}
