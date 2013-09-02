package com.home.addressbook.service;

import com.home.addressbook.domain.AddressBookItem;

public interface AddressBookItemService {
	
	public void persist(AddressBookItem item) throws ServiceException;
	public int getNumberOfMales() throws ServiceException;
	public String getNameOfOldestPerson() throws ServiceException;

}
