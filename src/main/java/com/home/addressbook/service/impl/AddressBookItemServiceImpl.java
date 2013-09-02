package com.home.addressbook.service.impl;

import org.springframework.stereotype.Service;

import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.service.AddressBookItemService;
import com.home.addressbook.service.ServiceException;

@Service
public class AddressBookItemServiceImpl implements AddressBookItemService {

	@Override
	public void persist(AddressBookItem item) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfMales() throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNameOfOldestPerson() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
