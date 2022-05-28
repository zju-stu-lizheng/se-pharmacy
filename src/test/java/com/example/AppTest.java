package com.example;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * test for MyJDBC 测试入库，加入购物车等操作
	 */
	@Test
	public void testForMyJDBC() {
		System.out.println("Welcome to App!");
		MyJDBC conJdbc = new MyJDBC("001");

		conJdbc.doDeleteTable("shoppingcart");
		conJdbc.doDeleteTable("medicine");
//		conJdbc.doDeleteTable("bill");

		/* test for insert Medicine */
		System.out.println("test for insert Medicine");
		String id = "001";
		String effString = "2022-05-28";
		String storeString = "1";
		String brandString = "国药";
		String name = "阿司匹林";
		String function = "解热镇痛";
		String dosage = "一日三次";
		String banned = "三高人群"; 
		float price = 25.0f;
		int stock = 20;
		conJdbc.insertMedicine(id, effString, storeString, brandString, name, function,dosage,banned, price, stock);

		id = "002";
		effString = "2022-05-30";
		storeString = "1";
		brandString = "国药";
		name = "头孢";
		function = "头孢就酒，越喝越勇";
		price = 24.0f;
		stock = 10;
		conJdbc.insertMedicine(id, effString, storeString, brandString, name, function,dosage,banned, price, stock);

		id = "001";
		effString = "2022-06-29";
		storeString = "1";
		brandString = "国药";
		name = "阿司匹林";
		function = "解热镇痛";
		price = 25.0f;
		stock = 10;
		conJdbc.insertMedicine(id, effString, storeString, brandString, name, function,dosage,banned, price, stock);

		/* test for query all */
		System.out.println(conJdbc.queryMedicine());

		/* test for add Medicine */
		System.out.println("test for add Medicine");
		id = "001";
		effString = "2022-05-28";
		storeString = "1";
		stock = 20;
		conJdbc.addMedicine(id, effString, storeString, stock);

		/* test for query all */
		System.out.println(conJdbc.queryMedicine());

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

		/* test for setShoppingCart */
		System.out.println("test for setShoppingCart");
		try {
			assertTrue(conJdbc.setShoppingCart("001", "001", "1", 2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(conJdbc.queryShoppingCart("001", "1"));
		
		/* test for addShoppingCart */
		System.out.println("test for addshoppingCart");
		try {
			assertTrue(conJdbc.addShoppingCart("001", "001", "1", 3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(conJdbc.queryShoppingCart("001", "1"));
		
		System.out.println("test for setShoppingCart 2");
		try {
			assertTrue(conJdbc.setShoppingCart("001", "002", "1", 4));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(conJdbc.queryShoppingCart("001", "1"));

		/* test for deleteShoppingCart */
		System.out.println("test for deleteShoppingCart");
		try {
			conJdbc.deleteShoppingCart("001", "002","1", 4);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(conJdbc.queryShoppingCart("001", "1"));
		
		
		System.out.println("Total Price for 1 is " + conJdbc.getPrice("001", "1"));
		System.out.println("Total Price for 2 is " + conJdbc.getPrice("001", "2"));

		try {
			conJdbc.buyMedicine("001", "1");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		conJdbc = null;
	}

	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {

	}
}
