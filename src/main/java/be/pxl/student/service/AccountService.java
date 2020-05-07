package be.pxl.student.service;

import be.pxl.student.entity.Label;
import be.pxl.student.util.exception.AccountNotFoundException;
import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.impl.AccountDaoImpl;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.EntityManagerUtil;
import be.pxl.student.util.exception.DuplicateAccountException;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class AccountService {

	private AccountDao accountDao;

	public AccountService() {
		accountDao = new AccountDaoImpl(EntityManagerUtil.createEntityManager());
	}

	public List<Payment> findPaymentsByAccountName(String name) throws AccountNotFoundException {
		Account account = accountDao.findAccountByName(name);
		if (account == null) {
			throw new AccountNotFoundException(name);
		}
		return account.getPayments();
	}

	public void addPayment(String name, String counterAccountIBAN, float amount, String detail, LocalDate date) throws AccountNotFoundException {
		Account account = accountDao.findAccountByName(name);
		if (account == null) {
			throw new AccountNotFoundException(name);
		}
		Account counterAccount = accountDao.findAccountByIBAN(counterAccountIBAN);
		if (counterAccount == null) {
			counterAccount = new Account();
			counterAccount.setIBAN(counterAccountIBAN);
			counterAccount = accountDao.createAccount(counterAccount);
		}
		Payment payment = new Payment();
		payment.setCounterAccount(counterAccount);
		payment.setAmount(amount);
		payment.setCurrency("EUR");
		payment.setDate(date.atStartOfDay());
		payment.setDetail(detail);
		account.addPayment(payment);
		accountDao.updateAccount(account);
	}

	public List<Account> findAllAccounts() {
		return accountDao.findAllAccounts();
	}

	public void removeAccount(long accountId) {
		Account account = accountDao.findAccountById(accountId);
		if (account != null) {
			accountDao.removeAccount(account);
		}
	}

	public void createOrUpdateAccount(String iban, String name) throws DuplicateAccountException {
		Account existingAccount = accountDao.findAccountByName(name);
		if (existingAccount != null) {
			throw new DuplicateAccountException("There already exists an account with name [" + name + "]");
		}
		existingAccount = accountDao.findAccountByIBAN(iban);
		if (existingAccount != null) {
			if (StringUtils.isNotBlank(existingAccount.getName())) {
				throw new DuplicateAccountException("There already exists an account with iban [" + iban + "]");
			} else {
				existingAccount.setName(name);
				accountDao.updateAccount(existingAccount);
			}
		} else {
			Account newAccount = new Account();
			newAccount.setName(name);
			newAccount.setIBAN(iban);
			accountDao.createAccount(newAccount);
		}
	}
}
