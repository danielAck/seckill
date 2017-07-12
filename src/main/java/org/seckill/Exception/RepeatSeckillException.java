package org.seckill.Exception;


/**
 * 重复秒杀异常（运行期异常）
 * Created by 张柏桦 on 2017/7/5.
 */
public class RepeatSeckillException extends SeckillException {
    public RepeatSeckillException(String message) {
        super(message);
    }

    public RepeatSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
