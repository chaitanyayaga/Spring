package com.springboot.webapp.SpringWebApp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springboot.webapp.SpringWebApp.model.Todo;
import com.springboot.webapp.SpringWebApp.service.TodoService;

@Controller // @SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) { // method name can be anything annotation makes the magic
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
	
	@RequestMapping(value="/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model){
		String name = getLoggedInUserName(model);
		model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}

/*	private String getLoggedInUserName(ModelMap model) {
		return (String) model.get("name");
	} */
	
	private String getLoggedInUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}
	
	@RequestMapping(value="/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model){
		model.addAttribute("todo", new Todo(0, getLoggedInUserName(model), "Default Desc",
				new Date(), false));
		//model.addAttribute("todo" , new Todo());
		return "todo";
	}
	
	//@RequestMapping(value="/add-todo", method = RequestMethod.POST)
	//public String addTodo(ModelMap model, @RequestParam String desc){
	@PostMapping(value="/add-todo")
	public String addTodo(ModelMap model,@Valid Todo todo, BindingResult result){
		//String name = (String) model.get("name");
		//model.put("todos", service.retrieveTodos(name));
		if(result.hasErrors()){
			return "todo";
		}
		service.addTodo(getLoggedInUserName(model), todo.getDesc(), todo.getTargetDate(), false);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) throws Exception{
		service.deleteTodo(id);
		if(id == 1) {
			throw new Exception();
		}
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/update-todo", method = RequestMethod.GET)
	public String showUpdateTodo(@RequestParam int id, ModelMap model){
		Todo todo= service.retrieveTodo(id);
		model.put("todo", todo);
		return "todo";
	}
	
	@PostMapping(value="/update-todo")
	public String updateTodo(ModelMap model,@Valid Todo todo, BindingResult result){
		todo.setUser(getLoggedInUserName(model));
		if(result.hasErrors()){
			return "todo";
		}
		service.updateTodo(todo);
		//Todo todo= service.u
		model.put("todo", todo);
		return "redirect:/list-todos";
	}
	
}