package fr.epita.quizmanager.tests;

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
import fr.epita.quizmanager.services.ExamDataService;
import fr.epita.quizmanager.services.QuestionDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestExamDataService {

	private static final Logger LOGGER = LogManager.getLogger(TestExamDataService.class);

	@Inject
	ExamDataService examDS;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	DataSource ds;

	@Test
	public void testSaveAnswerForExistingQuestion() {
		// given
		Question question = new Question();
		question.setTitle("What is JPA?");
		questionDAO.create(question);
		Answer answer = new Answer();
		answer.setContent("JPA is Java Persistence API");
		// when
		try {
			examDS.saveAnswer(question, answer);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// then
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(1) FROM ANSWERS_QUESTIONS");
				ResultSet rs = stmt.executeQuery();) {
			rs.next();
			int count = rs.getInt(1);
			Assert.assertEquals(1, count);
		} catch (Exception e) {
			LOGGER.error("Some exception occured while performing count verification", e);
		}
	}

	@Test
	public void testSaveAnswerForNotFoundQuestion() {
		// given
		Question question = new Question();
		Answer answer = new Answer();
		answer.setContent("Java is a subject at EPITA.");
		// when
		try {
			examDS.saveAnswer(question, answer);
		} catch (Exception e) { // then
			System.out.println("We got this exception: " + e.getMessage());
			Assert.assertTrue(e instanceof NullPointerException);
		}
	}

}
