package com.example;

/**
 * 管理员，调度程序与JDBC联络的类
 * @author Lenovo
 *
 */
public class Manager {
	/**
	 * 设置窗口数
	 * @param num
	 */
	public static void setWindow(int num,String storehouse_id) {
		MyWindows.setWindowsStatus(num, storehouse_id);
	}
}
