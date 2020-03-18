package be.pxl.student.util;

import be.pxl.student.entity.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PaymentMapper {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

	public Payment map(String validLine) throws InvalidPaymentException {
		String[] split = validLine.split(",");
		if (split.length != 7) {
			throw new InvalidPaymentException("Invalid number of fields in line.");
		}
		return new Payment(LocalDateTime.parse(split[3], FORMATTER), Float.parseFloat(split[4]), split[5], split[6]);
	}
}
