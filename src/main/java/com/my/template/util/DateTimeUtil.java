package com.my.template.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Utility for date and time related operations
 * PCF servers use different timezone configuration than local development environments
 * It's recommended to use this utility for currentTime() and currentDate() to ensure
 * the same time is used on all environments
 *
 * Created by Saransh Bansal on 18/07/2023.
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public final class DateTimeUtil {

	public static final String DEFAULT_TIME_ZONE = "Europe/London";
	private static String timeZone = DEFAULT_TIME_ZONE;
	public static final String CUSTOM_DATE_FORMAT = "dd/MM/yyyy";

	/**
	 * Returns current datetime using defined timeZone
	 *
	 * @return {@link LocalDateTime} instance
	 */
	public static LocalDateTime currentTime() {
		return LocalDateTime.now(ZoneId.of(timeZone));
	}

	/**
	 * Returns current date using defined timeZone
	 *
	 * @return {@link LocalDate} instance
	 */
	public static LocalDate currentDate() {
		return LocalDate.now(ZoneId.of(timeZone));
	}

	/**
	 * Sets custom timeZone
	 *
	 * @param timeZone timezone value
	 */
	public static void setTimeZone(String timeZone) {
		DateTimeUtil.timeZone = timeZone;
	}

	/**
	 * Parses string date into {@link LocalDate} using supported formats: ISO8601 and CUSTOM_DATE_FORMAT
	 *
	 * @param dateStr string to parse
	 * @return LocalDate or null if string cannot be parsed with any supported format
	 */
	public static LocalDate parseDate(String dateStr) {
		if (isBlank(dateStr)) {
			return null;
		}

		LocalDate parsedDate = parseAsCustomFormatDate(dateStr);
		return parsedDate == null ? parseAsISO8601Date(dateStr) : parsedDate;
	}

	/**
	 * Tries to parse provided string into date using ISO8601 format
	 *
	 * @param dateStr string to parse
	 * @return LocalDate or null if cannot be parsed
	 */
	private static LocalDate parseAsISO8601Date(String dateStr) {
		try {
			return parse(dateStr);
		} catch (DateTimeParseException e) {
			log.warn("Date [{}] cannot be parsed using ISO8601 format", dateStr, e);
			return null;
		}
	}

	/**
	 * Tries to parse provided string into date using CUSTOM_DATE_FORMAT format
	 *
	 * @param dateStr string to parse
	 * @return LocalDate or null if cannot be parsed
	 */
	private static LocalDate parseAsCustomFormatDate(String dateStr) {
		try {
			return parse(dateStr, ofPattern(CUSTOM_DATE_FORMAT));
		} catch (DateTimeParseException e) {
			log.warn("Date [{}] cannot be parsed using [{}] format", dateStr, CUSTOM_DATE_FORMAT, e);
			return null;
		}
	}
}
