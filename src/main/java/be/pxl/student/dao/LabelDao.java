package be.pxl.student.dao;

import be.pxl.student.entity.Label;

import java.util.List;

public interface LabelDao {

	Label findLabelByName(String name);

	Label findLabelById(long id);

	List<Label> findAllLabels();

	void saveLabel(Label label);

	void removeLabel(Label label);
}
