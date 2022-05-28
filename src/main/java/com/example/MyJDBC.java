package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// To do : bill 增加 订单日期和支付日期(可为空)
// 1. 订单日期是在新增订单时写入
// 2. 支付日期是在支付订单时写入

public class MyJDBC {

	/**
	 * 登录数据库所需的信息:包括驱动器，数据库名称以及登录名、密码
	 */
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/se?useSSL=false&serverTimezone=UTC";
	static final String USERNAME = "root";
	static final String PASSWD = "lizheng";

	// sql
	static Connection connection = null;
	static String anoString = "001";
	static Boolean isAdministrator;

	// 日期格式
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 构造函数，为管理员新建一个对象
	 * 
	 * @param ano : 管理员 id
	 */
	public MyJDBC(String ano) {
		isAdministrator = true;
		anoString = ano;
		connectDatabase();
	}

	/**
	 * 构造函数，为用户新建一个对象
	 */
	public MyJDBC() {
		isAdministrator = false;
		connectDatabase();
	}

	/**
	 * 析构方法，断开数据库连接
	 */
	protected void finalize() {
		// 断开连接
		disconnectDatabase();
		System.out.println("断开连接");
	}

	/**
	 * 删除表格内容，方便下次进行测试
	 * 
	 * @param tableString : 表名
	 */
	public static void doDeleteTable(String tableString) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM " + tableString + ";");
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	/**
	 * 连接JDBC数据库
	 */
	public static void connectDatabase() {
		// 加载驱动程序
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		// 获取与数据库连接的对象-Connetcion
		try {
			// 如果是关闭状态，则打开
			if (connection == null)
				connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWD);
			if (!connection.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			// Set auto commit as false.
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 断开数据库连接
	 */
	public static void disconnectDatabase() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 确认管理员登录的账户密码是否正确
	 * 
	 * @param ano    : 账号id
	 * @param passwd : 账号密码
	 * @return :true of false
	 */
	public static boolean ensureLogin(String ano, String passwd) {
		String pwd = null;
		Statement statement;
		ResultSet resultSet;
		try {
			// 获取执行sql语句的statement对象
			statement = connection.createStatement();
			// 执行sql语句,拿到结果集
			resultSet = statement.executeQuery("SELECT password FROM administrator Where ano = \"" + ano + "\";");
			// 遍历结果集，得到数据
			if (resultSet.next()) {
				pwd = resultSet.getString(1);
			} else {
				System.out.println("无该用户:" + ano);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (passwd.equals(pwd)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 插入一条管理员信息
	 * 
	 * @param ano         : 管理员 id
	 * @param aname       : 管理员 昵称
	 * @param password    : 管理员 密码
	 * @param phonenumber : 管理员 联系方式
	 * @return : true(插入成功)/false(插入失败)
	 */
	public static boolean insertAdministator(String ano, String aname, String password, String phonenumber) {
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO administrator" + " VALUES('" + ano + "','" + aname + "','" + password
					+ "','" + phonenumber + "');");
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 插入一条药品信息
	 * 
	 * @param id             : 药品 id
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param brand          : 药品 厂商
	 * @param name           : 药品 名字
	 * @param function       : 药品 作用
	 * @param dosage         : 用法用量
	 * @param banned         : 禁用人群
	 * @param price          : 药品 单价
	 * @param stock          : 药品 库存(入库数量)
	 * @return : true(插入成功)/false(插入失败)
	 */
	public static boolean insertMedicine(String id, String effective_date, String storehouse_id, String brand,
			String name, String function, String dosage, String banned, float price, int stock) {
		String sqlExecutionString = "";
		try {
			Statement statement = connection.createStatement();
			// insert into database
			sqlExecutionString = String.format(
					"INSERT INTO medicine VALUES('%s','%s','%s','%s','%s','%s','%s','%s',%f,%d);", id, effective_date,
					storehouse_id, brand, name, function, dosage, banned, price, stock);
			statement.executeUpdate(sqlExecutionString);
			// add a log
			String option = "insert medicine";
			sqlExecutionString = String.format("INSERT INTO log VALUES('%s','%s','%s','%s','%s',%d);", anoString,
					option, id, effective_date, storehouse_id, stock);
			statement.executeUpdate(sqlExecutionString);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 删除一条药品信息(如不存在，则返回false)
	 * 
	 * @param id             : 药品 id
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param stock          : 药品 库存(入库数量)
	 * @return : true(删除成功)/false(删除失败)
	 */
	public static boolean deleteMedicine(String id, String storehouse_id, String effective_date) throws SQLException {
		String sqlExecutionString = "";
		String sqlLogString = "";
		ResultSet resultSet;
		Statement statement;
		try {
			// 获取执行sql语句的statement对象
			statement = connection.createStatement();
			// 查询该药品的库存
			resultSet = statement.executeQuery("SELECT stock FROM medicine WHERE id = \"" + id + "\";");
			// 判断是否存在该药品，若不存在返回false
			if (!resultSet.next())
				return false;
			// 记录该药品下架前的库存
			int stock = Integer.valueOf(resultSet.getString(1));
			// 删除该药品的记录
			sqlExecutionString = String.format(
					"DELETE FROM medicine WHERE id='%s' AND storehouse_id='%s' AND effective_date='%s';", id,
					storehouse_id, effective_date);
			statement.executeUpdate(sqlExecutionString);
			// 添加到日志
			sqlLogString = String.format("INSERT INTO log VALUES('%s','%s','%s','%s','%s',%d);", anoString, "delete",
					id, effective_date, storehouse_id, stock);
			statement.executeUpdate(sqlLogString);
			// 两条语句均成功则提交
			connection.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
			// 否则事务回滚
			connection.rollback();
			return false;
		}
		return true;
	}

	/**
	 * 药品出库(如不存在，则返回false)
	 * 
	 * @param id             : 药品 id
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param num            : 药品 出库数量
	 * @return : true(出库成功)/false(出库失败)
	 */
	public static boolean deliveryMedicine(String id, String storehouse_id, String effective_date, int num)
			throws SQLException {
		String sqlExecutionString = "";
		String sqlLogString = "";
		ResultSet resultSet;
		Statement statement;
		try {
			// 获取执行sql语句的statement对象
			statement = connection.createStatement();
			// 查询该药品的库存
			resultSet = statement.executeQuery("SELECT stock FROM medicine WHERE id = \"" + id + "\";");
			// 判断是否存在该药品，若不存在返回false
			if (!resultSet.next())
				return false;
			// 计算该药品出库后的库存
			int stock = Integer.valueOf(resultSet.getString(1)) - num;
			// 更新该药品库存
			sqlExecutionString = String.format(
					"UPDATE medicine set stock = %d WHERE id='%s' AND storehouse_id='%s' AND effective_date='%s';",
					stock, id, storehouse_id, effective_date);
			statement.executeUpdate(sqlExecutionString);
			// 更新日志
			sqlLogString = String.format("INSERT INTO log VALUES('%s','%s','%s','%s','%s',%d);", anoString, "delivery",
					id, effective_date, storehouse_id, num);
			statement.executeUpdate(sqlLogString);
			// 两条语句均成功则提交
			connection.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
			// 否则事务回滚
			connection.rollback();
			return false;
		}
		return true;
	}

	/**
	 * 对已有药品进行增加操作
	 * 
	 * @param id             : 药品 id
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param stock          : 药品 库存(入库数量)
	 * @return : true(插入成功)/false(插入失败)
	 */
	public static boolean addMedicine(String id, String effective_date, String storehouse_id, int stock) {
		int remainStock = 0;
		Statement statement;
		ResultSet resultSet;
		String sqlExecutionString = "";
		// 获取执行sql语句的statement对象
		try {
			statement = connection.createStatement();
			// 执行sql语句,拿到结果集
			sqlExecutionString = String.format(
					"SELECT stock FROM medicine where id = '%s' and effective_date = '%s' and storehouse_id = '%s';",
					id, effective_date, storehouse_id);
			resultSet = statement.executeQuery(sqlExecutionString);
			// 遍历结果集，得到数据
			if (resultSet.next()) {
				remainStock = Integer.valueOf(resultSet.getString(1));
				// update database
				stock += remainStock;
				sqlExecutionString = String.format(
						"UPDATE medicine set stock = %d where id = '%s' and effective_date = '%s' and storehouse_id = '%s';",
						stock, id, effective_date, storehouse_id);
			} else {
				return false;
			}
			statement = connection.createStatement();
			statement.executeUpdate(sqlExecutionString);
			// add a log
			String option = "add medicine";
			sqlExecutionString = String.format("INSERT INTO log VALUES('%s','%s','%s','%s','%s',%d);", anoString,
					option, id, effective_date, storehouse_id, stock);
			statement.executeUpdate(sqlExecutionString);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 返回医院所有分部
	 * 
	 * @return "[\"Branch1\", \"Branch2\"]"
	 */
	public static String getAllBranch() {
		// 从medicine表中获取 : storehouse_id
		String sqlQueryString = "select distinct storehouse_id from medicine;";
		StringBuffer queryResultBuffer = new StringBuffer("[");
		int i = 0;
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(sqlQueryString);
			while (rs.next()) {
				/* 根据 属性获取该条记录相应的值 */
				String storehouse_id = rs.getString("storehouse_id");

				if (i == 0)
					queryResultBuffer.append("\"" + storehouse_id + "\"");
				else {
					queryResultBuffer.append(",\"" + storehouse_id + "\"");
				}
				/* 将每条记录添加入 buffer */
				i++;
			}
			queryResultBuffer.append("]");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return queryResultBuffer.toString();
	}

	/**
	 * 查询所有药品记录
	 * 
	 * @return : list(python)格式的药品记录
	 */
	public static String queryMedicine() {
		String sqlQueryString = "select id,name,brand,function,dosage,banned,price,url,sum(stock) as allStock from medicine natural join picture group by name,brand;";
		StringBuffer queryResultBuffer = new StringBuffer("[");
		int i = 0, j = 0;
		String tmpString;
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(sqlQueryString);
			while (rs.next()) {
				/* 根据 属性获取该条记录相应的值 */
				String id = rs.getString("id");
				String brand = rs.getString("brand");
				String name = rs.getString("name");
				String dosage = rs.getString("dosage");
				String banned = rs.getString("banned");
				tmpString = rs.getString("function");
				String url = rs.getString("url");
				float price = rs.getFloat("price");
				int allStock = rs.getInt("allStock");
				StringBuffer function = new StringBuffer("");
				char[] c = tmpString.toCharArray();

				for (j = 0; j < c.length; j++) {
					if (c[j] == '"') {
						function.append("\\\"");
					} else if (c[j] == '\\') {
						function.append("\\\\");
					} else {
						function.append(c[j]);
					}
				}
				if (i == 0)
					tmpString = "[\"" + id + "\",\"" + brand + "\",\"" + name + "\",\"" + function + "\",\"" + dosage
							+ "\",\"" + banned + "\"," + price + ",\"" + url + "\"," + allStock + "]";
				else {
					tmpString = ",[\"" + id + "\",\"" + brand + "\",\"" + name + "\",\"" + function + "\",\"" + dosage
							+ "\",\"" + banned + "\"," + price + ",\"" + url + "\"," + allStock + "]";
				}
				/* 将每条记录添加入 buffer */
				queryResultBuffer.append(tmpString);
				i++;
			}
			queryResultBuffer.append("]");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryResultBuffer.toString();
	}

	/**
	 * 查询目标用户购物车中的药品列表
	 * 
	 * @param user_id     : 用户 id
	 * @param branch_name : 药房 id
	 * @return : list格式的药品记录
	 */
	public static String queryShoppingCart(String user_id, String branch_name) {
		String sqlQueryString = String.format(
				"select * from shoppingCart natural join medicine where user_id = '%s' and medicine_id = id and storehouse_id = '%s' group by storehouse_id,user_id,medicine_id;",
				user_id, branch_name);
//		System.out.println(sqlQueryString);
		StringBuffer BillItemBuffer = new StringBuffer("[");
		StringBuffer queryResultBuffer = new StringBuffer();
		int i = 0, j = 0;
		String tmpString;
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(sqlQueryString);
			while (rs.next()) {
				/* 根据 属性获取该条记录相应的值 */
				String id = rs.getString("medicine_id");
				String brand = rs.getString("brand");
				String name = rs.getString("name");
				tmpString = rs.getString("function");
				float price = rs.getFloat("price");
				int num = rs.getInt("num");
				StringBuffer function = new StringBuffer("");
				char[] c = tmpString.toCharArray();

				for (j = 0; j < c.length; j++) {
					if (c[j] == '"') {
						function.append("\\\"");
					} else if (c[j] == '\\') {
						function.append("\\\\");
					} else {
						function.append(c[j]);
					}
				}
				if (i == 0)
					tmpString = "[\"" + id + "\",\"" + brand + "\",\"" + name + "\",\"" + function + "\"," + price + ","
							+ num + "]";
				else {
					tmpString = ",[\"" + id + "\",\"" + brand + "\",\"" + name + "\",\"" + function + "\"," + price
							+ "," + num + "]";
				}
				/* 将每条记录添加入 buffer */
				BillItemBuffer.append(tmpString);
				queryResultBuffer.append(tmpString);
				i++;
			}
			BillItemBuffer.append("]");
			// 账单,账单号,排队号,柜台号
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryResultBuffer.toString();
	}

	/**
	 * 根据有效日期和购物车中药品id 获取具体需要出库药品的信息
	 * 
	 * @param user_id : 用户 id
	 * @return ArrayList<MedicineBillEntry> bill;
	 */
	public static ArrayList<MedicineBillEntry> getBillEntries(String user_id) {
		ArrayList<MedicineBillEntry> list = new ArrayList<MedicineBillEntry>();
		// 从购物车中搜索得到该用户需要买的药
		String sqlQueryString = String.format("select * from shoppingCart where user_id = '%s';", user_id);
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(sqlQueryString);
			while (rs.next()) {
				/* 根据 属性获取该条记录相应的值 */
				String medicine_id = rs.getString("medicine_id");
				String storehouse_id = rs.getString("storehouse_id");
				int num = rs.getInt("num");

				// 针对每一种药,从所有药品中挑选出保质期最短的药品
				String sqlQueryString2 = String.format(
						"select brand,effective_date from medicine where id = '%s' AND storehouse_id = '%s' ORDER BY effective_date ASC;",
						medicine_id, storehouse_id);
				// 执行搜索语句，我只取第一条记录
				Statement stmt2 = connection.createStatement();
				ResultSet dateResultSet = stmt2.executeQuery(sqlQueryString2);
				dateResultSet.next();
				/* 根据 属性获取该条记录相应的值 */
				String brand = dateResultSet.getString("brand");
				String effective_date = dateResultSet.getString("effective_date");
				MedicineBillEntry tmpBillEntry = new MedicineBillEntry(medicine_id, num, brand, storehouse_id,
						effective_date);
				list.add(tmpBillEntry);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 往购物车内插入一条药品信息 : 将数量设定成指定的数量
	 * 
	 * @param user_id       : 用户 id
	 * @param medicine_id   : 药品 id
	 * @param storehouse_id : 药房 id
	 * @param num           : 数量
	 * @return true(修改成功)/false(修改失败)
	 * @throws SQLException
	 */
	public static boolean setShoppingCart(String user_id, String medicine_id, String storehouse_id, int num)
			throws SQLException {
		String sqlExecutionString = "";
		String sqlQueryString = "";
		ResultSet resultSet;
		Statement statement;
		Boolean hasBillBoolean;

		try {
			// 获取执行sql语句的statement对象
			statement = connection.createStatement();
			// 第一步:判断该用户在该药房是否有未支付的账单.如果有：获取该账单的账单号;如果无：新建一个账单，获取这个账单的账单号
			sqlQueryString = String.format(
					"SELECT bill_id FROM bill WHERE user_id = '%s' AND storehouse_id = '%s' AND isPaid = 0;", user_id,
					storehouse_id);
			resultSet = statement.executeQuery(sqlQueryString);
			int bill_id = -1;
			if (!resultSet.next()) {
				hasBillBoolean = false;
			} else {
				hasBillBoolean = true;
				bill_id = Integer.valueOf(resultSet.getString(1));
			}

			if (hasBillBoolean == false) {
				Date date = new Date();
				String order_date = formatter.format(date); // 当前日期
				sqlExecutionString = String.format(
						"INSERT INTO bill (user_id,storehouse_id,order_date,isPaid ) VALUES('%s','%s','%s',0);",
						user_id, storehouse_id, order_date);
				System.out.println("查询bill表的sql语句:" + sqlExecutionString);
				statement.executeUpdate(sqlExecutionString);
				connection.commit();

				resultSet = statement.executeQuery(sqlQueryString);
				if (!resultSet.next()) {
					hasBillBoolean = false;
				} else {
					hasBillBoolean = true;
					bill_id = Integer.valueOf(resultSet.getString(1));
				}
			}

			// 第二步: 往shoppingcart中插入该条数据
			sqlExecutionString = String.format(
					"SELECT SUM(stock) FROM medicine WHERE id = '%s' AND storehouse_id = '%s' GROUP BY id;",
					medicine_id, storehouse_id);
			// 查询该药品的库存
			resultSet = statement.executeQuery(sqlExecutionString);
			// 判断是否存在该药品，若不存在返回false
			if (!resultSet.next()) {
				return false;
			} else {
				if (resultSet.getString(1) == null)
					return false;
			}
			// 记录该药品当前的库存
			int stock = Integer.valueOf(resultSet.getString(1));
			// 查询购物车中是否已经有该药品
			resultSet = statement.executeQuery(String.format(
					"SELECT num FROM shoppingCart WHERE medicine_id = '%s' AND user_id ='%s' AND storehouse_id = '%s';",
					medicine_id, user_id, storehouse_id));
			// 判断是否存在该药品
			if (resultSet.next()) {
				// 如果购物车数量大于库存，返回false
				if (stock < num)
					return false;
				// 更新购物车的记录
				sqlExecutionString = String.format(
						"UPDATE shoppingCart set num = %d WHERE user_id='%s' AND medicine_id='%s' AND storehouse_id = '%s' AND bill_id = %d;",
						num, user_id, medicine_id, storehouse_id, bill_id);
				statement.executeUpdate(sqlExecutionString);
			} else {
				// 如果购物车数量大于库存，返回false
				if (stock < num)
					return false;
				// 将记录插入到购物车表中
				sqlExecutionString = String.format("INSERT INTO shoppingCart VALUES('%s','%s', %d,'%s',%d);", user_id,
						medicine_id, num, storehouse_id, bill_id);
				statement.executeUpdate(sqlExecutionString);
			}
			// 成功则提交
			connection.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
			// 失败则事务回滚
			connection.rollback();
			return false;
		}
		return true;
	}

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
	public static boolean addShoppingCart(String user_id, String medicine_id, String storehouse_id, int num)
			throws SQLException {
		String sqlExecutionString = "";
		String sqlQueryString = "";
		ResultSet resultSet;
		Statement statement;
		Boolean hasBillBoolean;

		try {
			// 获取执行sql语句的statement对象
			statement = connection.createStatement();
			// 第一步:判断该用户在该药房是否有未支付的账单.如果有：获取该账单的账单号;如果无：新建一个账单，获取这个账单的账单号
			sqlQueryString = String.format(
					"SELECT bill_id FROM bill WHERE user_id = '%s' AND storehouse_id = '%s' AND isPaid = 0;", user_id,
					storehouse_id);
			resultSet = statement.executeQuery(sqlQueryString);
			int bill_id = -1;
			if (!resultSet.next()) {
				hasBillBoolean = false;
			} else {
				hasBillBoolean = true;
				bill_id = Integer.valueOf(resultSet.getString(1));
			}

			if (hasBillBoolean == false) {
				Date date = new Date();
				String order_date = formatter.format(date); // 当前日期
				sqlExecutionString = String.format(
						"INSERT INTO bill (user_id,storehouse_id,order_date,isPaid ) VALUES('%s','%s','%s',0);",
						user_id, storehouse_id, order_date);
//				System.out.println(sqlExecutionString);
				statement.executeUpdate(sqlExecutionString);
				connection.commit();

				resultSet = statement.executeQuery(sqlQueryString);
				if (!resultSet.next()) {
					hasBillBoolean = false;
				} else {
					hasBillBoolean = true;
					bill_id = Integer.valueOf(resultSet.getString(1));
				}
			}

			// 第二步: 往shoppingcart中插入该条数据
			sqlExecutionString = String.format(
					"SELECT SUM(stock) FROM medicine WHERE id = '%s' AND storehouse_id = '%s' GROUP BY id;",
					medicine_id, storehouse_id);
			// 查询该药品的库存
			resultSet = statement.executeQuery(sqlExecutionString);
			// 判断是否存在该药品，若不存在返回false
			if (!resultSet.next()) {
				return false;
			} else {
				if (resultSet.getString(1) == null)
					return false;
			}
			// 记录该药品当前的库存
			int stock = Integer.valueOf(resultSet.getString(1));
			// 查询购物车中是否已经有该药品
			resultSet = statement.executeQuery(String.format(
					"SELECT num FROM shoppingCart WHERE medicine_id = '%s' AND user_id ='%s' AND storehouse_id = '%s';",
					medicine_id, user_id, storehouse_id));
			// 判断是否存在该药品
			if (resultSet.next()) {
				num += Integer.valueOf(resultSet.getString(1));
				// 如果购物车数量大于库存，返回false
				if (stock < num)
					return false;
				// 更新购物车的记录
				sqlExecutionString = String.format(
						"UPDATE shoppingCart set num = %d WHERE user_id='%s' AND medicine_id='%s' AND storehouse_id = '%s' AND bill_id = %d;",
						num, user_id, medicine_id, storehouse_id, bill_id);
				statement.executeUpdate(sqlExecutionString);
			} else {
				// 如果购物车数量大于库存，返回false
				if (stock < num)
					return false;
				// 将记录插入到购物车表中
				sqlExecutionString = String.format("INSERT INTO shoppingCart VALUES('%s','%s', %d,'%s',%d);", user_id,
						medicine_id, num, storehouse_id, bill_id);
				statement.executeUpdate(sqlExecutionString);
			}
			// 成功则提交
			connection.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
			// 失败则事务回滚
			connection.rollback();
			return false;
		}
		return true;
	}

	/**
	 * 从购物车中减少要购买的药品的数量
	 * 
	 * @param user_id       : 用户 id
	 * @param medicine_id   : 药品 id
	 * @param storehouse_id : 药房 id
	 * @param num           : 数量
	 * @return true(插入成功)/false(插入失败)
	 * @throws SQLException
	 */
	public static boolean deleteShoppingCart(String user_id, String medicine_id, String storehouse_id, int num)
			throws SQLException {
		String sqlExecutionString = "";
		String sqlQueryString = "";
		ResultSet resultSet;
		Statement statement;
		Boolean hasBillBoolean;
		try {
			// 获取执行sql语句的statement对象
			statement = connection.createStatement();
			// 第一步:判断该用户在该药房是否有未支付的账单.如果有：获取该账单的账单号;如果无：新建一个账单，获取这个账单的账单号
			sqlQueryString = String.format(
					"SELECT bill_id FROM bill WHERE user_id = '%s' AND storehouse_id = '%s' AND isPaid = 0;", user_id,
					storehouse_id);
			resultSet = statement.executeQuery(sqlQueryString);
			int bill_id = -1;
			if (!resultSet.next()) {
				hasBillBoolean = false;
			} else {
				hasBillBoolean = true;
				bill_id = Integer.valueOf(resultSet.getString(1));
			}

			if (hasBillBoolean == false) {
				Date date = new Date();
				String order_date = formatter.format(date); // 当前日期
				sqlExecutionString = String.format(
						"INSERT INTO bill (user_id,storehouse_id,order_date,isPaid ) VALUES('%s','%s','%s',0);",
						user_id, storehouse_id, order_date);
//							System.out.println(sqlExecutionString);
				statement.executeUpdate(sqlExecutionString);
				connection.commit();

				resultSet = statement.executeQuery(sqlQueryString);
				if (!resultSet.next()) {
					hasBillBoolean = false;
				} else {
					hasBillBoolean = true;
					bill_id = Integer.valueOf(resultSet.getString(1));
				}
			}

			// 第二步: 往shoppingcart中修改药品数量
			// 查询该药品的在购物车中的数量
			resultSet = statement.executeQuery(String.format(
					"SELECT num FROM shoppingCart WHERE medicine_id = '%s' AND user_id ='%s' AND storehouse_id = '%s';",
					medicine_id, user_id, storehouse_id));
			// 判断是否存在该药品，若不存在返回false
			if (!resultSet.next())
				return false;
			// 记录该药品在购物车当前的数量
			int oldnum = Integer.valueOf(resultSet.getString(1));
			// 如果数量小于删除量
			if (oldnum < num)
				return false;
			num = oldnum - num;
			if (num == 0) {
				// 将记录从购物车表中删除
				sqlExecutionString = String.format(
						"DELETE FROM shoppingCart WHERE user_id='%s' AND medicine_id='%s' AND storehouse_id='%s' and bill_id = %d;",
						user_id, medicine_id, storehouse_id, bill_id);
				statement.executeUpdate(sqlExecutionString);
			} else {
				// 将记录更新
				sqlExecutionString = String.format(
						"UPDATE shoppingCart set num = %d WHERE user_id='%s' AND medicine_id='%s' AND storehouse_id = '%s' and bill_id = %d;",
						num, user_id, medicine_id, storehouse_id, bill_id);
				statement.executeUpdate(sqlExecutionString);
			}
			// 成功则提交
			connection.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
			// 失败则事务回滚
			connection.rollback();
			return false;
		}
		return true;
	}

	/**
	 * 获取购物车的总价
	 * 
	 * @param user_id       : 用户 id
	 * @param storehouse_id : 药房 id
	 * @return 总价:float
	 */
	public static float getPrice(String user_id, String storehouse_id) {
		float sum;
		ResultSet resultSet;
		Statement statement;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(String.format(
					"SELECT SUM(medicine.price*shoppingCart.num) FROM shoppingCart NATURAL JOIN medicine WHERE user_id ='%s' AND storehouse_id = '%s' AND shoppingCart.medicine_id=medicine.id;",
					user_id, storehouse_id));
			if (!resultSet.next()) {
				return 0.0f;
			} else {
				if (resultSet.getString(1) != null)
					sum = Float.valueOf(resultSet.getString(1));
				else {
					return 0.0f;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0.0f;
		}
		return sum;
	}

	/**
	 * 用户支付订单(Bill)，药品库存需要对应减少，支付完的订单需要进行标记
	 * 
	 * @param user_id       : 用户 id
	 * @param storehouse_id : 药房 id
	 * @return
	 * @throws SQLException
	 */
	public static boolean buyMedicine(String user_id, String storehouse_id) throws SQLException {
		String sqlExecutionString = "";
		String sqlQueryString = "";
		ResultSet resultSet;
		Statement statement;
		Boolean hasBillBoolean;

		try {
			statement = connection.createStatement();
			// 1.根据 用户id和药房id 找出对应的账单
			sqlQueryString = String.format(
					"SELECT bill_id FROM bill WHERE user_id = '%s' AND storehouse_id = '%s' AND isPaid = 0;", user_id,
					storehouse_id);
			resultSet = statement.executeQuery(sqlQueryString);
			int bill_id = -1;
			if (!resultSet.next()) {
				hasBillBoolean = false;
			} else {
				hasBillBoolean = true;
				bill_id = Integer.valueOf(resultSet.getString(1));
			}

			if (hasBillBoolean == false) {
				// report error
				System.out.println("Error:该用户不存在未支付账单");
			} else {
				// 2. 将该账单置为已支付,并补充支付日期
				Date date = new Date();
				String paid_date = formatter.format(date); // 当前日期
				sqlExecutionString = String.format("UPDATE bill set isPaid = 1,paid_date='%s' WHERE bill_id='%s';",
						paid_date, bill_id);
				statement.executeUpdate(sqlExecutionString);
				// 3. 从库存中取出相应药品
				ArrayList<MedicineBillEntry> list = getBillEntries(user_id);
				for (MedicineBillEntry medicineItem : list) {
					if (medicineItem.storehouse_id == storehouse_id) {
						// 取出药品
						sqlExecutionString = String.format(
								"UPDATE medicine set stock = stock - %d where id = '%s' and effective_date = '%s' and storehouse_id = '%s';",
								medicineItem.num, medicineItem.medicine_id, medicineItem.effective_date,
								medicineItem.storehouse_id);
						statement.executeUpdate(sqlExecutionString);
					}

				}
			}
			connection.commit();
		} catch (SQLException e1) {
			e1.printStackTrace();
			// 失败则事务回滚
			connection.rollback();
			return false;
		}
		return true;
	}
}
