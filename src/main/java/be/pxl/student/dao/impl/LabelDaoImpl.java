package be.pxl.student.dao.impl;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.entity.Label;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class LabelDaoImpl implements LabelDao {

	private EntityManager entityManager;

	public LabelDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Label findLabelByName(String name) {
		TypedQuery<Label> query = entityManager.createNamedQuery("findLabelByName", Label.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Label findLabelById(long id) {
		return entityManager.find(Label.class, id);
	}

	public List<Label> findAllLabels() {
		TypedQuery<Label> query = entityManager.createNamedQuery("findAllLabels", Label.class);
		return query.getResultList();
	}

	@Override
	public void saveLabel(Label label) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(label);
		transaction.commit();
	}

	public void removeLabel(Label label) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(label);
		transaction.commit();

	}


}
