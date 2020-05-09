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

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.MCQAnswer;
import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.datamodel.User;
import fr.epita.quizmanager.services.dao.ExamDAO;
import fr.epita.quizmanager.services.dao.MCQAnswerDAO;
import fr.epita.quizmanager.services.dao.MCQChoiceDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizmanager.services.dao.UserDAO;

/**
 * @author Anh Tu
 *
 */
public class ExamDataService {

	@Inject
	UserDAO userDAO;

	@Inject
	ExamDAO examDAO;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	MCQAnswerDAO answerDAO;
	
	@Inject
	MCQChoiceDAO mcqChoiceDAO;

	@Inject
	DataSource ds;

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

	public List<Exam> getAllExams() {
		List<Exam> exams = new ArrayList<Exam>();
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT E_ID FROM EXAMS");) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("E_ID");
				Exam exam = examDAO.getById(id);
				exams.add(exam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return exams;
	}
	
	public String getExamGradeByUser(User user, Exam exam) {
		if (user == null || user.getId() == null || userDAO.getById(user.getId()) == null) {
			throw new NullPointerException("User is not found.");
		}
		if (exam == null || exam.getId() == null || examDAO.getById(exam.getId()) == null) {
			throw new NullPointerException("Exam is not found.");
		}
		int point = 0;
		int total = exam.getQuestions().size();
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT A_CONTENT, A_QUESTION_FK FROM MCQ_ANSWERS WHERE A_EXAM_FK = ? AND A_USER_FK = ?");) {
			stmt.setLong(1, exam.getId());
			stmt.setLong(2, user.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long choiceId = Long.parseLong(rs.getString("A_CONTENT"));
				MCQChoice choice = mcqChoiceDAO.getById(choiceId);
				if (choice.getIsValid()) {
					point++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return point + "/" + total;
	}

}
