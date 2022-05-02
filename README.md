



## 创建数据表

```mysql
mysql> create database se;
Query OK, 1 row affected (0.01 sec)

mysql> use se
Database changed
```



```sql
create table `medicine`( 
    `id` char(10), 
    `storehouse_id` char(2),
    `name` varchar(100), 
    `function` varchar(100),
    `effective_date` date,
    `price` float,
    `stock` int,
    primary key(id))engine=InnoDB default charset= utf8;
```

![image-20220502124355036](https://s2.loli.net/2022/05/02/e5hkFTIcYbfLVo3.png)

```sql
create table `administrator`( 
    `ano` char(10),
    `aname` char(100),
    `password` char(100), 
    `phonenumber` char(100),
    primary key(ano))engine=InnoDB default charset= utf8;
```


