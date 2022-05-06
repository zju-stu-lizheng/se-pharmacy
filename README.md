# 软工大程


## 一、创建数据表

```mysql
mysql> create database se;
Query OK, 1 row affected (0.01 sec)

mysql> use se
Database changed
```

![软工前端table](https://s2.loli.net/2022/05/06/5j31CupHbfasw9A.png)



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
    `storehouse_id` char(2),
	primary key(user_id,medicine_id,storehouse_id)
)engine=InnoDB default charset= utf8;
```



```sql
create table `picture`(
    `name` varchar(100),
    `brand` varchar(100),
    `url` varchar(256),
    primary key(name,brand)
)engine=InnoDB default charset= utf8;
```



### 清空表格操作

```mysql
delete from log;
delete from medicine;
delete from administrator;
delete from shoppingCart;
delete from picture;
```



### 插入图片

```sql
 insert into picture values('阿司匹林','国药','https://s2.loli.net/2022/05/06/q7ulP6FDjtVOMQE.png');
 insert into picture values('头孢','国药','https://s2.loli.net/2022/05/06/Fp3MwJu1U8tbi96.png');
```



## 二、数据包格式

### 管理员 -> 药房

* 入库一种药品，相当于数据库`medicine`表项增加一项


```json
{
    "op" : "insert_medicine",	// insert medicial option
    "ano" : "001",		//char(10),管理员id
    "id" : "001",		// char(10),药品id
    "storehouse_id" : "01",			//char(2),药房id
    "effective_date" : "2022-05-23", //YYYY-MM-DD
    "brand" : "国药",				  //char(100)
    "name" : "阿司匹林",			//char(100)
    "function" : "解热镇痛",		//char(100)
    "price" : 25.0,				  //float
    "stock" : 25				 //int
}
```

* 删除一条药品信息

```json
{
    "op" : "delete_medicine",	
    "ano" : "001",
    "id" : "001",		// char(10)
    "storehouse_id" : "01",			//char(2)
    "effective_date" : "2022-05-23"	//YYYY-MM-DD
}
```

* 查询药品信息
  * 需要告知管理员id（是谁进行查询）


```json
{
    "op" : "query_medicine",	
    "ano" : "001"
}
```

### 药房 -> 管理员

* 出入库的返回包
  * response 是 boolean 型变量, true/false

```json
{
	"op" : "ret_update",
	"response" : true
}
```

* 查询药品的返回包
  * 以`csv`格式返回药品信息。

```json
{
	"op" : "ret_query",
	"medicine_list" : "001,2022-05-23,01,国药,阿司匹林,解热镇痛,25.0,25\n"
}
```

### 患者 -> 药房

* 查询购物车中的药品清单

```json
{
    "op" : "query_shoppingCart",	
    "user_id" : "001"
}
```

* 加入购物车操作
  * String user_id : 用户 id
  * String medicine_id : 药品 id
  * String storehouse_id : 药房 id
  * int num ：数量

````json
{
    "op" : "insert_shoppingCart",	
    "user_id" : "001",
    "medicine_id" : "001",
    "storehouse_id" : "01",
    "num" : 5
}
````

### 药房 -> 患者

* 加入购物车的返回包

```json
{"op":"ret_update","response":true}
```

* 查询购物车返回包
  * 以 csv 格式返回，每行记录格式为`(medicine_id,storehouse_id,num)`

```json
{"op":"ret_query","medicine_list":"001,01,5\n"}
```






> Others:

* **Eclipse**中**格式化代码的快捷键**是 Ctrl+Shift+F
* `git pull` 之前请先`git add.` && `git commit -m "your commit"`
* 图库使用`sm.ms`
