package com.devproject;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.devproject.dao.ExcelDao;
import com.devproject.util.ExcelUtil;

@Component
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
public class DevProjectApplicationRunner implements ApplicationRunner {

	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUser;
	@Value("${spring.datasource.password}")
	private String dbPw;

	@Value("${excel.path}")
	private String excelPath;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void run(ApplicationArguments args) {
		logger.info("DevProjectApplicationRunner run");

		// Try-with-resources: try 코드 블록이 끝나면 자동으로 connection close
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPw)) {

			// Excel 객체 생성
			ExcelUtil excelUtil = new ExcelUtil();

			// Excel data 가져오기
			Map<String, List> map = excelUtil.loadExcel(new String(excelPath.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

			// 객체 생성
			ExcelDao excelDao = new ExcelDao();

			if (map != null && !map.isEmpty()) {
				for (Entry<String, List> elem : map.entrySet()) {
					int dataCnt = excelDao.insertExcelData(conn, elem.getKey(), elem.getValue()); // excel 데이터 db에 insert

					// 실제 엑셀 데이터 수와 insert 된 데이터 수 비교
					logger.info("=== {}/{} (excel cnt/insert cnt)", elem.getValue().size(), dataCnt);
					if (elem.getValue().size() == dataCnt) {
						logger.info("=== {} 엑셀 데이터와 insert된 데이터 수 일치", elem.getKey());
						conn.commit(); // 데이터 베이스에 영구적으로 반영
					} else {
						logger.info("=== {} 엑셀 데이터와 insert된 데이터 수 불일치. data rollback ", elem.getKey());
						conn.rollback(); // 트랜잭션 실행 이전 상태로 되돌리기
						logger.info("=== rollback success");
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
