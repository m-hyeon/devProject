package com.devProject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

public class ExcelDao {

	PreparedStatement pstmt = null;

	public void insertExcelData(Connection conn, String tableNm, List<HashMap<String, Object>> list) {

		String sql = null;

		try {
			for (int i = 0; i < list.size(); i++) {
				// 테이블 마다 쿼리문 setting
				if ("DMB_ACC_MAIN".equals(tableNm)) {
					sql = "INSERT INTO DMB_ACC_MAIN "
							+ "(ID, RPT_ID, SRC_FLAG, SVC_NAME, MAKE_FLAG, LON, LAT, DIR, STD_ID, STD_LENGTH"
							+ ", CTL_TYPE, SPEED, ACC_TITLE, ACC_DESC, ST_TIME, ED_TIME, LON_ORG, LAT_ORG"
							+ ", DIR_ORG, CTL_TIME, SVC_FLAG, WEEK, LGRP_CODE, MGRP_CODE, SGRP_CODE, GCODE, LANE_GCODE, UPTIME)"
							+ " VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,?, ?, ?, ? )";

					// 쿼리 수행 객체 생성
					pstmt = conn.prepareStatement(sql);

					// 변수 타입에 따라 setting
					pstmt.setString(1, list.get(i).get("ID").toString());
					pstmt.setString(2, list.get(i).get("RPT_ID").toString());
					chkData(3, String.valueOf(list.get(i).get("SRC_FLAG")), Types.INTEGER);
					pstmt.setString(4, list.get(i).get("SVC_NAME").toString());
					chkData(5, String.valueOf(list.get(i).get("MAKE_FLAG")), Types.INTEGER);
					chkData(6, String.valueOf(list.get(i).get("LON")), Types.FLOAT);
					chkData(7, String.valueOf(list.get(i).get("LAT")), Types.FLOAT);
					chkData(8, String.valueOf(list.get(i).get("DIR")), Types.INTEGER);
					chkData(9, String.valueOf(list.get(i).get("STD_ID")), Types.INTEGER);
					chkData(10, String.valueOf(list.get(i).get("STD_LENGTH")), Types.INTEGER);
					chkData(11, String.valueOf(list.get(i).get("CTL_TYPE")), Types.INTEGER);
					chkData(12, String.valueOf(list.get(i).get("SPEED")), Types.INTEGER);
					pstmt.setString(13, list.get(i).get("ACC_TITLE").toString());
					pstmt.setString(14, list.get(i).get("ACC_DESC").toString());
					pstmt.setString(15, list.get(i).get("ST_TIME").toString());
					pstmt.setString(16, list.get(i).get("ED_TIME").toString());
					chkData(17, String.valueOf(list.get(i).get("LON_ORG")), Types.FLOAT);
					chkData(18, String.valueOf(list.get(i).get("LAT_ORG")), Types.FLOAT);
					chkData(19, String.valueOf(list.get(i).get("DIR_ORG")), Types.INTEGER);
					chkData(20, String.valueOf(list.get(i).get("CTL_TIME")), Types.INTEGER);
					chkData(21, String.valueOf(list.get(i).get("SVC_FLAG")), Types.INTEGER);
					pstmt.setString(22, list.get(i).get("WEEK").toString());
					chkData(23, String.valueOf(list.get(i).get("LGRP_CODE")), Types.INTEGER);
					chkData(24, String.valueOf(list.get(i).get("MGRP_CODE")), Types.INTEGER);
					chkData(25, String.valueOf(list.get(i).get("SGRP_CODE")), Types.INTEGER);
					chkData(26, String.valueOf(list.get(i).get("GCODE")), Types.INTEGER);
					chkData(27, String.valueOf(list.get(i).get("LANE_GCODE")), Types.INTEGER);
					pstmt.setString(28, list.get(i).get("UPTIME").toString());
				} else if ("DMB_ACC_SUB".equals(tableNm)) {
					sql = "INSERT INTO DMB_ACC_SUB " + "(ID, SUB_ID, ADMIN_CODE, ADMIN_NAME, ROAD_NAME, ROAD_TYPE"
							+ ", TOTAL_LANE, ACC_LANE, ST_GUGAN, ED_GUGAN, ST_TIME, ED_TIME) " + " VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

					// 쿼리 수행 객체 생성
					pstmt = conn.prepareStatement(sql);

					// 변수 타입에 따라 setting
					pstmt.setString(1, list.get(i).get("ID").toString());
					chkData(2, String.valueOf(list.get(i).get("SUB_ID")), Types.INTEGER);
					chkData(3, String.valueOf(list.get(i).get("ADMIN_CODE")), Types.INTEGER);
					pstmt.setString(4, list.get(i).get("ADMIN_NAME").toString());
					pstmt.setString(5, list.get(i).get("ROAD_NAME").toString());
					chkData(6, String.valueOf(list.get(i).get("ROAD_TYPE")), Types.INTEGER);
					chkData(7, String.valueOf(list.get(i).get("TOTAL_LANE")), Types.INTEGER);
					chkData(8, String.valueOf(list.get(i).get("ACC_LANE")), Types.INTEGER);
					pstmt.setString(9, list.get(i).get("ST_GUGAN").toString());
					pstmt.setString(10, list.get(i).get("ED_GUGAN").toString());
					pstmt.setString(11, list.get(i).get("ST_TIME").toString());
					pstmt.setString(12, list.get(i).get("ED_TIME").toString());
				} else if ("DMB_ACC_STDLINK".equals(tableNm)) {
					sql = "INSERT INTO DMB_ACC_STDLINK " + "(ID, SUB_ID, SEQ_ID, STD_ID)" + " VALUES " + "(?, ?, ?, ?)";

					// 쿼리 수행 객체 생성
					pstmt = conn.prepareStatement(sql);

					// 변수 타입에 따라 setting
					pstmt.setString(1, list.get(i).get("ID").toString());
					chkData(2, String.valueOf(list.get(i).get("SUB_ID")), Types.INTEGER);
					chkData(3, String.valueOf(list.get(i).get("SEQ_ID")), Types.INTEGER);
					chkData(4, String.valueOf(list.get(i).get("STD_ID")), Types.INTEGER);
				} else if ("DMB_ACC_LINK".equals(tableNm)) {
					sql = "INSERT INTO DMB_ACC_LINK " + "(ID, SUB_ID, MAPVER, SEQ_ID, MESH, LINK, DIR)" + " VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?)";

					// 쿼리 수행 객체 생성
					pstmt = conn.prepareStatement(sql);

					// 변수 타입에 따라 setting
					pstmt.setString(1, list.get(i).get("ID").toString());
					chkData(2, String.valueOf(list.get(i).get("SUB_ID")), Types.INTEGER);
					pstmt.setString(3, list.get(i).get("MAPVER").toString());
					chkData(4, String.valueOf(list.get(i).get("SEQ_ID")), Types.INTEGER);
					chkData(5, String.valueOf(list.get(i).get("MESH")), Types.INTEGER);
					chkData(6, String.valueOf(list.get(i).get("LINK")), Types.INTEGER);
					chkData(7, String.valueOf(list.get(i).get("DIR")), Types.INTEGER);
				}

				// insert 쿼리문 실행
				System.out.println(pstmt.toString());
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("pstmt colse");
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void chkData(int cnt, String data, int type) throws Exception {

		if ("".equals(String.valueOf(data))) { // 값이 null인 경우
			pstmt.setNull(cnt, type);
		} else {
			if (type == Types.INTEGER) {
				pstmt.setInt(cnt, Integer.parseInt(data));
			} else if (type == Types.FLOAT) {
				pstmt.setFloat(cnt, Float.parseFloat(data));
			}
		}
	}
}
