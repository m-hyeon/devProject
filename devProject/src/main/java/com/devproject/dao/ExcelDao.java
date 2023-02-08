package com.devproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private PreparedStatement pstmt = null;

	public int insertExcelData(Connection conn, String tableNm, List<HashMap<String, Object>> list) {

		String sql = null;
		pstmt = null;
		int cnt = 0;

		// 3개이상 동일한 값을 사용 할 경우 상수로 적용
		String subId = "SUB_ID";

		try {
			for (cnt = 0; cnt < list.size(); cnt++) {
				// 테이블 마다 쿼리문 setting
				if ("DMB_ACC_MAIN".equals(tableNm)) {
					sql = "INSERT INTO DMB_ACC_MAIN "
							+ "(ID, RPT_ID, SRC_FLAG, SVC_NAME, MAKE_FLAG, LON, LAT, DIR, STD_ID, STD_LENGTH"
							+ ", CTL_TYPE, SPEED, ACC_TITLE, ACC_DESC, ST_TIME, ED_TIME, LON_ORG, LAT_ORG"
							+ ", DIR_ORG, CTL_TIME, SVC_FLAG, WEEK, LGRP_CODE, MGRP_CODE, SGRP_CODE, GCODE, LANE_GCODE, UPTIME) VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,?, ?, ?, ? )";

					// 쿼리 수행 객체 생성
					pstmt = conn.prepareStatement(sql);

					// 변수 타입에 따라 setting
					pstmt.setString(1, list.get(cnt).get("ID").toString());
					pstmt.setString(2, list.get(cnt).get("RPT_ID").toString());
					chkData(3, String.valueOf(list.get(cnt).get("SRC_FLAG")), Types.INTEGER);
					pstmt.setString(4, list.get(cnt).get("SVC_NAME").toString());
					chkData(5, String.valueOf(list.get(cnt).get("MAKE_FLAG")), Types.INTEGER);
					chkData(6, String.valueOf(list.get(cnt).get("LON")), Types.FLOAT);
					chkData(7, String.valueOf(list.get(cnt).get("LAT")), Types.FLOAT);
					chkData(8, String.valueOf(list.get(cnt).get("DIR")), Types.INTEGER);
					chkData(9, String.valueOf(list.get(cnt).get("STD_ID")), Types.INTEGER);
					chkData(10, String.valueOf(list.get(cnt).get("STD_LENGTH")), Types.INTEGER);
					chkData(11, String.valueOf(list.get(cnt).get("CTL_TYPE")), Types.INTEGER);
					chkData(12, String.valueOf(list.get(cnt).get("SPEED")), Types.INTEGER);
					pstmt.setString(13, list.get(cnt).get("ACC_TITLE").toString());
					pstmt.setString(14, list.get(cnt).get("ACC_DESC").toString());
					pstmt.setString(15, list.get(cnt).get("ST_TIME").toString());
					pstmt.setString(16, list.get(cnt).get("ED_TIME").toString());
					chkData(17, String.valueOf(list.get(cnt).get("LON_ORG")), Types.FLOAT);
					chkData(18, String.valueOf(list.get(cnt).get("LAT_ORG")), Types.FLOAT);
					chkData(19, String.valueOf(list.get(cnt).get("DIR_ORG")), Types.INTEGER);
					chkData(20, String.valueOf(list.get(cnt).get("CTL_TIME")), Types.INTEGER);
					chkData(21, String.valueOf(list.get(cnt).get("SVC_FLAG")), Types.INTEGER);
					pstmt.setString(22, list.get(cnt).get("WEEK").toString());
					chkData(23, String.valueOf(list.get(cnt).get("LGRP_CODE")), Types.INTEGER);
					chkData(24, String.valueOf(list.get(cnt).get("MGRP_CODE")), Types.INTEGER);
					chkData(25, String.valueOf(list.get(cnt).get("SGRP_CODE")), Types.INTEGER);
					chkData(26, String.valueOf(list.get(cnt).get("GCODE")), Types.INTEGER);
					chkData(27, String.valueOf(list.get(cnt).get("LANE_GCODE")), Types.INTEGER);
					pstmt.setString(28, list.get(cnt).get("UPTIME").toString());
				} else if ("DMB_ACC_SUB".equals(tableNm)) {
					sql = "INSERT INTO DMB_ACC_SUB " + "(ID, SUB_ID, ADMIN_CODE, ADMIN_NAME, ROAD_NAME, ROAD_TYPE"
							+ ", TOTAL_LANE, ACC_LANE, ST_GUGAN, ED_GUGAN, ST_TIME, ED_TIME) VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

					// 쿼리 수행 객체 생성
					pstmt = conn.prepareStatement(sql);

					// 변수 타입에 따라 setting
					pstmt.setString(1, list.get(cnt).get("ID").toString());
					chkData(2, String.valueOf(list.get(cnt).get(subId)), Types.INTEGER);
					chkData(3, String.valueOf(list.get(cnt).get("ADMIN_CODE")), Types.INTEGER);
					pstmt.setString(4, list.get(cnt).get("ADMIN_NAME").toString());
					pstmt.setString(5, list.get(cnt).get("ROAD_NAME").toString());
					chkData(6, String.valueOf(list.get(cnt).get("ROAD_TYPE")), Types.INTEGER);
					chkData(7, String.valueOf(list.get(cnt).get("TOTAL_LANE")), Types.INTEGER);
					chkData(8, String.valueOf(list.get(cnt).get("ACC_LANE")), Types.INTEGER);
					pstmt.setString(9, list.get(cnt).get("ST_GUGAN").toString());
					pstmt.setString(10, list.get(cnt).get("ED_GUGAN").toString());
					pstmt.setString(11, list.get(cnt).get("ST_TIME").toString());
					pstmt.setString(12, list.get(cnt).get("ED_TIME").toString());
				} else if ("DMB_ACC_STDLINK".equals(tableNm)) {
					sql = "INSERT INTO DMB_ACC_STDLINK " + "(ID, SUB_ID, SEQ_ID, STD_ID) VALUES " + "(?, ?, ?, ?)";

					// 쿼리 수행 객체 생성
					pstmt = conn.prepareStatement(sql);

					// 변수 타입에 따라 setting
					pstmt.setString(1, list.get(cnt).get("ID").toString());
					chkData(2, String.valueOf(list.get(cnt).get(subId)), Types.INTEGER);
					chkData(3, String.valueOf(list.get(cnt).get("SEQ_ID")), Types.INTEGER);
					chkData(4, String.valueOf(list.get(cnt).get("STD_ID")), Types.INTEGER);
				} else if ("DMB_ACC_LINK".equals(tableNm)) {
					sql = "INSERT INTO DMB_ACC_LINK " + "(ID, SUB_ID, MAPVER, SEQ_ID, MESH, LINK, DIR) VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?)";

					// 쿼리 수행 객체 생성
					pstmt = conn.prepareStatement(sql);

					// 변수 타입에 따라 setting
					pstmt.setString(1, list.get(cnt).get("ID").toString());
					chkData(2, String.valueOf(list.get(cnt).get(subId)), Types.INTEGER);
					pstmt.setString(3, list.get(cnt).get("MAPVER").toString());
					chkData(4, String.valueOf(list.get(cnt).get("SEQ_ID")), Types.INTEGER);
					chkData(5, String.valueOf(list.get(cnt).get("MESH")), Types.INTEGER);
					chkData(6, String.valueOf(list.get(cnt).get("LINK")), Types.INTEGER);
					chkData(7, String.valueOf(list.get(cnt).get("DIR")), Types.INTEGER);
				}

				if (pstmt != null) {
					pstmt.executeUpdate(); // insert 쿼리문 실행
					pstmt.clearParameters(); // PreparedStatement 재사용하기 위해 입력받은 파라미터 clear
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());

			return 0;
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					logger.info("pstmt colsed");
				}

			} catch (SQLException e) {
				logger.error(e.toString());
			}
		}

		return cnt;
	}

	private void chkData(int cnt, String data, int type) {

		try {
			if ("".equals(String.valueOf(data))) { // 값이 null인 경우
				pstmt.setNull(cnt, type);
			} else { // 값이 null이 아닌 경우
				if (type == Types.INTEGER) {
					pstmt.setInt(cnt, Integer.parseInt(data));
				} else if (type == Types.FLOAT) {
					pstmt.setFloat(cnt, Float.parseFloat(data));
				}
			}
		} catch (SQLException e) {
			logger.error(e.toString());
		}
	}
}
