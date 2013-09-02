package com.home.addressbook.web.rest;

import javax.inject.Inject;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.domain.Gender;
import com.home.addressbook.service.AddressBookItemService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@ContextConfiguration(locations={
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
	})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true)
public class AddressBookRestControllerIntegrationTest {
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yy");

	@Inject
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	@Inject
	private AddressBookItemService addressBookItemService;

	private void setupTestData() throws Exception {
		AddressBookItem item;
		//
		item = new AddressBookItem();
		item.setName("Bill McKnight"); item.setGender(Gender.MALE); item.setDateOfBirth(dateTimeFormatter.parseDateTime("16/03/77"));
		addressBookItemService.persist(item);
		//
		item = new AddressBookItem();
		item.setName("Paul Robinson"); item.setGender(Gender.MALE); item.setDateOfBirth(dateTimeFormatter.parseDateTime("15/01/85"));
		addressBookItemService.persist(item);
		//
		item = new AddressBookItem();
		item.setName("Gemma Lane"); item.setGender(Gender.FEMALE); item.setDateOfBirth(dateTimeFormatter.parseDateTime("20/11/91"));
		addressBookItemService.persist(item);
		//
		item = new AddressBookItem();
		item.setName("Sarah Stone"); item.setGender(Gender.FEMALE); item.setDateOfBirth(dateTimeFormatter.parseDateTime("20/09/80"));
		addressBookItemService.persist(item);
		//
		item = new AddressBookItem();
		item.setName("Wes Jackson"); item.setGender(Gender.MALE); item.setDateOfBirth(dateTimeFormatter.parseDateTime("14/08/74"));
		addressBookItemService.persist(item);
	}
	
	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		setupTestData();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetNumberOfMales() throws Exception {
		mockMvc.perform(get("/rest/api/addressbook/number-of-males").accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
			.andExpect(content().string("3"))
			;
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testPopulate() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("a b, Male, 11/02/71\n");
		sb.append("c d, Female, 12/03/85\n");
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, sb.toString().getBytes());
		mockMvc.perform(fileUpload("/rest/api/addressbook/populate").file(file))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
			.andExpect(content().string("2"))
			;
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetOldestPerson() throws Exception {
		mockMvc.perform(get("/rest/api/addressbook/name-of-oldest-person"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
			.andExpect(content().string("Wes Jackson"))
			;
	}

}
