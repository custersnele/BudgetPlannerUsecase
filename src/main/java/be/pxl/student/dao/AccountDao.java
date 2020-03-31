package be.pxl.student.dao;

import be.pxl.student.entity.Account;

public interface AccountDao {

	Account findAccountByName(String name);
	Account findAccountByIBAN(String iban);
	Account updateAccount(Account account);
	Account createAccount(Account account);

}
