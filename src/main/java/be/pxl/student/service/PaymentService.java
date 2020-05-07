package be.pxl.student.service;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.dao.impl.LabelDaoImpl;
import be.pxl.student.dao.impl.PaymentDaoImpl;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.EntityManagerUtil;
import be.pxl.student.util.exception.LabelNotFoundException;
import be.pxl.student.util.exception.PaymentNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class PaymentService {
	private PaymentDao paymentDao;
	private LabelDao labelDao;

	public PaymentService() {
		EntityManager entityManager = EntityManagerUtil.createEntityManager();
		paymentDao = new PaymentDaoImpl(entityManager);
		labelDao = new LabelDaoImpl(entityManager);
	}

	public void linkPayment(long paymentId, long labelId) throws LabelNotFoundException, PaymentNotFoundException {
		Label label = labelDao.findLabelById(labelId);
		if (label == null) {
			throw new LabelNotFoundException("Label with id [" + labelId + "] does not exist.");
		}
		Payment payment = paymentDao.findById(paymentId);
		if (payment == null) {
			throw new PaymentNotFoundException("Payment with id [" + paymentId + "] not found.");
		}
		payment.setLabel(label);
		paymentDao.updatePayment(payment);
	}

	public void removePayment(long paymentId) {
		Payment payment = paymentDao.findById(paymentId);
		if (payment != null) {
			payment.delete();
			paymentDao.removePayment(payment);
		}
	}
}
