package fr.epita.quizmanager.tests.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quizmanager.datamodel.Answer;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.datamodel.User;
import fr.epita.quizmanager.services.business.ExamDataService;
import fr.epita.quizmanager.services.dao.AnswerDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizmanager.services.dao.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestExamDataService {

	private static final Logger LOGGER = LogManager.getLogger(TestExamDataService.class);

	@Inject
	ExamDataService examDS;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	UserDAO userDAO;

	@Inject
	AnswerDAO answerDAO;

	@Inject
	DataSource ds;

	@Test
	public void testSaveAnswerForExistingQuestion() {
		// given
		Question question = new Question();
		question.setTitle("What is JPA?");
		questionDAO.create(question);

		User user = new User();
		user.setLoginName("anhtu.nguyen");
		user.setEmail("anh-tu.nguyen@epita.fr");
		userDAO.create(user);

		Answer answer = new Answer();
		answer.setContent("JPA is Java Persistence API");
		// when
//		try {
		examDS.saveAnswerToQuestion(user, question, answer);
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
		Answer fetchedAnswer = answerDAO.getById(answer.getId());
		Assert.assertEquals(user.getLoginName(), fetchedAnswer.getUser().getLoginName());
		Assert.assertEquals(answer.getContent(), fetchedAnswer.getContent());
	}

	@Test
	public void testSaveAnswerForNotFoundQuestion() {
		// given
		Question question = new Question();
		User user = new User();
		user.setLoginName("tu.nguyen");
		user.setEmail("tu.nguyen@epita.fr");
		userDAO.create(user);
		Answer answer = new Answer();
		answer.setContent("Java is a subject at EPITA.");
		// when
		try {
			examDS.saveAnswerToQuestion(user, question, answer);
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
		Question question = new Question();
		question.setTitle("What is JPA?");
		questionDAO.create(question);
		User user = new User();
		Answer answer = new Answer();
		answer.setContent("Java is a subject at EPITA.");
		// when
		try {
			examDS.saveAnswerToQuestion(user, question, answer);
		} catch (NullPointerException  e) { // then
			LOGGER.error(e.getMessage());
			Assert.assertTrue(e instanceof NullPointerException);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

}