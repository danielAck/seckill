package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by 张柏桦 on 2017/7/4.
 */
public interface SuccessKilledDao {

    //插入购买明细，可过滤重复
    //插入的结果集数量，返回 0 表示插入失败
    int insertSuccessKilled(@Param("seckillId") long seckillId ,@Param("userPhone") long userPhone);

    //根据id查询SuccessKilled并携带秒杀产品对象实体 参数传递 SeckillId 和 userPhone的原因是：
    // 数据库success_killed采用 ID 和 userPhone 双主键 共同确定一条元素
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
