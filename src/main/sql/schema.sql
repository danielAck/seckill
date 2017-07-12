-- 数据库 初始化脚本
-- 记录数据库的创建代码

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
--创建秒杀库存表
create table seckill(
  seckill_id bigint NOT NULL AUTO_INCREMENT comment'商品库存id',
  name varchar(120) NOT NULL comment'商品名称',
  number int NOT NULL comment'库存数量',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment'创建时间',
  start_time timestamp NOT NULL comment'秒杀开启时间',
  end_time timestamp NOT NULL  comment'秒杀结束时间',
  primary key (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 comment='秒杀库存表';

-- 初始化数据
insert into
    seckill(name,number,start_time,end_time)
values
    ('1000元秒杀iPhone6','100','2017-11-01 00:00:00','2017-11-02 00:00:00'),
    ('500元秒杀ipad2','200','2017-11-01 00:00:00','2017-11-02 00:00:00'),
    ('300元秒杀小米4','300','2017-11-01 00:00:00','2017-11-02 00:00:00'),
    ('200元秒杀红米NOTE','400','2017-11-01 00:00:00','2017-11-02 00:00:00');

-- 设计秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed(
  seckill_id bigint NOT NULL COMMENT '秒杀商品id',
  user_phone int NOT NULL COMMENT '用户手机号',
  state tinyint NOT NULL DEFAULT -1 COMMENT '状态标示：-1：无效 0：成功 1：已付款 2：已发货',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),
  key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- 手写DDL的原因
-- 可以记录每次上线的DDL修改
-- 上线 V1.1
ALTER TABLE seckill
DROP INDEX idx_create_time
ADD INDEX idx_c_s(strat_time,create_time);

-- 上线V1.2
-- 记录DDL
