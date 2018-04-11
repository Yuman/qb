package com.anami.yu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anami.yu.model.QbResult;
import com.google.gson.JsonObject;

public class GenericRetrieval implements QueryExecutor {
	static Logger logger = LoggerFactory.getLogger(GenericRetrieval.class);
	private DataSource dataSource;
	Connection con = null;

	public GenericRetrieval(DataSource dataSource) {
		this.dataSource = dataSource;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public QbResult execute(String query) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		QbResult jsonResponse = new QbResult();
		jsonResponse.setQueryText(query);
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			jsonResponse.setData(convertToJSON(rs));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			jsonResponse.setQueryText(e.getMessage() + " - " + query);
			logger.error("Error stack: ", e);
			e.printStackTrace();
		} finally {

		}
		return jsonResponse;
	}

	private static List<JsonObject> convertToJSON(ResultSet resultSet) throws SQLException {
		List<JsonObject> data = new LinkedList<JsonObject>();
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			JsonObject obj = new JsonObject();
			for (int i = 0; i < total_rows; i++) {
				String proName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
				Object proVal = resultSet.getObject(i + 1);
				obj.addProperty(proName, (proVal == null) ? null : proVal.toString());
			}
			data.add(obj);
		}

		return data;
	}
	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
