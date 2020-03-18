package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.HashMap;
import java.util.Map;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
	private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
	private PathMatcher csvMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv");
	private AccountMapper accountMapper = new AccountMapper();
	private CounterAccountMapper counterAccountMapper = new CounterAccountMapper();
	private PaymentMapper paymentMapper = new PaymentMapper();
	private Map<String, Account> createdAccounts = new HashMap<>();
	private EntityManager entityManager;

	public BudgetPlannerImporter(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void importCsv(Path path) {
		if (!csvMatcher.matches(path)) {
			LOGGER.debug("Invalid file: .csv expected. Provided: {}", path);
			return;
		}
		if (!Files.exists(path)) {
			LOGGER.error("File {} does not exist.", path);
			return;
		}

		try (BufferedReader reader = Files.newBufferedReader(path)) { // try-with-resources
			EntityTransaction tx = entityManager.getTransaction();
			tx.begin();
			String line = null;
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				try {
					Payment payment = paymentMapper.map(line);
					payment.setAccount(getOrCreateAccount(accountMapper.map(line)));
					payment.setCounterAccount(getOrCreateAccount(counterAccountMapper.map(line)));
					entityManager.persist(payment);
				} catch (InvalidPaymentException e) {
					LOGGER.error("Error while mapping line: {}", e.getMessage());
				}
			}
			tx.commit();
		} catch (IOException e) {
			LOGGER.fatal("An error occurred while reading file: {}", path);
		}

	}

	private Account getOrCreateAccount(Account account) {
		if (createdAccounts.containsKey(account.getIBAN())) {
			return createdAccounts.get(account.getIBAN());
		}
		entityManager.persist(account);
		createdAccounts.put(account.getIBAN(), account);
		return account;
	}
}
