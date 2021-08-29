

package com.smoothstack.EurekaServer;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EurekaServerApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;

	@Test
	public void testDashboard() throws URISyntaxException {
		final String baseUrl = "http://localhost:" + randomServerPort + "/";
		URI uri = new URI(baseUrl);

		HttpHeaders headers = new HttpHeaders();

		ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				String.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
}

