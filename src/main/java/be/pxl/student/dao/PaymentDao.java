package be.pxl.student.dao;

import be.pxl.student.entity.Payment;

public interface PaymentDao {

	Payment findById(long paymentId);
	void updatePayment(Payment payment);
	void removePayment(Payment payment);
	Long countPaymentsByLabel(long labelId);
}
