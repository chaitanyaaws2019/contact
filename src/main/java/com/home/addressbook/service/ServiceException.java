package com.home.addressbook.service;

@SuppressWarnings("serial")
public class ServiceException extends Exception {

	public ServiceException() {
	}
	
	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable ex) {
		super(ex);
	}

	public ServiceException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
