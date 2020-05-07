package be.pxl.student.rest.resources;

import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PaymentsSearchResource {
	@QueryParam("label")
	private String label;

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
		return allPredicates.stream().reduce(p -> true, Predicate::and);
	}
}
