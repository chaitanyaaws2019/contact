package com.home.addressbook.web.rest;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

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

	@Test
	public void testPopulate() throws Exception {
		when(importAddressBookService.importItems(any(InputStream.class))).thenReturn(11);
		//
		MockMultipartFile file = new MockMultipartFile("file", "test-content".getBytes());
		ResponseEntity<String> response = controller.populate(file);
		//
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("11", response.getBody());
		ArgumentCaptor<InputStream> inputArg = ArgumentCaptor.forClass(InputStream.class);
		verify(importAddressBookService, times(1)).importItems(inputArg.capture());
		assertNotNull(inputArg.getValue());
		StringWriter sw = new StringWriter();
		IOUtils.copy(inputArg.getValue(), sw);
		assertEquals("test-content", sw.toString());
	}

	@Test
	public void testPopulateEmpty() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "".getBytes());
		ResponseEntity<String> response = controller.populate(file);
		//
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testGetOldestPerson() throws Exception {
		when(addressBookItemService.getNameOfOldestPerson()).thenReturn("Sarah Stone");
		//
		ResponseEntity<String> response = controller.getNameOfOldestPerson();
		//
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Sarah Stone", response.getBody());
		verify(addressBookItemService, times(1)).getNameOfOldestPerson();
	}

	@Test
	public void testGetOldestPersonError500() throws Exception {
		when(addressBookItemService.getNameOfOldestPerson()).thenThrow(new ServiceException());
		//
		ResponseEntity<String> response = controller.getNameOfOldestPerson();
		//
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		verify(addressBookItemService, times(1)).getNameOfOldestPerson();
	}

}
