package com.home.addressbook.service.impl;

import java.io.ByteArrayInputStream;

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
import com.home.addressbook.service.CsvBadFormatException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ImportAddressBookServiceImplTest {
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yy");

	@Mock
	private AddressBookItemDAO addressBookItemDAO;
	
	@InjectMocks
	private ImportAddressBookServiceImpl service;
	
	@Before
	public void setup() {
		service = new ImportAddressBookServiceImpl();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testImportItems() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("Bill McKnight, Male, 16/03/77").append("\n");
		sb.append("Gemma Lane, Female, 20/11/91").append("\n");
		//
		int numberOfImportedItems = service.importItems(new ByteArrayInputStream(sb.toString().getBytes()));
		//
		ArgumentCaptor<AddressBookItem> itemArg = ArgumentCaptor.forClass(AddressBookItem.class); 
		verify(addressBookItemDAO, times(2)).persist(itemArg.capture());
		//
		assertEquals(2, numberOfImportedItems);
		//
		assertNotNull(itemArg.getAllValues().get(0));
		assertEquals("Bill McKnight", itemArg.getAllValues().get(0).getName());
		assertEquals(Gender.MALE, itemArg.getAllValues().get(0).getGender());
		assertEquals("16/03/77", dateTimeFormatter.print(itemArg.getAllValues().get(0).getDateOfBirth()));
		//
		assertNotNull(itemArg.getAllValues().get(1));
		assertEquals("Gemma Lane", itemArg.getAllValues().get(1).getName());
		assertEquals(Gender.FEMALE, itemArg.getAllValues().get(1).getGender());
		assertEquals("20/11/91", dateTimeFormatter.print(itemArg.getAllValues().get(1).getDateOfBirth()));
	}

	@Test(expected=CsvBadFormatException.class)
	public void testImportItemsBadFormatException() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("Bill McKnight, Male").append("\n");
		//
		service.importItems(new ByteArrayInputStream(sb.toString().getBytes()));
	}

}
