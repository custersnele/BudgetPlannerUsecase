package be.pxl.student.service;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Label;
import be.pxl.student.util.exception.LabelInUseException;
import be.pxl.student.util.exception.LabelNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LabelServiceRemoveLabelTest {
	private static final long LABEL_ID = 12l;

	@Mock
	private LabelDao labelDao;
	@Mock
	private PaymentDao paymentDao;
	@InjectMocks
	private LabelService labelService;
	private Label label;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		label = new Label();
		label.setId(LABEL_ID);
		label.setName("Clothes");
	}

	@Test
	public void anExceptionIsThrownWhenLabelIsUsed() throws LabelNotFoundException, LabelInUseException {
		when(labelDao.findLabelById(LABEL_ID)).thenReturn(label);
		when(paymentDao.countPaymentsByLabel(LABEL_ID)).thenReturn(5l);

		assertThrows(LabelInUseException.class, () -> labelService.removeLabel(LABEL_ID));

		verify(labelDao, never()).removeLabel(label);
	}

	@Test
	public void unusedLabelIsRemoved() throws LabelNotFoundException, LabelInUseException {
		when(labelDao.findLabelById(LABEL_ID)).thenReturn(label);
		when(paymentDao.countPaymentsByLabel(LABEL_ID)).thenReturn(0l);

		labelService.removeLabel(LABEL_ID);

		verify(labelDao).removeLabel(label);
	}

}
