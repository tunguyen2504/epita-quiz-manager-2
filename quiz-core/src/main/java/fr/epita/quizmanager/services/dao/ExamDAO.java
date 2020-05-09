package fr.epita.quizmanager.services.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.Question;

/**
 * @author Anh Tu
 *
 */
public class ExamDAO extends GenericDAO<Exam, Long> {

	@Override
	public String getQuery() {
		return "from Exam where title = :pTitle";
	}

	@Override
	public void setParameters(Map<String, Object> parameters, Exam exam) {
		parameters.put("pTitle", exam.getTitle());
	}
	
	@Override
	public Class<Exam> getEntityClass() {
		return Exam.class;
	}
	
}
