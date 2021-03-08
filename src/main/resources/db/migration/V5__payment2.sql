alter table zbry_gz change in_time year int null comment '年份';
alter table zbry_gz add month int null comment '月份';
alter table pqry_gz change in_time year int null comment '年份';
alter table pqry_gz add month int null comment '月份';
alter table dlxpry_gz change in_time year int null comment '年份';
alter table dlxpry_gz add month int null comment '月份';
alter table normal_assess
    add in_time varchar(255) null comment '时间';