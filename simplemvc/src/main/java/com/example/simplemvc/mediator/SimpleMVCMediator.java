package com.example.simplemvc.mediator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simplemvc.dao.IDAO;
import com.example.simplemvc.dao.ISimpleMVCDAO;

@Service
public class SimpleMVCMediator implements ISimpleMVCMediator {

	@Autowired
	private ISimpleMVCDAO simpleMVCDAO;

	@Override
	public String consultSimple() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDAO getDAO() {
		return this.simpleMVCDAO;
	}

}
