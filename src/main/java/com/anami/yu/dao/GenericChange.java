package com.anami.yu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anami.yu.model.QbResult;
import com.google.gson.JsonObject;

public class GenericChange implements QueryExecutor {
	static Logger logger = LoggerFactory.getLogger(GenericChange.class);
	private DataSource dataSource;
	Connection con = null;

	public GenericChange(DataSource dataSource) {
		this.dataSource = dataSource;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public QbResult execute(String query) {
		PreparedStatement ps = null;
		int rowCount = 0;
		QbResult jsonResponse = new QbResult();
		jsonResponse.setQueryText(query);
		try {
			ps = con.prepareStatement(query);
			rowCount = ps.executeUpdate();
			jsonResponse.setData(convertToJSON(rowCount));
			ps.close();
		} catch (SQLException e) {
			jsonResponse.setData(convertToJSON(0));
			jsonResponse.setQueryText(e.getMessage() + " - " + query);
			logger.error("Error stack: ", e);
			e.printStackTrace();
		} finally {

		}
		return jsonResponse;
	}

	private static List<JsonObject> convertToJSON(int rowCount) {
		List<JsonObject> data = new LinkedList<JsonObject>();
		JsonObject obj = new JsonObject();
		obj.addProperty("rowCount", rowCount);
		data.add(obj);
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
