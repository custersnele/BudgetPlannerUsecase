package be.pxl.student.dao.impl;

import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

	@Override
	public void removePayment(Payment payment) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(payment);
		transaction.commit();
	}

	@Override
	public Long countPaymentsByLabel(long labelId) {
		TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(*) FROM Payment p WHERE p.label.id = :labelId", Long.class);
		query.setParameter("labelId", labelId);
		return query.getSingleResult();
	}
}
