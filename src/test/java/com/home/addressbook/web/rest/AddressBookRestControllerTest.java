package com.home.addressbook.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.home.addressbook.service.AddressBookItemService;
import com.home.addressbook.service.ImportAddressBookService;
import com.home.addressbook.service.ServiceException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AddressBookRestControllerTest {

	@Mock
	private AddressBookItemService addressBookItemService;
	
	@Mock
	private ImportAddressBookService importAddressBookService;

	@InjectMocks
	AddressBookRestController controller;
	
	@Before
	public void setup() {
		controller = new AddressBookRestController();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetNumberOfMales() throws Exception {
		when(addressBookItemService.getNumberOfMales()).thenReturn(77);
		//
		ResponseEntity<String> response = controller.getNumberOfMales();
		//
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("77", response.getBody());
		verify(addressBookItemService, times(1)).getNumberOfMales();
	}

	@Test
	public void testGetNumberOfMalesError500() throws Exception {
		when(addressBookItemService.getNumberOfMales()).thenThrow(new ServiceException());
		//
		ResponseEntity<String> response = controller.getNumberOfMales();
		//
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		verify(addressBookItemService, times(1)).getNumberOfMales();
	}
}
