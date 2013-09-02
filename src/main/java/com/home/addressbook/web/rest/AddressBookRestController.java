package com.home.addressbook.web.rest;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.home.addressbook.service.AddressBookItemService;
import com.home.addressbook.service.ImportAddressBookService;
import com.home.addressbook.service.ServiceException;

@Controller
@RequestMapping(value="/rest/api/addressbook")
public class AddressBookRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(AddressBookRestController.class);
	
	@Inject
	private AddressBookItemService addressBookItemService;
	
	@Inject
	private ImportAddressBookService importAddressBookService;
	
	@RequestMapping(value="/number-of-males")
	public ResponseEntity<String> getNumberOfMales() {
		try {
			int numberOfMales = addressBookItemService.getNumberOfMales();
			return new ResponseEntity<String>(String.valueOf(numberOfMales), HttpStatus.OK);
		} catch (ServiceException ex) {
			logger.debug("error", ex);
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/populate")
	public ResponseEntity<String> populate(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		try {
			int importedItems = importAddressBookService.importItems(file.getInputStream());
			return new ResponseEntity<String>(String.valueOf(importedItems), HttpStatus.OK);
		} catch (ServiceException ex) {
			logger.debug("error", ex);
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException ex) {
			logger.debug("error", ex);
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value="/name-of-oldest-person")
	public ResponseEntity<String> getNameOfOldestPerson() {
		try {
			String nameOfOldestPerson = addressBookItemService.getNameOfOldestPerson();
			return new ResponseEntity<String>(nameOfOldestPerson, HttpStatus.OK);
		} catch (ServiceException ex) {
			logger.debug("error", ex);
			return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
