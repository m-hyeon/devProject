package com.devProject.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.devProject.dao.ExcelDao;
import com.devProject.util.ExcelUtil;

public class Main {

	static final String DB_URL = "jdbc:mariadb://192.168.55.244:3306/test";
	static final String DB_USER = "TEST";
	static final String DB_PW = "TEST";

	public static void main(String args[]) throws Exception {

		Connection conn = null;

		try {
			// DB 커넥션 객체 얻기
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);

			// Excel 객체 생성
			ExcelUtil excelUtil = new ExcelUtil();
			String excelPath = "D:\\SpringBoot\\20230109_유고 데이터 값.xls";
			// Excel data 가져오기
			HashMap<String, List> map = excelUtil.loadExcel(excelPath);

			// 객체 생성
			ExcelDao excelDao = new ExcelDao();

			for (Entry<String, List> elem : map.entrySet()) {
				excelDao.insertExcelData(conn, elem.getKey(), map.get(elem.getKey())); // excel 데이터 db에 insert
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("conn colse");
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
