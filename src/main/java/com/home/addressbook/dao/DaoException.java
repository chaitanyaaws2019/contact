package com.home.addressbook.dao;

@SuppressWarnings("serial")
public class DaoException extends Exception {
	
	public DaoException() {
	}
	
	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(Throwable ex) {
		super(ex);
	}

	public DaoException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
