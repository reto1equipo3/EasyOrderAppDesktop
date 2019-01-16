/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easyorderappdesktop.rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:EmpleadoREST [empleado]<br>
 * USAGE:
 * <pre>
 *        EmpleadoRESTClient client = new EmpleadoRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Imanol
 */
public class EmpleadoRESTClient {

	private WebTarget webTarget;
	private Client client;
	private static final String BASE_URI = "http://localhost:8080/EasyOrderAppServer/webresources";

	public EmpleadoRESTClient() {
		client = javax.ws.rs.client.ClientBuilder.newClient();
		webTarget = client.target(BASE_URI).path("empleado");
	}

	public <T> T find(Class<T> responseType, String id) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public void create(Object requestEntity) throws ClientErrorException {
		webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
	}

	public void update(Object requestEntity) throws ClientErrorException {
		webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
	}

	public <T> T iniciarSesion(Class<T> responseType, String login, String password) throws ClientErrorException {
		WebTarget resource = webTarget;
		resource = resource.path(java.text.MessageFormat.format("login/{0}/{1}", new Object[]{login, password}));
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public <T> T findAll(Class<T> responseType) throws ClientErrorException {
		WebTarget resource = webTarget;
		return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
	}

	public void delete(String id) throws ClientErrorException {
		webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
	}

	public void close() {
		client.close();
	}
	
}
