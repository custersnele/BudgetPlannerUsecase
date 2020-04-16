package be.pxl.student.dao.impl;

import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class PaymentDaoImpl implements PaymentDao {

	private EntityManager entityManager;

	public PaymentDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Payment findById(long paymentId) {
		return entityManager.find(Payment.class, paymentId);
	}

	@Override
	public void updatePayment(Payment payment) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(payment);
		transaction.commit();
	}
}
