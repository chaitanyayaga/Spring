package com.springboot.springbootadvanced.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.springbootadvanced.SpringBootAdvancedApplication;
// Part 1: Initialize and launch spring boot application
//           @RunWith(SpringRunner.class) --> launches spring context
//           @SpringBootTest(classes = Appliction.class, 
//              webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) --Launches application and uses random port
//           @LocalServerPort
 //             private int port
import com.springboot.springbootadvanced.model.Question;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootAdvancedApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerIT {
    
	private static final String SURVEYS_SURVEY1_QUESTIONS = "/surveys/Survey1/questions";
	@LocalServerPort
	private int port;
	
	@Test
	public void testJsonAssert() throws JSONException {
		JSONAssert.assertEquals("{id:1}", "{id:   1,name:chaitanya}", false);
		                   //  expected      actual
	}
	
	String retrievespecificQuestion = "/surveys/Survey1/questions/Question1";
	
	
	// Integration Testing 
	@Test
	void testRetrieveSurveyQuestion() throws JSONException {
		
		String url = createURLWithPort(retrievespecificQuestion);
		
		TestRestTemplate restTemplate = new TestRestTemplate();
		
		//String output = restTemplate.getForObject(url, String.class);
		HttpHeaders headers = new HttpHeaders();
		List<MediaType> acceptType = new ArrayList<MediaType>();
		acceptType.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptType);
		//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.GET, entity, String.class);
		
		System.out.println("Response : " + response.getBody());
		String expected =  "{id:Question1,description:Largest Country in the World,correctAnswer:Russia}";
		String expected1 =  "{id:Question1,description:Largest}";
		//JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.LENIENT);
		//JSONAssert.assertEquals(expected, response.getBody(), JSONCompareMode.LENIENT);
		// space parsing issues
		assertTrue(response.getBody().contains(expected));  
		
 	}
	
	@Test
    public void retrieveSurveyQuestions() throws Exception {
		String url = createURLWithPort(SURVEYS_SURVEY1_QUESTIONS);

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();
		List<MediaType> acceptType = new ArrayList<MediaType>();
		acceptType.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptType);
		HttpEntity entity = new HttpEntity<String>(null, headers);
		ResponseEntity<List<Question>> response = restTemplate.exchange(url,
				HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Question>>() {
				});
          List<String> countries = new ArrayList<String>();
          countries.add("India");
          countries.add("Russia");
          countries.add("United States");
          countries.add("China");
		Question sampleQuestion = new Question("Question1",
				"Largest Country in the World", "Russia",
						countries);

		assertTrue(response.getBody().contains(sampleQuestion));
    }
	
	@Test
	public void addQuestion() {

		String url = createURLWithPort(SURVEYS_SURVEY1_QUESTIONS);

		TestRestTemplate restTemplate = new TestRestTemplate();

		HttpHeaders headers = new HttpHeaders();
		List<MediaType> acceptType = new ArrayList<MediaType>();
		acceptType.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptType);

		 List<String> countries = new ArrayList<String>();
         countries.add("India");
         countries.add("Russia");
         countries.add("United States");
         countries.add("China");
		Question question = new Question("DOESNTMATTER", "Question1", "Russia",
				countries);

		HttpEntity entity = new HttpEntity<Question>(question, headers);

		ResponseEntity<String> response = restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

		assertTrue(actual.contains("/surveys/Survey1/questions/"));

	}
	
	private String createURLWithPort(final String uri) {
		return "http://localhost:" + port + uri;
	}

}
