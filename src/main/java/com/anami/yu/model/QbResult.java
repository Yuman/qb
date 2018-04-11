package com.anami.yu.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class QbResult {
	private String id;
	private String queryText;
	private List<JsonObject> data;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public List<JsonObject> getData() {
		return data;
	}
	public void setData(List<JsonObject> data) {
		this.data = data;
	}
	@Override
	public String toString(){
		return queryText +": " + data.toString();
	}
}
