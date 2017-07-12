package org.seckill.Exception;

/**
 * 秒杀关闭异常
 * Created by 张柏桦 on 2017/7/5.
 */
public class SeckillCloseExcepion extends SeckillException {
    public SeckillCloseExcepion(String message) {
        super(message);
    }

    public SeckillCloseExcepion(String message, Throwable cause) {
        super(message, cause);
    }
}
