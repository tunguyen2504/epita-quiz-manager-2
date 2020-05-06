package fr.epita.quizmanager.tests.integration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.MCQAnswer;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.datamodel.User;
import fr.epita.quizmanager.services.business.ExamDataService;
import fr.epita.quizmanager.services.dao.ExamDAO;
import fr.epita.quizmanager.services.dao.MCQAnswerDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizmanager.services.dao.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestExamDataService {

	private static final Logger LOGGER = LogManager.getLogger(TestExamDataService.class);

	@Inject
	ExamDataService examDS;
	
	@Inject
	ExamDAO examDAO;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	UserDAO userDAO;

	@Inject
	MCQAnswerDAO answerDAO;

	@Inject
	DataSource ds;

	@Test
	public void testSaveAnswerForExistingQuestion() {
		// given
		Exam exam = new Exam();
		exam.setTitle("Advanced Java");
		
		
		Question question = new Question();
		question.setTitle("What is JPA?");
		questionDAO.create(question);
		List<Question> questions = new ArrayList<>();
		questions.add(question);
		exam.setQuestions(questions);
		examDAO.create(exam);
		
		User user = new User();
		user.setLoginName("anhtu.nguyen");
		userDAO.create(user);

		MCQAnswer answer = new MCQAnswer();
		answer.setContent("JPA is Java Persistence API");
		// when
//		try {
		examDS.saveAnswerToQuestion(user, exam, question, answer);
//		} catch (Exception e) {
//			LOGGER.error(e);
//		}
		// then
//		try (Connection connection = ds.getConnection();
//				PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(1) FROM ANSWERS WHERE A_QUESTION_FK = " + question.getId() + " AND A_USER_FK = " + user.getId());
//				ResultSet rs = stmt.executeQuery();) {
//			rs.next();
//			int count = rs.getInt(1);
//			Assert.assertEquals(1, count);
//		} catch (Exception e) {
//			LOGGER.error("Some exception occured while performing count verification", e);
//		}
		MCQAnswer fetchedAnswer = answerDAO.getById(answer.getId());
		Assert.assertEquals(user.getLoginName(), fetchedAnswer.getUser().getLoginName());
		Assert.assertEquals(answer.getContent(), fetchedAnswer.getContent());
	}

	@Test
	public void testSaveAnswerForNotFoundQuestion() {
		// given
		Exam exam = new Exam();
		exam.setTitle("Advanced Java");
		examDAO.create(exam);
		
		Question question = new Question();
		User user = new User();
		user.setLoginName("tu.nguyen");
		userDAO.create(user);
		MCQAnswer answer = new MCQAnswer();
		answer.setContent("Java is a subject at EPITA.");
		// when
		try {
			examDS.saveAnswerToQuestion(user, exam, question, answer);
		} catch (NullPointerException  e) { // then
			LOGGER.error(e.getMessage());
			Assert.assertTrue(e instanceof NullPointerException);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Test
	public void testSaveAnswerByNotFoundUser() {
		// given
		Exam exam = new Exam();
		exam.setTitle("Advanced Java");
		examDAO.create(exam);
		Question question = new Question();
		question.setTitle("What is JPA?");
		questionDAO.create(question);
		User user = new User();
		MCQAnswer answer = new MCQAnswer();
		answer.setContent("Java is a subject at EPITA.");
		// when
		try {
			examDS.saveAnswerToQuestion(user, exam, question, answer);
		} catch (NullPointerException  e) { // then
			LOGGER.error(e.getMessage());
			Assert.assertTrue(e instanceof NullPointerException);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

}
