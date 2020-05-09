package fr.epita.quizmanager.services.dao;

import java.util.Map;

import fr.epita.quizmanager.datamodel.Question;

/**
 * @author Anh Tu
 *
 */
public class QuestionDAO extends GenericDAO<Question, Long> {

	@Override
	public String getQuery() {
		return "from Question where title = :pTitle";
	}

	public void setParameters(Map<String, Object> parameters, Question question) {
		parameters.put("pTitle", question.getTitle());
	}

	@Override
	public Class<Question> getEntityClass() {
		return Question.class;
	}

}
