package com.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 管理员，调度程序与JDBC联络的类:
 * ps:not used now
 * 
 * @author Lenovo
 *
 */
public class Manager {
	/**
	 * 设置窗口数
	 * 
	 * @param num
	 */
	public static void setWindow(int num, String storehouse_id) {
		MyWindows.setWindowsStatus(num, storehouse_id);
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
			statement = MyJDBC.connection.createStatement();
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
			statement = MyJDBC.connection.createStatement();
			statement.executeUpdate("INSERT INTO administrator" + " VALUES('" + ano + "','" + aname + "','" + password
					+ "','" + phonenumber + "');");
			MyJDBC.connection.commit();
		} catch (SQLException e) {
			try {
				MyJDBC.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
