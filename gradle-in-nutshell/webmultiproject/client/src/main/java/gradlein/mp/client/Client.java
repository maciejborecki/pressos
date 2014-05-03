package gradlein.mp.client;

import gradlein.mp.domain.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

public class Client {

	private static final String HOST = "http://localhost:8080/";

	private CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	private CloseableHttpResponse response;

	/**
	 * 
	 * @param id
	 *            when null =
	 */
	public void list(Integer id) throws ClientProtocolException, IOException {
		HttpGet getRequest = new HttpGet(HOST + "persons");
		getRequest.addHeader("accept", "application/json");
		execute(getRequest);
		String inputLine;
		BufferedReader in = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
		}
	}

	private void execute(HttpRequestBase request) throws IOException,
			ClientProtocolException {
		System.out.println("Sending " + request.getMethod() + " request at:"
				+ request.getURI());
		response = httpClient.execute(request);
		System.out.println("Response code: " + response.getStatusLine().getStatusCode());
		System.out.println("Headers:");
		for(Header header: response.getAllHeaders()) {
			System.out.println(header);
		}
	}

	public void add(String firstName, String lastName)
			throws ClientProtocolException, IOException {
		Person newPerson = new Person(firstName, lastName);
		Gson gson = new Gson();
		String json = gson.toJson(newPerson);

		HttpPost postRequest = new HttpPost(HOST + "persons");

		postRequest.setEntity(new StringEntity(json));
		postRequest.addHeader("Content-Type", "application/json");

		execute(postRequest);

	}

	private void close() {
		try {
			httpClient.close();
			if (response != null) {
				response.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = null;
		try {
			if (args.length == 1 && "list".equals(args[0])) {
				client = new Client();
				client.list(null);
			} else if (args.length == 3 && "add".equals(args[0])) {
				client = new Client();
				client.add(args[1], args[2]);
			} else {
				printHelp();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
			System.out.println("\n");
		}
	}

	private static void printHelp() {
		System.out.println("Usage:");
		System.out.println("list                       - lists all persons");
		System.out.println("add <firstName> <lastName> - adds new person");
	}
}
