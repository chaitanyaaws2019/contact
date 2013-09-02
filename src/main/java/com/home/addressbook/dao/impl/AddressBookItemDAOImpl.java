package com.home.addressbook.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.home.addressbook.dao.AddressBookItemDAO;
import com.home.addressbook.dao.DaoException;
import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.domain.Gender;

@Repository
public class AddressBookItemDAOImpl implements AddressBookItemDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void persist(AddressBookItem item) throws DaoException {
	}

	@Override
	public int getNumberOfItemsByGender(Gender gender) throws DaoException {
		return 0;
	}

	@Override
	public AddressBookItem findItemForOldestPerson() throws DaoException {
		return null;
	}

}
