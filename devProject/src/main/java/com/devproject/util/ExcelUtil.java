package com.devproject.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelUtil {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Map<String, List> loadExcel(String filePath) {

		File file = new File(filePath);
		Workbook wb = null;
		HashMap<String, List> table = null;

		try {

			wb = Workbook.getWorkbook(file);

			Sheet[] sheets = wb.getSheets(); // 전체 sheet 가져오기

			table = new HashMap<>(); // 테이블 담을 곳 생성
			List<HashMap<String, Object>> list = null;
			HashMap<String, Object> data = null;
			List<String> columList = null;
			int sheetRows = 0;

			// 시트
			for (Sheet sheet : sheets) {

				list = new ArrayList<>(); // 데이터 행 담을 곳 생성
				columList = new ArrayList<>(); // 테이블 컬럼 값 담을 곳 생성

				// 시트 행
				sheetRows = sheet.getRows(); // for문에 바로 입력 시 row 데이터를 여러번 읽어오므로 한번만 읽어오도록 선언
				for (int i = 0; i < sheetRows; i++) {

					data = new HashMap<>(); // 데이터 값 담을 곳 생성

					// 시트 열
					for (int j = 0; j < sheet.getColumns(); j++) {
						// 한 셀
						Cell cell = sheet.getCell(j, i);

						if (i == 0) { // 테이블 컬럼 값 저장
							columList.add(cell.getContents().trim());
						} else { // 테이블 데이터 값 저장
							data.put(columList.get(j), cell.getContents().trim());
						}
					}

					if (data.size() > 0) {
						list.add(data); // 데이터 행 저장
					}
				}

				// 엑셀 데이터 row 수와 list에 담긴 데이터 수가 동일한지 확인
				if (sheetRows-1 == list.size()) { 
					table.put(sheet.getName(), list); // 테이블 데이터 저장
				} else {
					// 데이터 수가 동일하지 않을 경우 데이터 insert 미진행하도록 return null
					logger.info("=== {} 엑셀 데이터와 list 데이터 수 불일치. insert 미진행 ", sheet.getName());
					return null;
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}

		return table;
	}
}
