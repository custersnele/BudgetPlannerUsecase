package be.pxl.student;

import be.pxl.student.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Scanner;

public class BudgetPlannerFindPayments {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = null;
		EntityManager entityManager = null;
		try {
			Scanner input = new Scanner(System.in);
			entityManagerFactory = Persistence.createEntityManagerFactory("musicdb_pu");
			entityManager = entityManagerFactory.createEntityManager();
			System.out.println("Geef een naam: ");
			String name = input.nextLine();
			TypedQuery<Account> findByName = entityManager.createNamedQuery("findByName", Account.class);
			findByName.setParameter("name", name);
			Account result = findByName.getSingleResult();
			System.out.println(result.getIBAN() + " by " + result.getName());
			System.out.println("#payments: " + result.getPayments().size());
			result.getPayments().forEach(System.out::println);
		}
		finally {
			if (entityManager != null) {
				entityManager.close();
			}
			if (entityManagerFactory != null) {
				entityManagerFactory.close();
			}
		}
	}

}
