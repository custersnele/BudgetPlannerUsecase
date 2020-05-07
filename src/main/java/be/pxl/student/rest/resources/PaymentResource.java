package be.pxl.student.rest.resources;

import java.time.LocalDate;

public class PaymentResource {
	private Long id;
	private String counterAccount;
	private LocalDate date;
	private float amount;
	private LabelResource label;
	private String currency;
	private String detail;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCounterAccount() {
		return counterAccount;
	}

	public void setCounterAccount(String counterAccount) {
		this.counterAccount = counterAccount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public LabelResource getLabel() {
		return label;
	}

	public void setLabel(LabelResource label) {
		this.label = label;
	}
}
