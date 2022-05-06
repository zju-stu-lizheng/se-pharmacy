package com.example;

import java.sql.SQLException;

/**
 * 测试MyJDBC
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		MyJDBC conJdbc = new MyJDBC("001");
		MyJDBC.connectDatabase();

//		StringBuffer brand = new StringBuffer();
//		String tmpString = "\"ni\\hao\"";
//		System.out.println(tmpString);
//
//		char[] c = tmpString.toCharArray();
//		int j;
//
//		for (j = 0; j < c.length; j++) {
//			if (c[j] == '"') {
//				brand.append("\\\"");
//			} else if (c[j] == '\\') {
//				brand.append("\\\\");
//			} else {
//				brand.append(c[j]);
//			}
//		}
//		System.out.println(brand);

		/* test for insert administator */
//		String ano = "001";
//		String aname = "lizheng";
//		String password = "yp";
//		String phonenumber = "123456";
//		conJdbc.insertAdministator(ano, aname, password, phonenumber);

		/* test for insert Medicine */
//		System.out.println("test for insert Medicine");
//		String id = "001";
//		String effString = "2022-05-28";
//		String storeString = "1";
//		String brandString = "国药";
//		String name = "阿司匹林";
//		String function = "解热镇痛";
//		float price = 25.0f;
//		int stock = 20;
//		conJdbc.insertMedicine(id, effString, storeString, brandString, name, function, price, stock);
//
//		id = "002";
//		effString = "2022-05-30";
//		storeString = "1";
//		brandString = "国药";
//		name = "头孢";
//		function = "头孢就酒，越喝越勇";
//		price = 24.0f;
//		stock = 10;
//		conJdbc.insertMedicine(id, effString, storeString, brandString, name, function, price, stock);

		/* test for query all */
		System.out.print(conJdbc.queryMedicine());

		/* test for add Medicine */
//		System.out.println("test for add Medicine");
//		id = "001";
//		effString = "2022-05-25";
//		storeString = "1";
//		stock = 20;
//		conJdbc.addMedicine(id, effString, storeString, stock);

		/* test for query all */
//		System.out.print(conJdbc.queryMedicine());

		// /* test for delivery Medicine */
		// System.out.println("test for delivery Medicine");
		// try {
		// conJdbc.deliveryMedicine(id, storeString, effString, 2);
		// } catch (SQLException e1) {
		// e1.printStackTrace();
		// }
		// /* test for query all */
		// System.out.print(conJdbc.queryMedicine());

		// /* test for delete Medicine */
		// System.out.println("test for delete Medicine");
		// id = "001";
		// effString = "2022-05-25";
		// storeString = "1";
		// stock = 20;
		// try {
		// conJdbc.deleteMedicine(id, storeString, effString);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		/* test for addShoppingCart */
//		System.out.println("test for addShoppingCart");
//		try {
//			if(conJdbc.addShoppingCart("001", "001", "1", 2)) {
//				System.out.println("add1 success");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println("test for addShoppingCart 2");
//		try {
//			conJdbc.addShoppingCart("001", "002", "1", 3);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("Total Price for 1 is " + conJdbc.getPrice("001", "1"));
//		System.out.println("Total Price for 2 is " + conJdbc.getPrice("001", "2"));

		/* test for deleteShoppingCart */
//		System.out.println("test for deleteShoppingCart");
//		try {
//			conJdbc.deleteShoppingCart("001", "001", 4);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		MyJDBC.disconnectDatabase();
	}
}
