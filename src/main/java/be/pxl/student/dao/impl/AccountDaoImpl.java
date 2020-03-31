package be.pxl.student.dao.impl;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class AccountDaoImpl implements AccountDao {
	private static final Logger LOGGER = LogManager.getLogger(AccountDaoImpl.class);

	private EntityManager entityManager;

	public AccountDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Account findAccountByName(String name) {
		TypedQuery<Account> query = entityManager.createNamedQuery("findByName", Account.class);
		LOGGER.info("query with name [" + name + "]");
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Account findAccountByIBAN(String iban) {
		TypedQuery<Account> query = entityManager.createNamedQuery("findByIban", Account.class);
		LOGGER.info("query with iban [" + iban + "]");
		query.setParameter("iban", iban);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Account updateAccount(Account account) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		account = entityManager.merge(account);
		transaction.commit();
		return account;
	}

	public Account createAccount(Account account) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(account);
		transaction.commit();
		return account;
	}
}
