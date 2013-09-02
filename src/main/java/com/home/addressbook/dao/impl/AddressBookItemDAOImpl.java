package com.home.addressbook.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.home.addressbook.dao.AddressBookItemDAO;
import com.home.addressbook.dao.DaoException;
import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.domain.Gender;

@Repository
public class AddressBookItemDAOImpl implements AddressBookItemDAO {
	private static final Logger logger = LoggerFactory.getLogger(AddressBookItemDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	@Transactional
	public void persist(AddressBookItem item) throws DaoException {
		try {
			getEntityManager().persist(item);
		} catch (Throwable ex) {
			logger.error("error", ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public int getNumberOfItemsByGender(Gender gender) throws DaoException {
		try {
			TypedQuery<Long> q = getEntityManager().createNamedQuery("getNumberOfItemsByGender", Long.class);
			q.setParameter("gender", gender);
			Long r = q.getSingleResult();
			return r.intValue();
		} catch (Throwable ex) {
			logger.error("error", ex);
			throw new DaoException(ex);
		}
	}

	@Override
	public AddressBookItem findItemForOldestPerson() throws DaoException {
		try {
			TypedQuery<AddressBookItem> q = getEntityManager().createQuery("select o from AddressBookItem o order by o.dateOfBirth asc", AddressBookItem.class);
			q.setMaxResults(1);
			List<AddressBookItem> r = q.getResultList();
			if (r.size() < 1) {
				return null;
			}
			return r.get(0);
		} catch (Throwable ex) {
			logger.error("error", ex);
			throw new DaoException(ex);
		}
	}

}
