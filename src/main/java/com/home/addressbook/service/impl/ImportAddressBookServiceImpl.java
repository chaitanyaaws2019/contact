package com.home.addressbook.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

import com.home.addressbook.dao.AddressBookItemDAO;
import com.home.addressbook.dao.DaoException;
import com.home.addressbook.domain.AddressBookItem;
import com.home.addressbook.domain.Gender;
import com.home.addressbook.service.CsvBadFormatException;
import com.home.addressbook.service.ImportAddressBookService;
import com.home.addressbook.service.ServiceException;

import static com.google.common.base.Preconditions.*;

public class ImportAddressBookServiceImpl implements ImportAddressBookService {
	
	private static final Logger logger = LoggerFactory.getLogger(ImportAddressBookServiceImpl.class);
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yy");

	@Inject
	private AddressBookItemDAO addressBookItemDAO;
	
	
	@Override
	public int importItems(InputStream input) throws ServiceException {
		checkNotNull(input);
		Reader reader = new InputStreamReader(input);
		ICsvListReader csvReader = new CsvListReader(reader, CsvPreference.STANDARD_PREFERENCE);
		int cnt = 0;
		try {
			List<String> line;
			while ((line = csvReader.read()) != null) {
				AddressBookItem item = new AddressBookItem();
				if (line.size() < 3) {
					throw new CsvBadFormatException();
				}
				item.setName(StringUtils.trim(line.get(0)));
				try {
					item.setGender(Gender.valueOf(StringUtils.upperCase(StringUtils.trim(line.get(1)))));
				} catch (IllegalArgumentException ex) {
					throw new CsvBadFormatException();
				}
				try {
					DateTime dateOfBirth = dateTimeFormatter.parseDateTime(StringUtils.trim(line.get(2)));
					item.setDateOfBirth(dateOfBirth);
				} catch (IllegalArgumentException ex) {
					throw new CsvBadFormatException();
				}
				addressBookItemDAO.persist(item);
				cnt++;
			}
		} catch (DaoException ex) {
			logger.error("dao-error", ex);
			throw new ServiceException(ex);
		} catch (IOException ex) {
			logger.error("error in reading from csv input stream", ex);
			throw new ServiceException(ex);
		}
		return cnt;
	}

}
