package com.anami.yu.wsclient;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import com.anami.yu.model.QbQuery;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class QuickbooksConsumer {
	
	 private String brokerhost =  System.getenv("brokerhost"); 
	 private Gson gson;
	 ClientConfig config = new DefaultClientConfig();
	 public QuickbooksConsumer(){
	    config.getClasses().add(JacksonJaxbJsonProvider.class);
	    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
	 }
	public String TakeResult() {
		Client client = Client.create(config);

		WebResource webResource = client.resource(brokerhost+ "/qbresult");

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);

		System.out.println("Output from Server .... \n");
		System.out.println(output);
		return "";
	}

	public String sendQuery(String qry) {
		Client client = Client.create(config);
		Gson gson = new Gson();
				
		WebResource webResource = client.resource(brokerhost+"/qbquery");

		System.out.println("Sending query: " + qry);
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, qry);

		if (response.getStatus() != 201) {
		//	throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println("resp status: " +response.getStatus());
		System.out.println(output);
		return "";
	}
}
