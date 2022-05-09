package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateTest {
	@Test
	public void testDate() {
		//账单号按照创建时间的格式
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");  
		System.out.println(formatter.format(date));  
	}
}
