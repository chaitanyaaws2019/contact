package com.home.addressbook.dao;

import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.domain.Gender;

public interface AddressBookItemDAO {
	
	public void persist(AddressBookItem item) throws DaoException;
	public int getNumberOfItemsByGender(Gender gender) throws DaoException;
	public AddressBookItem findItemForOldestPerson() throws DaoException;

}
