package be.pxl.student.service;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.impl.LabelDaoImpl;
import be.pxl.student.entity.Label;
import be.pxl.student.util.DuplicateLabelException;
import be.pxl.student.util.EntityManagerUtil;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class LabelService {

	private LabelDao labelDao;

	public LabelService() {
		labelDao = new LabelDaoImpl(EntityManagerUtil.createEntityManager());
	}

	public List<Label> findAllLabels() {
		return labelDao.findAllLabels();
	}

	public void addLabel(String name) throws DuplicateLabelException {
		Label existingLabel = labelDao.findLabelByName(name);
		if (existingLabel != null) {
			throw new DuplicateLabelException("There already exists a label with name [" + name + "]");
		}
		labelDao.saveLabel(new Label(name));
	}

	public void removeLabel(long labelId) {
		Label labelById = labelDao.findLabelById(labelId);
		if (labelById != null) {
			labelDao.removeLabel(labelById);
		}

	}
}
