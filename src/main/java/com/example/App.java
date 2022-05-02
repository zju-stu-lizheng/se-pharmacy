package com.example;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		MyJDBC conJdbc = new MyJDBC("001");
		MyJDBC.connectDatabase();

		/* test for insert administator */
		String ano = "001";
		String aname = "lizheng";
		String password = "yp";
		String phonenumber = "123456";
		conJdbc.insertAdministator(ano, aname, password, phonenumber);

		/* test for insert Medicine */
		System.out.println("test for insert Medicine");
		String id = "001";
		String effString = "2022-05-25";
		String storeString = "1";
		String brandString = "国药";
		String name = "阿司匹林";
		String function = "解热镇痛";
		float price = 25.0f;
		int stock = 20;
		conJdbc.insertMedicine(id, effString, storeString, brandString, name, function, price, stock);

		/* test for query all */
		System.out.print(conJdbc.queryMedicine());

		/* test for add Medicine */
		System.out.println("test for add Medicine");
		id = "001";
		effString = "2022-05-25";
		storeString = "1";
		stock = 20;
		conJdbc.addMedicine(id, effString, storeString, stock);

		/* test for query all */
		System.out.print(conJdbc.queryMedicine());

		/* test for delivery Medicine */
		System.out.println("test for delivery Medicine");
		try {
			conJdbc.deliveryMedicine(id, storeString, effString, 2);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/* test for query all */
		System.out.print(conJdbc.queryMedicine());

		/* test for delete Medicine */
		System.out.println("test for delete Medicine");
		id = "001";
		effString = "2022-05-25";
		storeString = "1";
		stock = 20;
		try {
			conJdbc.deleteMedicine(id, storeString, effString);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/* test for query all */
		System.out.print(conJdbc.queryMedicine());

		MyJDBC.disconnectDatabase();
	}
}
