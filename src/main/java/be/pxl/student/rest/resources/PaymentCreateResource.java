package be.pxl.student.rest.resources;

public class PaymentCreateResource {
	private String counterAccount;
	private float amount;
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
}
