package com.oodlefinance.pradeep.rajendran.internal;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = InternalApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class InternalApplicationIntegrationTest {
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@LocalServerPort
    private int port;
	
	@Before
	public void setup() {
		Message message1 = new Message();
		message1.setMessage("hello world1");
		
		messageService.create(message1);
	}
	
	@After
	public void tearDown() {
		this.messageService.clear();
	}
	
	@Test
	public void shouldCreateMessageSuccessfully() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");      

		HttpEntity<String> request = new HttpEntity<>("{\"message\":\"hi\"}", headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/message", request, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	public void shouldFailCreatingMessage() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");      

		HttpEntity<String> request = new HttpEntity<>("{\"message\":\"\"}", headers);
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/message", request, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(response.getBody().contains("Message is empty"));
	}

	@Test
	public void shouldReturnAllMessages() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/message", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("hello world1"));
	}

	@Test
	public void shouldReturnMessageNotFound() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/message/0", String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertTrue(response.getBody().contains("Message not found"));
	}
	
	@Test
	public void shouldDeleteMessageSuccessfully() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json");      

		HttpEntity<String> request = new HttpEntity<>("{\"message\":\"hi\"}", headers);
		ResponseEntity<String> createResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/message", request, String.class);
		
		this.restTemplate.delete("http://localhost:" + port + "/message/" + createResponse.getBody());
		
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/message/" + createResponse.getBody(), String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertTrue(response.getBody().contains("Message not found"));
	}
}
