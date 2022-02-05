package com.springboot.springbootadvanced.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.springbootadvanced.service.SurveyService;
import com.springboot.springbootadvanced.model.Question;
import com.springboot.springbootadvanced.model.Survey;

@RestController
public class SurveyController {
	
	@Autowired
	private SurveyService surveyService;

	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveQuestions(@PathVariable String surveyId) {
		return surveyService.retrieveQuestions(surveyId);
	}
	// message converters : Object -> json  JSON -XML
	// Jackson Data Bind : Object --> json which happens in the background

	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveDetailsForQuestion(@PathVariable String surveyId,
			@PathVariable String questionId) {
		return surveyService.retrieveQuestion(surveyId, questionId);
	}
	
	 @PostMapping("/surveys/{surveyId}/questions")
	 public ResponseEntity<?>  addQuestionToSurvey(@PathVariable String surveyId,
	            @RequestBody Question question) {

	        Question createdTodo = surveyService.addQuestion(surveyId, question);

	        if (createdTodo == null) {
	            return ResponseEntity.noContent().build();
	        }
            // when question is created we should return response back with URI of the response in 
	        // in the response header and status of 201 created
	        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();

	        return ResponseEntity.created(location).build();

	    }


}
