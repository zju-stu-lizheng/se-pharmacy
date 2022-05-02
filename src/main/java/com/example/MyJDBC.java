package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyJDBC {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/se?useSSL=false&serverTimezone=UTC";
	static String USERNAME = "root";
	static String PASSWD = "lizheng";

	static Connection connection = null;
	private static Statement statement;
	private static ResultSet resultSet;

	public static void connect() {
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

	public static boolean ensure(String ano, String passwd) {
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

	public static boolean insertAdministator(String ano, String aname, String password, String phonenumber) {
		try {
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO administrator" + " VALUES('" + ano + "','" + aname + "','" + password
					+ "','" + phonenumber + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		connect();
//		String ano = "002";
//		String aname = "wangyiping";
//		String password = "yp";
//		String phonenumber = "123456";
//		insertAdministator(ano,aname,password,phonenumber);


		System.out.println(ensure("001", "lz"));
		System.out.println(ensure("002", "yp"));
	}

}
