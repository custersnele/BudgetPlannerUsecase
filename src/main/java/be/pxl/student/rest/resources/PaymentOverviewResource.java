package be.pxl.student.rest.resources;

import java.util.List;

public class PaymentOverviewResource {
	private double receivingAmount;
	private double spendingAmount;
	private double resultAmount;
	private List<PaymentResource> payments;

	public double getReceivingAmount() {
		return receivingAmount;
	}

	public void setReceivingAmount(double receivingAmount) {
		this.receivingAmount = receivingAmount;
	}

	public double getSpendingAmount() {
		return spendingAmount;
	}

	public void setSpendingAmount(double spendingAmount) {
		this.spendingAmount = spendingAmount;
	}

	public double getResultAmount() {
		return resultAmount;
	}

	public void setResultAmount(double resultAmount) {
		this.resultAmount = resultAmount;
	}

	public List<PaymentResource> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentResource> payments) {
		this.payments = payments;
	}
}
