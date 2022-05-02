package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyJDBC {

	/**
	 * 登录数据库所需的信息:包括驱动器，数据库名称以及登录名、密码
	 */
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/se?useSSL=false&serverTimezone=UTC";
	static final String USERNAME = "root";
	static final String PASSWD = "lizheng";

	static Connection connection = null;
	String anoString;

	/**
	 * 构造函数，每一个管理员新建一个对象
	 * 
	 * @param ano : 管理员 id
	 */
	public MyJDBC(String ano) {
		anoString = ano;
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
	public boolean ensureLogin(String ano, String passwd) {
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
	public boolean insertAdministator(String ano, String aname, String password, String phonenumber) {
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
	 * @param price          : 药品 单价
	 * @param stock          : 药品 库存(入库数量)
	 * @return : true(插入成功)/false(插入失败)
	 */
	public boolean insertMedicine(String id, String effective_date, String storehouse_id, String brand, String name,
			String function, float price, int stock) {
		String sqlExecutionString = "";
		try {
			Statement statement = connection.createStatement();
			// insert into database
			sqlExecutionString = String.format("INSERT INTO medicine VALUES('%s','%s','%s','%s','%s','%s',%f,%d);", id,
					effective_date, storehouse_id, brand, name, function, price, stock);
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
	public boolean deleteMedicine(String id, String storehouse_id, String effective_date)
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
			// 记录该药品下架前的库存
			int stock = Integer.valueOf(resultSet.getString(1));
			// 删除该药品的记录
			sqlExecutionString = String.format(
					"DELETE FROM medicine WHERE id='%s' AND storehouse_id='%s' AND effective_date='%s';", id,
					storehouse_id, effective_date);
			statement.executeUpdate(sqlExecutionString);
			// 添加到日志
			sqlLogString = String.format("INSERT INTO log VALUES('%s','%s','%s','%s','%s',%d);", anoString, "delete",
					id,
					effective_date, storehouse_id, stock);
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
	public boolean deliveryMedicine(String id, String storehouse_id, String effective_date, int num)
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
					id,
					effective_date, storehouse_id, num);
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
	 * @return
	 */
	public boolean addMedicine(String id, String effective_date, String storehouse_id, int stock) {
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
	 * 查询所有药品记录
	 * 
	 * @return : csv格式的药品记录
	 */
	public String queryMedicine() {
		String sqlExecutionString = "select * from medicine;";
		StringBuffer queryResultBuffer = new StringBuffer();
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(sqlExecutionString);
			while (rs.next()) {
				/* 根据 属性获取该条记录相应的值 */
				String id = rs.getString("id");
				String effective_date = rs.getString("effective_date");
				String storehouse_id = rs.getString("storehouse_id");
				String brandString = rs.getString("brand");
				String name = rs.getString("name");
				String function = rs.getString("function");
				float price = rs.getFloat("price");
				int stock = rs.getInt("stock");

				String tmpString = id + "," + effective_date + "," + storehouse_id + "," + brandString + "," + name
						+ "," + function + "," + price + "," + stock + "\n";
				/* 将每条记录添加入 buffer */
				queryResultBuffer.append(tmpString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryResultBuffer.toString();
	}

}
