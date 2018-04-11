package com.anami.yu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.anami.yu.dao.GenericChange;
import com.anami.yu.dao.GenericRetrieval;
import com.anami.yu.dao.QueryExecutor;
import com.anami.yu.model.QbQuery;
import com.anami.yu.model.QbResult;
import com.anami.yu.wsclient.QuickbooksAdapter;
import com.google.gson.Gson;

public class Batch {
	static Logger logger = LoggerFactory.getLogger(QbAdapter.class);
	QueryExecutor select, change;

	public Batch(ApplicationContext ctx) {
		select = ctx.getBean("genericRetrieval", GenericRetrieval.class);
		change = ctx.getBean("genericChange", GenericChange.class);
	}

	public void run() {
		QuickbooksAdapter adapter = new QuickbooksAdapter();
		List<QbQuery> queries = adapter.takeQuery();
		if (queries.isEmpty()) {
			return;
		}

		logger.info("\nPreparing to process queries...");

		for (QbQuery qry : queries) {
			logger.info(qry.toString());
			QbResult res = null;
			Gson gson = new Gson();
			if (qry.getQuery().startsWith("SELECT") || qry.getQuery().startsWith("select")) {
				res = select.execute(qry.getQuery());
			} else {
				res = change.execute(qry.getQuery());
			}
			res.setId(qry.getId());
			String qbres = gson.toJson(res).toString();
			logger.info("\nSending result: " + qbres);

			// QbResult restored = gson.fromJson(qbres, QbResult.class);
			adapter.sendResult(gson.toJson(res).toString());

			adapter.deleteQuery(qry.getId());
		}
		//logger.info("\nDone with queries. Closing connection ..");
		// select.close();
		// change.close();
	}
}
