package com.home.addressbook.service.impl;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.home.addressbook.dao.AddressBookItemDAO;
import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.domain.Gender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AddressBookItemServiceImplTest {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yy");

	@Mock
	private AddressBookItemDAO addressBookItemDAO;
	
	@InjectMocks
	private AddressBookItemServiceImpl service;
	
	@Before
	public void setup() {
		service = new AddressBookItemServiceImpl();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testPersist() throws Exception {
		// 
		AddressBookItem item = new AddressBookItem();
		item.setName("Bill McKnight");
		item.setGender(Gender.MALE);
		item.setDateOfBirth(dateTimeFormatter.parseDateTime("16/03/77"));
		// 
		service.persist(item);
		// 
		ArgumentCaptor<AddressBookItem> itemArg = ArgumentCaptor.forClass(AddressBookItem.class);
		verify(addressBookItemDAO, times(1)).persist(itemArg.capture());
		assertNotNull(itemArg.getValue());
		assertEquals("Bill McKnight", itemArg.getValue().getName());
		assertEquals(Gender.MALE, itemArg.getValue().getGender());
		assertEquals("16/03/77", dateTimeFormatter.print(itemArg.getValue().getDateOfBirth()));
	}
	
	@Test
	public void testGetNumberOfMales() throws Exception {
		// 
		when(addressBookItemDAO.getNumberOfItemsByGender(Gender.MALE)).thenReturn(99);
		// 
		int numberOfMales = service.getNumberOfMales();
		//
		verify(addressBookItemDAO, times(1)).getNumberOfItemsByGender(Gender.MALE);
		assertEquals(99, numberOfMales);
	}
	
	@Test
	public void testGetNameOfOldestPerson() throws Exception {
		// 
		AddressBookItem item = new AddressBookItem();
		item.setName("Paul Robinson");
		item.setGender(Gender.MALE);
		item.setDateOfBirth(dateTimeFormatter.parseDateTime("15/01/85"));
		// 
		when(addressBookItemDAO.findItemForOldestPerson()).thenReturn(item);
		// 
		String nameOfOldestPerson = service.getNameOfOldestPerson();
		//
		verify(addressBookItemDAO, times(1)).findItemForOldestPerson();
		assertEquals("Paul Robinson", nameOfOldestPerson);
	}
	
	@Test
	public void testGetNameOfOldestPersonShouldReturnNull() throws Exception {
		// 
		when(addressBookItemDAO.findItemForOldestPerson()).thenReturn(null);
		// 
		String nameOfOldestPerson = service.getNameOfOldestPerson();
		//
		verify(addressBookItemDAO, times(1)).findItemForOldestPerson();
		assertNull(nameOfOldestPerson);
	}
	
}
