package be.pxl.student.rest.resources;

import org.apache.johnzon.mapper.JohnzonConverter;

import java.time.LocalDate;

public class PaymentCreateResource {
	private String counterAccount;
	private float amount;
	@JohnzonConverter(LocalDateConverter.class) //https://johnzon.apache.org/
	private LocalDate date;
	private String detail;

	public String getCounterAccount() {
		return counterAccount;
	}

	public void setCounterAccount(String counterAccount) {
		this.counterAccount = counterAccount;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}


	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
