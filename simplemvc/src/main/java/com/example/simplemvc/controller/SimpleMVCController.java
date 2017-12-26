package com.example.simplemvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplemvc.mediator.ICRUDMediator;
import com.example.simplemvc.mediator.ISimpleMVCMediator;
import com.example.simplemvc.model.Simple;

@RestController
public class SimpleMVCController extends AbstractController<Simple, Integer> implements ISimpleMVCController {

	@Autowired
	private ISimpleMVCMediator simpleMVCMediator;

	@Override
	public ICRUDMediator<Simple, Integer> getCRUDMediator() {
		return this.simpleMVCMediator;
	}

	@RequestMapping(value = "/testing", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String testing() {
		return "testing";
	}

}
