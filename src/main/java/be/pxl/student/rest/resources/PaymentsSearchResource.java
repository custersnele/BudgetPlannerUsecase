package be.pxl.student.rest.resources;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.QueryParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PaymentsSearchResource {
	@QueryParam("dateFrom")
	private LocalDate dateFrom;
	@QueryParam("dateTo")
	private LocalDate dateTo;
	@QueryParam("label")
	private String label;

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Predicate<Payment> getFilter() {
		List<Predicate<Payment>> allPredicates = new ArrayList<>();
		if (StringUtils.isNotBlank(label)) {
			allPredicates.add(p -> p.hasLabel(new Label(label)));
		}
		if (dateFrom != null) {
			allPredicates.add(p -> p.getDate().toLocalDate().equals(dateFrom) || p.getDate().toLocalDate().isAfter(dateFrom));
		}
		if (dateTo != null) {
			allPredicates.add(p -> p.getDate().toLocalDate().equals(dateTo) || p.getDate().toLocalDate().isBefore(dateTo));
		}

		return allPredicates.stream().reduce(p -> true, Predicate::and);
	}
}
