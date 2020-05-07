package be.pxl.student.rest.resources;

import org.apache.johnzon.mapper.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements Converter<LocalDate> {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu");

	@Override
	public String toString(final LocalDate instance) {
		return instance.toString();
	}

	@Override
	public LocalDate fromString(final String text) {
		return LocalDate.parse(text, FORMATTER);
	}
}
