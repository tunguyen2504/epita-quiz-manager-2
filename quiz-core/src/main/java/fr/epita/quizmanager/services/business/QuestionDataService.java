package fr.epita.quizmanager.services.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.dao.MCQChoiceDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;

/**
 * @author Anh Tu
 *
 */
public class QuestionDataService {

	@Inject
	QuestionDAO questionDAO;
	
	@Inject
	MCQChoiceDAO mcqChoiceDAO;
	
	@Inject
	ExamDataService examDS;
	
	@Inject
	DataSource ds;
	
	@Transactional(value = TxType.REQUIRED)
	public void createQuestion(Question question, List<MCQChoice> choices) {
		questionDAO.create(question);
		question.setChoices(choices);
		questionDAO.update(question);
		for (MCQChoice choice : choices) {
			choice.setQuestion(question);
			mcqChoiceDAO.create(choice);
		}
	}
	
	@Transactional(value = TxType.REQUIRED)
	public void updateQuestion(Question question, List<MCQChoice> choices) {
		if (question == null || question.getId() == null || questionDAO.getById(question.getId()) == null) {
			throw new NullPointerException("Question is not found.");
		}
		question.setChoices(choices);
		questionDAO.update(question);
		for (MCQChoice choice : choices) {
			choice.setQuestion(question);
			mcqChoiceDAO.update(choice);
		}
	}
	
	public List<MCQChoice> getAllChoices(Question question) {
		if (question == null || question.getId() == null || questionDAO.getById(question.getId()) == null) {
			throw new NullPointerException("Question is not found.");
		}
		return question.getChoices();
	}
	
	public List<Question> getAllQuestions() {
		List<Question> questions = new ArrayList<Question>();
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT Q_ID FROM QUESTIONS");) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("Q_ID");
				Question question = questionDAO.getById(id);
				questions.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return questions;
	}
	
}
