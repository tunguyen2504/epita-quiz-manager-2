package fr.epita.quizmanager.services.dao;

import java.util.Map;

import fr.epita.quizmanager.datamodel.MCQAnswer;

/**
 * @author Anh Tu
 *
 */
public class MCQAnswerDAO extends GenericDAO<MCQAnswer, Long>{

	@Override
	public String getQuery() {
		return "from MCQAnswer where content = :pContent";
	}

	@Override
	public void setParameters(Map<String, Object> parameters, MCQAnswer mcqAnswer) {
		parameters.put("pContent", mcqAnswer.getContent());
	}
	
	@Override
	public Class<MCQAnswer> getEntityClass() {
		return MCQAnswer.class;
	}
	
}
