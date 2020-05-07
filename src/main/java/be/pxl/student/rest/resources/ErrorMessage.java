package be.pxl.student.rest.resources;

public class ErrorMessage {
	private String message;

	public ErrorMessage() {
	}

	public ErrorMessage(Exception e) {
		message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
