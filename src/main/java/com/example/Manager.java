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
}
