package com.example;

import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * To do list:
 * 1. 医生端数据包解析
 * 1.1 返回所有药品信息
 * 1.2 给用户开药
 * 2. 预约端数据包解析
 * 2.1 获取历史消费记录,日期+药品
 * 
 * @author Lenovo
 *
 */

public class MyJSON {

	/**
	 * 收到insert数据包，需要将药品入库
	 * 
	 * @param ano            : 管理員 id
	 * @param id             : 药品 id
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param brand          : 药品 厂商
	 * @param name           : 药品 名字
	 * @param function       : 药品 作用
	 * @param dosage         : 用法用量
	 * @param banned         : 禁用人群
	 * @param price          : 药品 单价
	 * @param stock          : 药品 库存(入库数量)
	 */
	public static void insertOperate(String ano, String id, String effective_date, String storehouse_id, String brand,
			String name, String function, String dosage, String banned, float price, int stock, int isPrescription,
			String unit) {
		MyJDBC conJdbc = new MyJDBC(ano);
		Boolean response = MyJDBC.insertMedicine(id, effective_date, storehouse_id, stock);
		/* 发送一个json包 */
		JSONObject obj = new JSONObject();

		obj.put("op", "ret_update");
		obj.put("response", response);

		System.out.println(obj);
	}

	/**
	 * 收到delete数据包，需要将药品出库
	 * 
	 * @param ano            : 管理員 id
	 * @param id             : 药品 id
	 * @param storehouse_id  : 库房 id <char(2)>
	 * @param effective_date : 药品 有效日期 <YYYY-MM-DD>
	 * @param stock          : 药品 库存(入库数量)
	 */
	public static void deleteOperate(String ano, String id, String effective_date, String storehouse_id) {
		MyJDBC conJdbc = new MyJDBC(ano);
		Boolean response = false;
		try {
			response = conJdbc.deleteMedicine(id, storehouse_id, effective_date);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/* 发送一个json包 */
		JSONObject obj = new JSONObject();

		obj.put("op", "ret_update");
		obj.put("response", response);

		System.out.println(obj);
	}

	/**
	 * 收到query数据包，查询所有药品
	 * 
	 * @param ano : 管理员 id
	 * @return : csv格式的药品信息
	 */
	public static String queryMedicineOperate(String ano) {
		MyJDBC conJdbc = new MyJDBC(ano);
		String medicineList = conJdbc.queryMedicine();
		/* 发送一个json包 */
		JSONObject obj = new JSONObject();

		obj.put("op", "ret_query");
		obj.put("medicine_list", medicineList);

		System.out.println(obj);
		return medicineList;
	}

	/**
	 * 收到query数据包，查询该用户购物车内所有药品
	 * 
	 * @param user_id     : 用户 id
	 * @param branch_name : 药房 id
	 * @return
	 */
	public static String queryShoppingCartOperate(String user_id, String branch_name) {
		MyJDBC conJdbc = new MyJDBC();
		String medicineList = conJdbc.getShoppingCart(user_id, branch_name);
		/* 发送一个json包 */
		JSONObject obj = new JSONObject();

		obj.put("op", "ret_query");
		obj.put("medicine_list", medicineList);

		System.out.println(obj);
		return medicineList;
	}

	/**
	 * 收到insert数据包，往购物车中添加一条记录
	 * 
	 * @param user_id       : 用户 id
	 * @param medicine_id   : 药品 id
	 * @param storehouse_id : 药房 id
	 * @param num           : 购买数量
	 */
	public static void insertShoppingCartOperate(String user_id, String medicine_id, String storehouse_id, int num) {
		MyJDBC conJdbc = new MyJDBC();
		Boolean response = false;
		try {
			response = conJdbc.addShoppingCart(user_id, medicine_id, storehouse_id, num);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/* 发送一个json包 */
		JSONObject obj = new JSONObject();

		obj.put("op", "ret_update");
		obj.put("response", response);

		System.out.println(obj);
	}

	/**
	 * 根据json数据包内容，确定执行何种操作
	 * 
	 * @param msg
	 * @return
	 */
	public static String jsonParser(String msg) {
		JSONParser parser = new JSONParser();
		String opString;
		String retString = "";

		try {
			JSONObject obj = (JSONObject) parser.parse(msg);

			System.out.print("op is ");
			opString = obj.get("op").toString();
			System.out.println(opString);
			String ano, id, effective_date, storehouse_id, brand, name, function, dosage, banned, user_id, medicine_id,
					unit;
			float price;
			int stock, num, isPrescription;

			switch (opString) {
				case "insert_medicine":
					// System.out.println("insert_medicine");
					ano = obj.get("ano").toString();
					id = obj.get("id").toString();
					effective_date = obj.get("effective_date").toString();
					storehouse_id = obj.get("storehouse_id").toString();
					brand = obj.get("brand").toString();
					name = obj.get("name").toString();
					function = obj.get("function").toString();
					dosage = obj.get("dosage").toString();
					banned = obj.get("banned").toString();
					price = Float.valueOf(obj.get("price").toString());
					stock = Integer.valueOf(obj.get("stock").toString());
					isPrescription = Integer.valueOf(obj.get("isPrescription").toString());
					unit = obj.get("unit").toString();

					/* 解析得到一个插入操作 */
					insertOperate(ano, id, effective_date, storehouse_id, brand, name, function, dosage, banned, price,
							stock, isPrescription, unit);
					break;
				case "delete_medicine":
					// System.out.println("delete_medicine");
					ano = obj.get("ano").toString();
					id = obj.get("id").toString();
					effective_date = obj.get("effective_date").toString();
					storehouse_id = obj.get("storehouse_id").toString();

					/* 解析得到一个出库操作 */
					deleteOperate(ano, id, effective_date, storehouse_id);
					break;
				case "query_medicine":
					ano = obj.get("ano").toString();

					/* 解析得到一个查询药品操作 */
					retString = queryMedicineOperate(ano);
					break;
				case "query_shoppingCart":
					user_id = obj.get("user_id").toString();
					storehouse_id = obj.get("storehouse_id").toString();
					/* 解析得到一个查询购物车操作 */
					queryShoppingCartOperate(user_id, storehouse_id);
					break;
				case "insert_shoppingCart":
					user_id = obj.get("user_id").toString();
					medicine_id = obj.get("medicine_id").toString();
					storehouse_id = obj.get("storehouse_id").toString();
					num = Integer.valueOf(obj.get("num").toString());

					/* 解析得到加入购物车操作 */
					insertShoppingCartOperate(user_id, medicine_id, storehouse_id, num);
					break;
				default:
					break;
			}

		} catch (ParseException pe) {
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}
		return retString.toString();
	}
}
