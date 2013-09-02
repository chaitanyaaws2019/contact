package com.home.addressbook.service;

import java.io.InputStream;

public interface ImportAddressBookService {
	
	public int importItems(InputStream input) throws ServiceException;

}
