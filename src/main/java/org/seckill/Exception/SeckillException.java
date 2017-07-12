package org.seckill.Exception;

/**
 * 秒杀相关异常
 * Created by 张柏桦 on 2017/7/5.
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
