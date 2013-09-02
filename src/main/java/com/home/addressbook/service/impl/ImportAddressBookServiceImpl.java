package com.home.addressbook.service.impl;

import java.io.InputStream;

import javax.inject.Inject;

import com.home.addressbook.dao.AddressBookItemDAO;
import com.home.addressbook.service.ImportAddressBookService;
import com.home.addressbook.service.ServiceException;

public class ImportAddressBookServiceImpl implements ImportAddressBookService {

	@Inject
	private AddressBookItemDAO addressBookItemDAO;
	
	
	@Override
	public int importItems(InputStream input) throws ServiceException {
		return 0;
	}

}
