package com.example.simplemvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

}
