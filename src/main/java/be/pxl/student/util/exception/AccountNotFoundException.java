package be.pxl.student.util.exception;

public class AccountNotFoundException  extends Exception {

	public AccountNotFoundException(String nameOrIban) {
		super("Account  [" + nameOrIban + "] does not exist.");
	}
}
