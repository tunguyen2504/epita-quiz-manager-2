package fr.epita.quizmanager.services.business;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.MCQAnswer;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.datamodel.User;
import fr.epita.quizmanager.services.dao.ExamDAO;
import fr.epita.quizmanager.services.dao.MCQAnswerDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizmanager.services.dao.UserDAO;

public class ExamDataService {

	@Inject
	UserDAO userDAO;

	@Inject
	ExamDAO examDAO;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	MCQAnswerDAO answerDAO;

	@Transactional(value = TxType.REQUIRED)
	public void saveAnswerToQuestion(User user, Exam exam, Question question, MCQAnswer answer) {
		if (user.getId() == null || userDAO.getById(user.getId()) == null) {
			throw new RuntimeException("User is not found.");
		}
		if (exam.getId() == null || examDAO.getById(exam.getId()) == null) {
			throw new RuntimeException("Exam is not found.");
		}
		if (question.getId() == null || questionDAO.getById(question.getId()) == null) {
			throw new RuntimeException("Question is not found.");
		}
		answer.setExam(exam);
		answer.setQuestion(question);
		answer.setUser(user);
		answerDAO.create(answer);
	}

	@Transactional(value = TxType.REQUIRED)
	public void addQuestionListToExam(Exam exam, List<Question> questions) {
		if (exam.getId() == null || examDAO.getById(exam.getId()) == null) {
			throw new RuntimeException("Exam is not found.");
		}
		for (Question question : questions) {
			if (question.getId() == null || questionDAO.getById(question.getId()) == null) {
				throw new RuntimeException("Question is not found.");
			} else {
				List<Exam> exams = new ArrayList<Exam>();
				question.setExams(exams);
				questionDAO.update(question);
			}
		}
		List<Question> questionsOfExam = exam.getQuestions();
		questionsOfExam.addAll(questions);
		exam.setQuestions(questionsOfExam);
		examDAO.update(exam);
	}

}
