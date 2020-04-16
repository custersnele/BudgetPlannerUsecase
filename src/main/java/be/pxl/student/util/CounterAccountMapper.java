package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.util.exception.InvalidPaymentException;

public class CounterAccountMapper {

	public Account map(String validLine) throws InvalidPaymentException {
		String[] split = validLine.split(",");
		if (split.length != 7) {
			throw new InvalidPaymentException("Invalid number of fields in line.");
		}
		Account account = new Account();
		// account.setName(split[0]); counteraccount is unknown
		account.setIBAN(split[2]);
		return account;
	}
}
