package fr.epita.quizmanager.tests.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.business.QuestionDataService;

/**
 * @author Anh Tu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestQuestionDataService {

	private static final Logger LOGGER = LogManager.getLogger(TestExamDataService.class);

	@Inject
	QuestionDataService questionDs;

	@Inject
	DataSource ds;

	@Test
	public void testSaveQuestion() {
		// given
		Question question = new Question();
		question.setTitle("What is Java?");
		List<MCQChoice> choices = new ArrayList<MCQChoice>();
		for (int i = 0; i < 4; i++) {
			MCQChoice choice = new MCQChoice();
			choice.setContent("Java is a programming language " + i);
			choices.add(choice);
		}
		questionDs.createQuestion(question, choices);
		// then
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt1 = connection.prepareStatement("SELECT Q_ID FROM QUESTIONS WHERE Q_TITLE = ?");
				PreparedStatement stmt2 = connection.prepareStatement("SELECT C_ID FROM MCQ_CHOICES WHERE C_QUESTION_FK = ?");) {
			stmt1.setString(1, question.getTitle());
			stmt2.setLong(1, question.getId());
			ResultSet rs1 = stmt1.executeQuery();
			rs1.next();
			Long id = rs1.getLong("Q_ID");
			ResultSet rs2 = stmt2.executeQuery();
			List<Long> ids = new ArrayList<Long>();
			while(rs2.next()) {
				Long choiceId = rs2.getLong("C_ID");
				ids.add(choiceId);
			}
			Assert.assertEquals(question.getId(), id);
			Assert.assertEquals(choices.get(0).getId(), ids.get(0));
		} catch (Exception e) {
			LOGGER.error("Some exception occured while performing count verification", e);
		}

	}
}
