package com.prabhas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.prabhas.dao.StudentDao;
import com.prabhas.model.Student;

@Controller
public class Mycontroller {
	@Autowired
	private StudentDao studentDao;

	@GetMapping("/")
	public ModelAndView index() {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("index.html");
	        return modelAndView;
	}
	@GetMapping("/login")
	public ModelAndView login() {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("login.html");
	        return modelAndView;
	}
	@PostMapping("/submit")
	public ModelAndView submitForm(@RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam("city") String city) {
    Student student = new Student(id, name, city);
    studentDao.update(student);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("success.html"); // Create this page to show confirmation
    return modelAndView;
}

	@GetMapping("/register")
	public ModelAndView register() {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("register.html");
	        return modelAndView;
	}
	@GetMapping("/loginpage")
	public ModelAndView loginpage() {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("loginpage.html");
	        return modelAndView;
	}
	@GetMapping("/registerpage")
	public ModelAndView registerpage() {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("registerpage.html");
	        return modelAndView;
	}

}
