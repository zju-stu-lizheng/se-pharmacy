# 软工大程


## 一、创建数据表

```mysql
mysql> create database se;
Query OK, 1 row affected (0.01 sec)

mysql> use se
Database changed
```

<img src="https://s2.loli.net/2022/05/07/FgGBVzX5leHLbfP.png" alt="软工前端table" style="zoom:80%;" />

**创建表的`sql`语句请看`medicine.sql`**



### 清空表格操作

```mysql
delete from log;
delete from medicine;
delete from administrator;
delete from shoppingCart;
delete from picture;
```

### 插入管理员

```sql
insert into administrator values('001','lizheng','yp','123456');
```



### 插入图片

```sql
 insert into picture values('阿司匹林','国药','https://s2.loli.net/2022/05/06/q7ulP6FDjtVOMQE.png');
 insert into picture values('头孢','国药','https://s2.loli.net/2022/05/06/Fp3MwJu1U8tbi96.png');
```



## 二、API list

### 管理员 -> 药房

* 入库一种药品，相当于数据库`medicine`表项增加一项

```java
/**
	 * 插入一条药品信息
	 * 
	 * @param id             : 药品 id
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param brand          : 药品 厂商
	 * @param name           : 药品 名字
	 * @param function       : 药品 作用
	 * @param price          : 药品 单价
	 * @param stock          : 药品 库存(入库数量)
	 * @return : true(插入成功)/false(插入失败)
	 */
public boolean insertMedicine(String id, String effective_date, String storehouse_id, String brand, String name,
                              String function, float price, int stock);
```

* 删除一条药品信息

```java
/**
	 * 删除一条药品信息(如不存在，则返回false)
	 * 
	 * @param id             : 药品 id
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param stock          : 药品 库存(入库数量)
	 * @return : true(删除成功)/false(删除失败)
	 */
public boolean deleteMedicine(String id, String storehouse_id, String effective_date) throws SQLException;
```

* 查询药品信息

  ```java
  /**
  	 * 查询所有药品记录
  	 * 
  	 * @return : list(python)格式的药品记录
  	 */
  public String queryMedicine();
  ```

返回例子：`[m_id,brand,name,function,price,url]`

  ```python
  [["002","国药","头孢","头孢就酒，越喝越勇",25.0,"https://s2.loli.net/2022/05/06/Fp3MwJu1U8tbi96.png"],["001","国药","阿司匹林","解热镇痛",25.0,"https://s2.loli.net/2022/05/06/q7ulP6FDjtVOMQE.png"]]
  ```

  

* 药品出库

```java
  /**
  	 * 药品出库(如不存在，则返回false)
  	 * 
  	 * @param id             : 药品 id
  	 * @param storehouse_id  : 库房 id <char(2)>
  	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
  	 * @param num            : 药品 出库数量
  	 * @return : true(出库成功)/false(出库失败)
  	 */
  public boolean deliveryMedicine(String id, String storehouse_id, String effective_date, int num)
      throws SQLException;
```

  



### 患者 -> 药房

* 查询购物车中的药品清单

```java
/**
	 * 查询目标用户购物车中的药品列表
	 * 
	 * @param user_id     : 用户 id
	 * @param branch_name : 药房 id
	 * @return : list格式的药品记录
	 */
public String queryShoppingCart(String user_id, String branch_name);
```

返回例子：`[m_id,brand,name,function,price,num]`

  ```python
  [["001","国药","阿司匹林","解热镇痛",25.0,2],["002","国药","头孢","头孢就酒，越喝越勇",24.0,3]]
  ```

* 加入购物车操作
  * String user_id : 用户 id
  * String medicine_id : 药品 id
  * String storehouse_id : 药房 id
  * int num ：数量

```java
/**
	 * 往购物车内插入一条药品信息
	 * 
	 * @param user_id       : 用户 id
	 * @param medicine_id   : 药品 id
	 * @param storehouse_id : 药房 id
	 * @param num           : 数量
	 * @return true(插入成功)/false(插入失败)
	 * @throws SQLException
	 */
public boolean addShoppingCart(String user_id, String medicine_id, String storehouse_id, int num)
    throws SQLException 
```




> Others:

* **Eclipse**中**格式化代码的快捷键**是 Ctrl+Shift+F
* `git pull` 之前请先`git add.` && `git commit -m "your commit"`
* 图库使用`sm.ms`
