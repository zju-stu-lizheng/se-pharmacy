package com.example;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * test for 测试入库，加入购物车等操作
	 */
//	@Test
	public void testForMyJDBC() {
		System.out.println("Welcome to App!");

		MyJDBC.connectDatabase();

		MyJDBC.doDeleteTable("shoppingcart");
		MyJDBC.doDeleteTable("medicine");
		MyJDBC.doDeleteTable("window");
		MyJDBC.doDeleteTable("Queue");
//		MyJDBC.doDeleteTable("bill");

		/* test for insert Medicine */
		System.out.println("test for insert Medicine");
		String id = "001";
		String effString = "2022-05-28";
		String storeString = "玉古路店";
		String brandString = "国药";
		String name = "阿司匹林";
		String function = "解热镇痛";
		String dosage = "一日三次";
		String banned = "三高人群";
		float price = 25.0f;
		int stock = 20;
		MyJDBC.insertMedicine(id, effString, storeString, brandString, name, function, dosage, banned, price, stock);

		id = "002";
		effString = "2022-05-30";
		brandString = "国药";
		name = "头孢";
		function = "头孢就酒，越喝越勇";
		price = 24.0f;
		stock = 10;
		MyJDBC.insertMedicine(id, effString, storeString, brandString, name, function, dosage, banned, price, stock);

		id = "001";
		effString = "2022-06-29";
		brandString = "国药";
		name = "阿司匹林";
		function = "解热镇痛";
		price = 25.0f;
		stock = 10;
		MyJDBC.insertMedicine(id, effString, storeString, brandString, name, function, dosage, banned, price, stock);

		/* test for query all */
		System.out.println(MyJDBC.queryMedicine());

		/* test for add Medicine */
		System.out.println("test for add Medicine");
		id = "001";
		effString = "2022-05-28";
		stock = 20;
		MyJDBC.addMedicine(id, effString, storeString, stock);

		/* test for query all */
		System.out.println(MyJDBC.queryMedicine());

		// /* test for delivery Medicine */
		// System.out.println("test for delivery Medicine");
		// try {
		// MyJDBC.deliveryMedicine(id, storeString, effString, 2);
		// } catch (SQLException e1) {
		// e1.printStackTrace();
		// }
		// /* test for query all */
		// System.out.print(MyJDBC.queryMedicine());

		// /* test for delete Medicine */
		// System.out.println("test for delete Medicine");
		// id = "001";
		// effString = "2022-05-25";
		// storeString = "1";
		// stock = 20;
		// try {
		// MyJDBC.deleteMedicine(id, storeString, effString);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		/* test for setShoppingCart */
		System.out.println("test for setShoppingCart");
		try {
			assertTrue(MyJDBC.setShoppingCart("001", "001", storeString, 2));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(MyJDBC.queryShoppingCart("001", storeString));

		/* test for addShoppingCart */
		System.out.println("test for addshoppingCart");
		try {
			assertTrue(MyJDBC.addShoppingCart("002", "002", storeString, 3));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(MyJDBC.queryShoppingCart("001", storeString));

		System.out.println("test for setShoppingCart 2");
		try {
			assertTrue(MyJDBC.setShoppingCart("001", "002", storeString, 4));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(MyJDBC.queryShoppingCart("001", storeString));

		/* test for deleteShoppingCart */
//		System.out.println("test for deleteShoppingCart");
//		try {
//			MyJDBC.deleteShoppingCart("001", "002", storeString, 4);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

//		System.out.println(MyJDBC.queryShoppingCart("001", storeString));

//		System.out.println("Total Price for 1 is " + MyJDBC.getPrice("001", storeString));
//		System.out.println("Total Price for 2 is " + MyJDBC.getPrice("001", storeString));

//		try {
//			System.out.println("buy medicine!");
//			MyJDBC.buyMedicine("001", storeString);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		System.out.println("query all branch!");
		System.out.println(MyJDBC.getAllBranch());

		/* test for query all */
		System.out.println(MyJDBC.queryMedicine());
		
		
		/* test for MyWindow */
		System.out.println("test for MyWindow");
		assertTrue(MyJDBC.addWindow(35, storeString, 2));
		assertTrue(MyJDBC.addWindow(36, storeString, 2));
		assertTrue(MyJDBC.addWindow(37, storeString, 3));
		
		System.out.println(MyJDBC.searchWindowPeople(storeString, 2));
	}

	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		String storeString = "玉古路店";
		Manager.setWindow(5, storeString);
		MyJDBC.connectDatabase();
		
		/* test for setShoppingCart */
		System.out.println("test for setShoppingCart");
		try {
			assertTrue(MyJDBC.setShoppingCart("001", "001", storeString, 2));
			MyJDBC.buyMedicine("001", storeString);
		} catch (SQLException e) {
			e.printStackTrace();
		}

//		MyJDBC.addQueue(32, storeString);
//		MyJDBC.deleteQueue(32);
//		MyJDBC.deleteWindow(35);
	}
}
