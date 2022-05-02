# 软工大程


## 一、创建数据表

```mysql
mysql> create database se;
Query OK, 1 row affected (0.01 sec)

mysql> use se
Database changed
```

![image-20220502172252083](https://s2.loli.net/2022/05/02/9ayOUQwqpgS2khN.png)

```sql
create table `medicine`( 
    `id` char(10), 
    `effective_date` date,/*YYYY-MM-DD*/
    `storehouse_id` char(2),
    `brand` varchar(100),
    `name` varchar(100), 
    `function` varchar(100),
    `price` float,
    `stock` int,
    primary key(id,effective_date,storehouse_id))engine=InnoDB default charset= utf8;
```



```sql
create table `administrator`( 
    `ano` char(10),
    `aname` char(100),
    `password` char(100), 
    `phonenumber` char(100),
    primary key(ano))engine=InnoDB default charset= utf8;
```



```sql
create table `log`( 
    `ano` char(10),
    `option` char(100),
    `id` char(10), 
    `effective_date` date,/*YYYY-MM-DD*/
    `storehouse_id` char(2),
    `stock` int,
    foreign key (ano) references administrator(ano)
    )engine=InnoDB default charset= utf8;
```



```sql
create table shoppingCart(
	`user_id` char(10),
	`medicine_id` char(10),
	`num` int,
	primary key(user_id,medicine_id)
)engine=InnoDB default charset= utf8;
```





```mysql
delete from log;
delete from medicine;
delete from administrator;
```

## 二、数据包格式

* 入库一种药品，相当于数据库`medicine`表项增加一项

```json
{
    "op" : "insert",	// insert medicial option
    "id" : "001",	// char(10)
    "effective_date" : "2022-05-23",	//YYYY-MM-DD
    "storehouse_id" : "01",			//char(2)
    "brand" : "国药",					//char(100)
    "name" : "阿司匹林",			//char(100)
    "function" : "解热镇痛",		//char(100)
    "price" : 25.0,				//float
    "stock" : 25				//int
}
```

* 在已入库的基础上，进行增加药品数量操作

```
{
    "op" : "add",	// insert medicial option
    "id" : "001",	// char(10)
    "effective_date" : "2022-05-23",	//YYYY-MM-DD
    "storehouse_id" : "01",			//char(2)
    "brand" : "国药",					//char(100)
    "name" : "阿司匹林",			//char(100)
    "function" : "解热镇痛",		//char(100)
    "price" : 25.0,				//float
    "stock" : 25				//int
}
```





> Others:

* **Eclipse**中**格式化代码的快捷键**是Ctrl+Shift+F

* `git pull` 之前请先`git add.` && `git commit -m "your commit"`
