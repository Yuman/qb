package com.anami.yu.dao;

import com.anami.yu.model.QbResult;

public interface QueryExecutor {
	QbResult execute(String query);
	void close();
}
