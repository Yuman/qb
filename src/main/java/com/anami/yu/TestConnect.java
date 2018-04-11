package com.anami.yu;

import com.anami.yu.model.QbQuery;
import com.anami.yu.wsclient.QuickbooksConsumer;
import com.google.gson.Gson;

public class TestConnect {

	public static void main(String[] args){
		new TestConnect().sendQuery();
	}

	public void sendQuery() {
		Gson gson = new Gson();
		QuickbooksConsumer consumer = new QuickbooksConsumer();
		QbQuery qry = new QbQuery();
		qry.setName("1st query");
		qry.setQuery("select * from item");
		String body = gson.toJson(qry).toString();
		System.out.println("Sent: " + body);
		consumer.sendQuery(body);
		
		QbQuery ins = new QbQuery();
		ins.setName("2nd query");
		ins.setQuery("delete from item  where id=11");
		String bodyi = gson.toJson(ins).toString();
		System.out.println("Sent: " + bodyi);
		consumer.sendQuery(bodyi);
	}

}
