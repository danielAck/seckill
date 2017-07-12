package org.seckill.entity;

import java.util.Date;

/**
 * Created by 张柏桦 on 2017/7/4.
 */

public class SuccessKilled {

    private long seckillId;
    private long userPhone;
    private short state;
    private Date createTime;
    private Seckill seckill;  //连带成功秒杀订单的信息
                              //包括秒杀商品信息ID， 订单商品名称，订单商品当前剩余配额，秒杀商品开始时间、结束时间、创建时间


    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
