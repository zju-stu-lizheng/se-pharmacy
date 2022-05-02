package com.example;

import javax.print.attribute.standard.JobKOctets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MyJSON {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		String s = "{\r\n" + "    \"op\" : \"insert\",	\r\n" + "    \"id\" : \"001\",	\r\n"
				+ "    \"effective_date\" : \"2022-05-23\",	\r\n" + "    \"storehouse_id\" : \"01\",			\r\n"
				+ "    \"brand\" : \"国药\",					\r\n" + "    \"name\" : \"阿司匹林\",			\r\n"
				+ "    \"function\" : \"解热镇痛\",		\r\n" + "    \"price\" : 25.0,				\r\n"
				+ "    \"stock\" : 25				\r\n" + "}";

		try {
			JSONObject obj = (JSONObject) parser.parse(s);

			System.out.print("op is");
			String opString = obj.get("op").toString();
			System.out.println(opString);
			switch (opString) {
			case "insert":
				System.out.println("insert");
				System.out.println("id=" + obj.get("id"));
				System.out.println("effective_date=" + obj.get("effective_date"));
				System.out.println("storehouse_id=" + obj.get("storehouse_id"));
				System.out.println("brand=" + obj.get("brand"));
				System.out.println("name=" + obj.get("name"));
				System.out.println("function=" + obj.get("function"));
				System.out.println("price=" + obj.get("price"));
				System.out.println("stock=" + obj.get("stock"));
				break;
			case "":

			default:
				break;
			}

		} catch (ParseException pe) {
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}
	}
}
