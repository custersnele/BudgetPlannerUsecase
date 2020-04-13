package be.pxl.student.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(
		{ @NamedQuery(name = "findLabelByName", query = "SELECT l FROM Label l WHERE l.name = :name"), @NamedQuery(name = "findAllLabels", query = "SELECT l FROM Label l") }

)
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

	public Label() {
		// JPA onlye
	}

	public Label(String name) {
		setName(name);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
