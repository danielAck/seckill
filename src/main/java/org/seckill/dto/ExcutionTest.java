package org.seckill.dto;

import org.seckill.enums.SeckillStateEnum;

/**
 * Created by 张柏桦 on 2017/7/6.
 */
public class ExcutionTest {
    private long seckillId;
    private int state;
    private String stateInfo;

    public ExcutionTest(long seckillId, SeckillStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
}
