package com.home.addressbook.dao;

import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.domain.Gender;

public interface AddressBookItemDAO {
	
	public void persist() throws DaoException;
	public int getNumberOfItemsByGender(Gender gender) throws DaoException;
	public AddressBookItem findItem4OldestPerson() throws DaoException;

}
