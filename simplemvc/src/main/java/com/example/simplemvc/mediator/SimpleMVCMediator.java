package com.example.simplemvc.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simplemvc.dao.ICRUDDAO;
import com.example.simplemvc.dao.SimpleMVCDAO;
import com.example.simplemvc.model.Simple;

@Service
public class SimpleMVCMediator extends AbstractCRUDMediator<Simple, Integer> implements ISimpleMVCMediator {

	@Autowired
	private SimpleMVCDAO simpleMVCDAO;

	@Override
	protected ICRUDDAO<Simple, Integer> getDAO() {
		return this.simpleMVCDAO;
	}

}
