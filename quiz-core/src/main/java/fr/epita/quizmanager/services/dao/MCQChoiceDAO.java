package fr.epita.quizmanager.services.dao;

import java.util.Map;

import fr.epita.quizmanager.datamodel.MCQChoice;

public class MCQChoiceDAO extends GenericDAO<MCQChoice, Long> {

	@Override
	public String getQuery() {
		return "from MCQChoice where content = :pContent";
	}

	public void setParameters(Map<String, Object> parameters, MCQChoice mcqChoice) {
		parameters.put("pContent", mcqChoice.getContent());
	}

	@Override
	public Class<MCQChoice> getEntityClass() {
		return MCQChoice.class;
	}
	
}
