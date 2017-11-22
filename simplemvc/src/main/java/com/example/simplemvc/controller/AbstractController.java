package com.example.simplemvc.controller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.simplemvc.mediator.IMediator;

public abstract class AbstractController<T> implements ISimpleController<T> {

	@Override
	public T create(@RequestBody T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T update(@RequestBody T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub

	}

	public abstract IMediator getMediator();

}
