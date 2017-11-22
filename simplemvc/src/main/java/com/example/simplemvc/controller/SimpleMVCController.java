package com.example.simplemvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplemvc.mediator.IMediator;
import com.example.simplemvc.mediator.ISimpleMVCMediator;

@RestController
public class SimpleMVCController extends AbstractController<Object> implements ISimpleMVCController {

	@Autowired
	private ISimpleMVCMediator simpleMVCMediator;

	@Override
	public IMediator getMediator() {
		return this.simpleMVCMediator;
	}

}
