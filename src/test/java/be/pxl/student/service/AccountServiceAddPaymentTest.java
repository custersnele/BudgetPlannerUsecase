package be.pxl.student.service;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;
import be.pxl.student.util.exception.AccountNotFoundException;
import be.pxl.student.util.exception.LabelInUseException;
import be.pxl.student.util.exception.LabelNotFoundException;
import com.sun.mail.iap.Argument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceAddPaymentTest {

	private static final String ACCOUNT_NAME = "Rookie";
	private static final String COUNTER_ACCOUNT_IBAN = "BE8888888";
	@Mock
	private AccountDao accountDao;
	@InjectMocks
	private AccountService accountService;
	private Account account;
	@Captor
	private ArgumentCaptor<Account> accountCaptor;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		account = new Account();
		account.setIBAN("BE1234567");
		account.setName(ACCOUNT_NAME);
	}

	@Test
	public void counterAccountIsCreatedIfNotExists() throws AccountNotFoundException, LabelInUseException {
		when(accountDao.findAccountByName(ACCOUNT_NAME)).thenReturn(account);

		accountService.addPayment(ACCOUNT_NAME, COUNTER_ACCOUNT_IBAN, -85.75f, "sportschoenen", LocalDate.now());

		verify(accountDao).createAccount(accountCaptor.capture());
		Account createdCounterAccount = accountCaptor.getValue();
		assertNotNull(createdCounterAccount);
		assertEquals(COUNTER_ACCOUNT_IBAN, createdCounterAccount.getIBAN());
	}

}
