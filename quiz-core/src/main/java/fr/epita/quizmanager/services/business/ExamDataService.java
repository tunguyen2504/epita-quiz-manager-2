package fr.epita.quizmanager.services.business;

import java.util.ArrayList;
import java.util.Arrays;
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
		if (user == null || user.getId() == null || userDAO.getById(user.getId()) == null) {
			throw new NullPointerException("User is not found.");
		}
		if (exam == null || exam.getId() == null || examDAO.getById(exam.getId()) == null) {
			throw new NullPointerException("Exam is not found.");
		}
		if (question == null || question.getId() == null || questionDAO.getById(question.getId()) == null) {
			throw new NullPointerException("Question is not found.");
		}
		answer.setExam(exam);
		answer.setQuestion(question);
		answer.setUser(user);
		answerDAO.create(answer);
	}

	@Transactional(value = TxType.REQUIRED)
	public void addQuestionListToExam(Exam exam, List<Question> questions) {
		if (exam == null || exam.getId() == null || examDAO.getById(exam.getId()) == null) {
			throw new NullPointerException("Exam is not found.");
		}
		for (Question question : questions) {
			if (question == null || question.getId() == null || questionDAO.getById(question.getId()) == null) {
				throw new NullPointerException("Question is not found.");
			} else {
				question.getExams().add(exam);
				questionDAO.update(question);
			}
		}
		exam.getQuestions().addAll(questions);
		examDAO.update(exam);
	}
	
	@Transactional(value = TxType.REQUIRED)
	public void removeQuestionsFromExam(Exam exam, List<Question> questions) {
		if (exam == null || exam.getId() == null || examDAO.getById(exam.getId()) == null) {
			throw new NullPointerException("Exam is not found.");
		}
		List<Question> questionsOfExam = exam.getQuestions();
		List<Question> questionsToRemove = new ArrayList<Question>();
		for (Question question : questions) {
			if (question == null || question.getId() == null || questionDAO.getById(question.getId()) == null) {
				throw new NullPointerException("Question is not found.");
			} else {
				List<Exam> exams = question.getExams();
				List<Exam> examsToRemove = new ArrayList<Exam>();
				for (Exam e : exams) {
					if (e.getId().equals(exam.getId())) {
						examsToRemove.add(e);
					}
				}
				exams.removeAll(examsToRemove);
				questionDAO.update(question);

				for (Question q : questionsOfExam) {
					if (q.getId().equals(question.getId())) {
						questionsToRemove.add(q);
					}
				}
			}
		}
		questionsOfExam.removeAll(questionsToRemove);
		examDAO.update(exam);
	}
	
	public List<Question> getAllQuestions(Exam exam) {
		if (exam == null || exam.getId() == null || examDAO.getById(exam.getId()) == null) {
			throw new NullPointerException("Exam is not found.");
		}
		
		return exam.getQuestions();
	}

}
