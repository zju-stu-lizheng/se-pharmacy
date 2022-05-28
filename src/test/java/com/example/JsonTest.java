package com.example;

import org.junit.Test;

public class JsonTest {

	@Test
	public void testForMyJSON() {
		MyJDBC.connectDatabase();

		String insertMedicine1 = "{\r\n" + "    \"op\" : \"insert_medicine\",	\r\n"
				+ "    \"ano\" : \"001\",		\r\n" + "    \"id\" : \"001\",		\r\n"
				+ "    \"storehouse_id\" : \"01\",			\r\n" + "    \"effective_date\" : \"2022-05-23\", \r\n"
				+ "    \"brand\" : \"国药\",				  \r\n" + "    \"name\" : \"阿司匹林\",			\r\n"
				+ "    \"function\" : \"解热镇痛\",		\r\n"
				+ "    \"dosage\" : \"一日三次\",		\r\n"
				+ "    \"banned\" : \"三高人群\",		\r\n"
				+ "    \"price\" : 25.0,				  \r\n"
				+ "    \"stock\" : 25				 \r\n" + "}";

		String insertMedicine2 = "{\r\n" + "    \"op\" : \"insert_medicine\",	\r\n"
				+ "    \"ano\" : \"001\",		\r\n" + "    \"id\" : \"002\",		\r\n"
				+ "    \"storehouse_id\" : \"01\",			\r\n" + "    \"effective_date\" : \"2022-05-23\", \r\n"
				+ "    \"brand\" : \"国药\",				  \r\n" + "    \"name\" : \"头孢\",			\r\n"
				+ "    \"function\" : \"头孢就酒，越喝越勇\",		\r\n" 
				+ "    \"dosage\" : \"一日三次\",		\r\n"
				+ "    \"banned\" : \"三高人群\",		\r\n"
				+ "    \"price\" : 25.0,				  \r\n"
				+ "    \"stock\" : 25				 \r\n" + "}";

		String s2 = "{\r\n" + "    \"op\" : \"delete_medicine\",	\r\n" + "    \"ano\" : \"001\",\r\n"
				+ "    \"id\" : \"001\",		\r\n" + "    \"storehouse_id\" : \"01\",			\r\n"
				+ "    \"effective_date\" : \"2022-05-23\"	\r\n" + "}";

		String s3 = "{\r\n" + "    \"op\" : \"query_medicine\",	\r\n" + "    \"ano\" : \"001\"\r\n" + "}";

		String s4 = "{\r\n" + "    \"op\" : \"insert_shoppingCart\",	\r\n" + "    \"user_id\" : \"001\",\r\n"
				+ "    \"medicine_id\" : \"001\",\r\n" + "    \"storehouse_id\" : \"01\",\r\n" + "    \"num\" : 5\r\n"
				+ "}";

		String s5 = "{\r\n" + "    \"op\" : \"insert_shoppingCart\",	\r\n" + "    \"user_id\" : \"001\",\r\n"
				+ "    \"medicine_id\" : \"002\",\r\n" + "    \"storehouse_id\" : \"01\",\r\n" + "    \"num\" : 3\r\n"
				+ "}";

		String queryShoppingString = "{\r\n" + "    \"op\" : \"query_shoppingCart\",	\r\n"
				+ "    \"user_id\" : \"001\"\r\n"
				+ "    \"storehouse_id\" : \"1\"\r\n"
		        + "}";
		
		System.out.println(MyJSON.jsonParser(insertMedicine1));
		System.out.println(MyJSON.jsonParser(insertMedicine2));
		System.out.println(MyJSON.jsonParser(queryShoppingString));
		System.out.println(MyJSON.jsonParser(s3));

		System.out.println(MyJSON.jsonParser(s4));
		System.out.println(MyJSON.jsonParser(s5));
		System.out.println(MyJSON.jsonParser(queryShoppingString));
	}
	

}
