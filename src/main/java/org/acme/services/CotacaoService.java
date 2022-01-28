package org.acme.services;



import java.io.IOException;

import javax.inject.Singleton;

import org.acme.model.Cotacao;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@Singleton

@RegisterRestClient
public class CotacaoService {

	CloseableHttpClient httpCliente = HttpClients.createDefault();
	ObjectMapper objectMapper = new ObjectMapper();
	
	public Cotacao fetchCotacao(String data) throws IOException{
		
		String url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='"+data+"'&$top=100&$format=json";
		
		System.out.println(url);
		
		HttpGet  request = new HttpGet(url);
		request.addHeader("content-type", "application/json");
		
		CloseableHttpResponse response = httpCliente.execute(request);
		
		// não é o melhor jeito de mapear o json.
		String json = EntityUtils.toString(response.getEntity());
		JsonObject jsonObj = new JsonObject(json);
		JsonArray jarr = jsonObj.getJsonArray("value");		
		JsonObject jo = new JsonObject(jarr.getValue(0).toString());

		
		Cotacao cotacao = new Cotacao(jo.getString("cotacaoCompra"), jo.getString("cotacaoVenda"), jo.getString("dataHoraCotacao"));
		
		return cotacao;
		
		
	}
	
	
	
	
}
