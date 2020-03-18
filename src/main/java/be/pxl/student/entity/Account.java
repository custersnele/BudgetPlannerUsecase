package be.pxl.student.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NamedQueries(@NamedQuery(name="findByName", query="SELECT a FROM Account a WHERE a.name = :name"))
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String IBAN;
    private String name;
    @OneToMany(mappedBy = "account")
    private List<Payment> payments;

    public Long getId() {
        return id;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
