package com.devProject.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelUtil {

	public HashMap<String, List> loadExcel(String filePath) throws Exception {

		File file = new File(filePath);
		Workbook wb = null;

		wb = Workbook.getWorkbook(file);

		Sheet[] sheets = wb.getSheets(); // 전체 sheet 가져오기

		HashMap<String, List> table = new HashMap<String, List>(); // 테이블 담을 곳 생성
		List<HashMap<String, Object>> list = null;
		HashMap<String, Object> data = null;
		List<String> columList = null;

		// 시트
		for (Sheet sheet : sheets) {

			list = new ArrayList<HashMap<String, Object>>(); // 데이터 행 담을 곳 생성
			columList = new ArrayList<String>(); // 테이블 컬럼 값 담을 곳 생성

			// 시트 행
			for (int i = 0; i < sheet.getRows(); i++) {

				data = new HashMap<String, Object>(); // 데이터 값 담을 곳 생성

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

			table.put(sheet.getName(), list); // 테이블 데이터 저장
		}

		return table;
	}
}
