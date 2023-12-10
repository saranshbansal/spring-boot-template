package com.my.template.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;

import static java.util.Arrays.asList;
import static java.util.TimeZone.getTimeZone;
import static org.testng.AssertJUnit.*;

/**
 * Unit test for {@link DateTimeUtil}
 *
 * Created by Saransh Bansal on 20/07/2023
 */
public class DateTimeUtilTest {

	@Test(dataProvider = "dateTimeDataProvider")
	public void shouldReturnCorrectCurrentDate(String timeZone, LocalDateTime expectedDate) {
		// when
		DateTimeUtil.setTimeZone(timeZone);
		LocalDate result = DateTimeUtil.currentDate();
		// then
		assertEquals(expectedDate.toLocalDate(), result);
	}

	@Test(dataProvider = "dateValidationDataProvider")
	public void shouldParseDateCorrectly(String dateToCheck, boolean shouldBeParsed) {
		// when
		LocalDate result = DateTimeUtil.parseDate(dateToCheck);
		// then
		if (shouldBeParsed) {
			assertNotNull(result);
		} else {
			assertNull(result);
		}
	}

	@DataProvider
	public Object[][] dateValidationDataProvider() {
		return new Object[][]{
				{"2018", false},
				{"2018 ", false},
				{"2018-", false},
				{"2018-05", false},
				{"2018-05-", false},
				{"2018-05-01", true},
				{"01/05/2016", true},
				{"01-05-2016", false},
				{"01/05", false},
				{"01/2018", false},
				{"", false},
		};
	}

	@DataProvider
	private Iterator<Object[]> dateTimeDataProvider() {
		return asList(
				createTimeDateDataProviderRecord("Pacific/Honolulu", -10),
				createTimeDateDataProviderRecord("Cuba", -5),
				createTimeDateDataProviderRecord("Europe/London", 0),
				createTimeDateDataProviderRecord("Europe/Zaporozhye", 2),
				createTimeDateDataProviderRecord("Indian/Christmas", 7)
		).iterator();
	}

	private Object[] createTimeDateDataProviderRecord(String zone, int offsetInHrs) {
		int dtOffset = getTimeZone(zone).inDaylightTime(new Date()) ? 1 : 0;
		return new Object[]{zone, LocalDateTime.now(ZoneId.of("GMT")).plusHours(offsetInHrs + dtOffset).withNano(0)};
	}
}