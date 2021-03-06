package com.example.simplemvc.mediator;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.simplemvc.dao.ICRUDDAO;

public abstract class AbstractCRUDMediator<T, ID extends Serializable> implements ICRUDMediator<T, ID> {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T create(T json) {
		return getDAO().create(json);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public T update(ID id, T json) {
		return getDAO().update(id, json);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(ID id) {
		getDAO().delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public T findOne(ID id) {
		return getDAO().findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> listAll() {
		return getDAO().findAll();
	}

	protected abstract ICRUDDAO<T, ID> getDAO();

}
