package fr.epita.quizmanager.services.business;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import fr.epita.quizmanager.datamodel.Answer;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.datamodel.User;
import fr.epita.quizmanager.services.dao.AnswerDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizmanager.services.dao.UserDAO;

public class ExamDataService {

	@Inject
	UserDAO userDAO;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	AnswerDAO answerDAO;

	@Transactional(value = TxType.REQUIRED)
	public void saveAnswerToQuestion(User user, Question question, Answer answer) {
		if (user.getId() == null || userDAO.getById(user.getId()) == null) {
			throw new RuntimeException("User is not found.");
		}
		if (question.getId() == null || questionDAO.getById(question.getId()) == null) {
			throw new RuntimeException("Question is not found.");
		} else {
			answer.setQuestion(question);
			answer.setUser(user);
			answerDAO.create(answer);
		}
	}

}
