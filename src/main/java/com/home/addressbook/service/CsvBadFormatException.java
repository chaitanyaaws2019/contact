package com.home.addressbook.service;

@SuppressWarnings("serial")
public class CsvBadFormatException extends ServiceException {

	public CsvBadFormatException() {
	}
	
	public CsvBadFormatException(String msg) {
		super(msg);
	}

	public CsvBadFormatException(Throwable ex) {
		super(ex);
	}

	public CsvBadFormatException(String msg, Throwable ex) {
		super(msg, ex);
	}

}
