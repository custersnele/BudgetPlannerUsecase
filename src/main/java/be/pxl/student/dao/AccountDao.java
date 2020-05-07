package be.pxl.student.dao;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;

import java.util.List;

public interface AccountDao {

	Account findAccountByName(String name);
	Account findAccountByIBAN(String iban);
	Account findAccountById(long id);
	Account updateAccount(Account account);
	Account createAccount(Account account);
	List<Account> findAllAccounts();
	void removeAccount(Account account);


}
