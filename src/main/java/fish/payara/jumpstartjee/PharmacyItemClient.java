package fish.payara.jumpstartjee;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PharmacyItemClient {

	private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	public static void main(String[] args) throws Exception {

		PharmacyItemClient obj = new PharmacyItemClient();

		obj.sendGet();

	}

	private void sendGet() throws Exception {

		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(URI.create("http://localhost:8080/jee-jumpstart/api/pharmacy/items")).build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		// print status code
		System.out.println(response.statusCode());

		// print response body
		System.out.println(response.body());

	}

}
