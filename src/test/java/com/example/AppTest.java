package com.example;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Vector;

import org.junit.Test;

/**
 * Unit test for simple AppLICATION.
 */
public class AppTest {
	/**
	 * test for 测试入库，加入购物车等操作
	 */
	// @Test
	public void testAddMedicine() {
		MyJDBC.connectDatabase();
		/* test for insert Medicine */
		System.out.println("test for insert Medicine");
		String id = "1";
		String effString = "2023-05-28";
		String storeString = "玉古路店";
		int stock = 20;
		MyJDBC.insertMedicine(id, effString, storeString, stock);

		id = "2";
		effString = "2023-05-30";
		stock = 10;
		MyJDBC.insertMedicine(id, effString, storeString, stock);

		id = "1";
		effString = "2023-06-29";
		stock = 10;
		MyJDBC.insertMedicine(id, effString, storeString, stock);

		/* test for add Medicine */
		System.out.println("test for add Medicine");
		id = "1";
		effString = "2022-05-28";
		stock = 20;
		MyJDBC.addMedicine(id, effString, storeString, stock);

		/* test for query all */
		// System.out.println(MyJDBC.queryMedicine());

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

	}

	// @Test
	public void addAllMedicine() {
		MyJDBC.connectDatabase();
		Vector<String> allID = MyJDBC.getAllMedicineID();
		String effString = "2023-06-28";
		String storeString = "玉古路店";
		for (int i = 0; i < allID.size(); i++) {
			int stock = 100 + (int) Math.round(Math.random() * 20);
			String each = allID.get(i);
			MyJDBC.insertMedicine(each, effString, storeString, stock);
		}
	}

	@Test
	public void SearchMedicine() {
		MyJDBC.connectDatabase();

		String storeString = "紫金港店";

		System.out.println(MyJDBC.searchMedicine("", 705));
		// System.out.println(MyJDBC.searchMedicine("", storeString, 1));

		// System.out.println(MyJDBC.searchMedicine("头孢拉定颗粒", storeString, 1));
	}

	@Test
	public void testGetShoppingCart() {
		MyJDBC.connectDatabase();

		String branchName = "玉古路店";
		System.out.println(MyJDBC.getShoppingCart("9", branchName));
	}

	@Test
	public void testGetAllBranch() {
		MyJDBC.connectDatabase();
		System.out.println(MyJDBC.getAllBranch());
	}

	@Test
	public void testQueryMedicine() {
		MyJDBC.connectDatabase();

		String branchName = "玉古路店";
		// System.out.println("测试用户端:\n" + MyJDBC.queryMedicine("99999", branchName));
		System.out.println("测试管理员端:\n" + MyJDBC.queryMedicine(705));
	}

	@Test
	public void testSearchMedicineInfo() {
		MyJDBC.connectDatabase();
		System.out.println(MyJDBC.searchMedicineInfo("1"));
	}

	@Test
	public void testAdmQueryMedicine() {
		MyJDBC.connectDatabase();
		System.out.println(MyJDBC.queryMedicine(1));
	}

	// @Test
	public void testForShoppingCart() {
		System.out.println("Welcome to App!");
		MyJDBC.connectDatabase();

		String storeString = "玉古路店";
		Manager.setWindow(5, storeString);

		MyJDBC.doDeleteTable("shoppingCart");
		MyJDBC.doDeleteTable("SE_window");
		MyJDBC.doDeleteTable("SE_Queue");
		MyJDBC.doDeleteTable("bill");

		/* test for setShoppingCart */
		System.out.println("test for setShoppingCart");
		try {
			assertTrue(MyJDBC.setShoppingCart("001", "1", storeString, 2));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(MyJDBC.getShoppingCart("001", storeString));

		// System.out.println("test for setShoppingCart to 0");
		// try {
		// assertTrue(MyJDBC.setShoppingCart("001", "001", storeString, 0));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// System.out.println(MyJDBC.getShoppingCart("001", storeString));

		/* test for addShoppingCart */
		// System.out.println("test for addshoppingCart");
		// try {
		// assertTrue(MyJDBC.addShoppingCart("002", "002", storeString, 3));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		// System.out.println(MyJDBC.getShoppingCart("002", storeString));

		System.out.println("test for setShoppingCart 2");
		try {
			assertTrue(MyJDBC.setShoppingCart("001", "2", storeString, 4));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(MyJDBC.getShoppingCart("001", storeString));

		/* test for deleteShoppingCart */
		// System.out.println("test for deleteShoppingCart");
		// try {
		// MyJDBC.deleteShoppingCart("001", "002", storeString, 4);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// System.out.println(MyJDBC.queryShoppingCart("001", storeString));

		// System.out.println("Total Price for 1 is " + MyJDBC.getPrice("001",
		// storeString));
		// System.out.println("Total Price for 2 is " + MyJDBC.getPrice("001",
		// storeString));

		// try {
		// System.out.println("buy medicine!");
		// MyJDBC.buyMedicine("001", storeString);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		System.out.println("query all branch!");
		System.out.println(MyJDBC.getAllBranch());

		/* test for query all */
		System.out.println(MyJDBC.queryMedicine("布", storeString));

		try {
			MyJDBC.commitBill("001", storeString);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(MyJDBC.getShoppingCart("001", storeString));
	}

	// @Test
	public void testGETShoppingCart() {
		System.out.println("Welcome to App!");
		MyJDBC.connectDatabase();

		String storeString = "玉古路店";

		/* test for setShoppingCart */
		System.out.println("test for setShoppingCart");
		try {
			assertTrue(MyJDBC.addShoppingCart("001", "001", storeString, 2));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(MyJDBC.getShoppingCart("001", storeString));

		try {
			MyJDBC.commitBill("001", storeString);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(MyJDBC.getShoppingCart("001", storeString));
	}

	// @Test
	public void testSearchMedicine() {
		System.out.println("Welcome to App!");
		MyJDBC.connectDatabase();

		String storeString = "玉古路店";

		/* test for SearchMedicine */
		// System.out.println(MyJDBC.searchMedicine("布", storeString));
	}

	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void testCommitBill() {
		String storeString = "玉古路店";
		Manager.setWindow(5, storeString);
		MyJDBC.connectDatabase();

		/* test for setShoppingCart */
		System.out.println("test for setShoppingCart");
		try {
			assertTrue(MyJDBC.setShoppingCart("999", "1", storeString, 2));
			MyJDBC.commitBill("999", storeString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
