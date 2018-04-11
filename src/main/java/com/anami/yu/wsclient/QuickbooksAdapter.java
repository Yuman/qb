package com.anami.yu.wsclient;

import com.anami.yu.model.QbQuery;
import com.anami.yu.model.QbQueryResponse;
import com.anami.yu.model.QbResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import java.util.List;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

public class QuickbooksAdapter {

	private String brokerhost = System.getenv("brokerhost");
	ClientConfig config = new DefaultClientConfig();
	Client client = null;

	public QuickbooksAdapter() {
		config.getClasses().add(JacksonJaxbJsonProvider.class);
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		client = Client.create(config);
	}

	public List<QbQuery> takeQuery() {

		WebResource webResource = client.resource(brokerhost + "/qbquery");

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		System.out.println("\nResponse on takeQuery from broker server .... \n");

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		// System.out.println(response.getEntity(String.class));
		Gson gson = new Gson();
		QbQueryResponse rsp = response.getEntity(QbQueryResponse.class);
		System.out.println(gson.toJson(rsp));
		return rsp.queries;
	}

	public String deleteQuery(String id) {

		WebResource webResource = client.resource(brokerhost + "/qbquery/" + id);

		ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);
		System.out.println("\nResponse on deleteQuery from broker server .... \n");

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		Gson gson = new Gson();
		QbQueryResponse rsp = response.getEntity(QbQueryResponse.class);
		System.out.println(gson.toJson(rsp));
		return rsp.ticket;
	}

	public String sendResult(String result) {
		Gson gson = new Gson();

		WebResource webResource = client.resource(brokerhost + "/qbresult");

		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, result);

		if (response.getStatus() != 201) {
			// throw new RuntimeException("Failed : HTTP error code : " +
			// response.getStatus());
		}

		System.out.println("\nResponse on sendResult from broker server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
		return "";
	}
}
