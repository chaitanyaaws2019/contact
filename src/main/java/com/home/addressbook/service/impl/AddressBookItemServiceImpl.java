package com.home.addressbook.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.home.addressbook.dao.AddressBookItemDAO;
import com.home.addressbook.dao.DaoException;
import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.domain.Gender;
import com.home.addressbook.service.AddressBookItemService;
import com.home.addressbook.service.ServiceException;

@Service
public class AddressBookItemServiceImpl implements AddressBookItemService {
	private static final Logger logger = LoggerFactory.getLogger(AddressBookItemServiceImpl.class);
	
	@Inject
	private AddressBookItemDAO addressBookItemDAO;

	@Override
	public void persist(AddressBookItem item) throws ServiceException {
		try {
			addressBookItemDAO.persist(item);
		} catch (DaoException ex) {
			logger.error("error", ex);
			throw new ServiceException(ex);
		}
	}

	@Override
	public int getNumberOfMales() throws ServiceException {
		try {
			return addressBookItemDAO.getNumberOfItemsByGender(Gender.MALE);
		} catch (DaoException ex) {
			logger.error("error", ex);
			throw new ServiceException(ex);
		}
	}

	@Override
	public String getNameOfOldestPerson() throws ServiceException {
		try {
			AddressBookItem itemForOldestPerson = addressBookItemDAO.findItemForOldestPerson();
			if (itemForOldestPerson == null) {
				return null;
			}
			return itemForOldestPerson.getName();
		} catch (DaoException ex) {
			logger.error("error", ex);
			throw new ServiceException(ex);
		}
	}

}
