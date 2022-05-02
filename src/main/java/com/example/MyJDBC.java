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
	private static Statement statement;
	private static ResultSet resultSet;

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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
	 * @param ano:            管理员 id
	 * @param aname：管理员       昵称
	 * @param password：管理员    密码
	 * @param phonenumber：管理员 联系方式
	 * @return : true(插入成功)/false(插入失败)
	 */
	public static boolean insertAdministator(String ano, String aname, String password, String phonenumber) {
		try {
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO administrator" + " VALUES('" + ano + "','" + aname + "','" + password
					+ "','" + phonenumber + "');");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 插入一条药品信息(如存在该药品，则在库存基础上增加stock)
	 * 
	 * @param id             : 药品 id
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param name           : 药品 名字
	 * @param function       : 药品 作用
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param price          : 药品 单价
	 * @param stock          : 药品 库存(入库数量)
	 * @return : true(插入成功)/false(插入失败)
	 */
	public static boolean insertMedicine(String id, String storehouse_id, String name, String function,
			String effective_date, float price, int stock) {
		int remainStock = 0;
		String sqlExectionString = "";
		// 获取执行sql语句的statement对象
		try {
			statement = connection.createStatement();
			// 执行sql语句,拿到结果集
			resultSet = statement.executeQuery("SELECT stock FROM medicine Where id = \"" + id + "\";");
			// 遍历结果集，得到数据
			if (resultSet.next()) {
				remainStock = Integer.valueOf(resultSet.getString(1));
				// update database
				stock += remainStock;
				sqlExectionString = String.format("UPDATE medicine set stock = %d where id = %s;", stock, id);
				
			} else {
				// insert into database
				sqlExectionString = String.format("INSERT INTO medicine VALUES('%s','%s','%s','%s','%s',%f,%d);", id,
						storehouse_id, name, function, effective_date, price, stock);
			}
			statement = connection.createStatement();
			statement.executeUpdate(sqlExectionString);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}

		return true;
	}

	
	public static void main(String[] args) {
		connectDatabase();
//		String ano = "002";
//		String aname = "wangyiping";
//		String password = "yp";
//		String phonenumber = "123456";
//		insertAdministator(ano,aname,password,phonenumber);

//		String id = "001";
//		String storeString = "1";
//		String name = "阿司匹林";
//		String function = "解热镇痛";
//		String effString = "2022-05-22";
//		float price = 25.0f;
//		int stock = 20;
//		insertMedicine(id, storeString, name, function, effString, price, stock);

		System.out.println(ensureLogin("001", "lz"));
		System.out.println(ensureLogin("002", "yp"));
	}

}
