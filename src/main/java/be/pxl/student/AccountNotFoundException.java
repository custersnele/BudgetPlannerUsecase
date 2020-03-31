package be.pxl.student;

public class AccountNotFoundException  extends Exception {

	public AccountNotFoundException(String nameOrIban) {
		super("Account  [" + nameOrIban + "] does not exist.");
	}
}
