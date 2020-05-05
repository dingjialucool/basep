package com.chinobot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Test;

public class LocalDateTest {

	@Test
	public void test() {
//		LocalDate d = LocalDate.now();
//		LocalDateTime atStartOfDay2 = d.atStartOfDay();
//		System.out.println(atStartOfDay2);
//		LocalDate plusDays = d.plusDays(2);
//		LocalDateTime atStartOfDay = d.atTime(8,0);
//		LocalDateTime plusHours = atStartOfDay.plusHours(2);
//		System.out.println(atStartOfDay);
//		System.out.println(plusHours);
//		boolean after = atStartOfDay2.toLocalDate().equals(LocalDate.now());
//		System.out.println(after);
//		System.out.println("-------------------------------");
//		LocalDate parse = d.parse("2019-04-09");
//		System.out.println("parse"+parse);
//		System.out.println("now:"+d.plusDays(3));
//		System.out.println(d.equals(LocalDate.now()));
//		System.out.println(d.isAfter(LocalDate.now())+"-----");
//		System.out.println(d.isBefore(LocalDate.now()));
//		System.out.println("nowdate:"+Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String ss = now.format(dateTimeFormatter);
		System.out.println(ss);
		
	}
}
