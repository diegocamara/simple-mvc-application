package com.example.simplemvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {

	@RequestMapping(value = "/testing", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String testing() {
		return "testing";
	}

}
