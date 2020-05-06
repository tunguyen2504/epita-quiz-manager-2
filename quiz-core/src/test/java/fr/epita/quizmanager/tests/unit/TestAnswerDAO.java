package fr.epita.quizmanager.tests.unit;

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

import fr.epita.quizmanager.datamodel.MCQAnswer;
import fr.epita.quizmanager.services.dao.MCQAnswerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestAnswerDAO {

	private static final Logger LOGGER = LogManager.getLogger(TestAnswerDAO.class);

	@Inject
	MCQAnswerDAO dao;

	@Inject
	DataSource ds;

	@Test
	public void testCreate() {
		// given
		MCQAnswer answer = new MCQAnswer();
		answer.setContent("Java");
		// when
		dao.create(answer);

		LOGGER.info("answer id {}", answer.getId());
		// then
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT A_ID FROM QUESTIONS WHERE Q_TITLE = 'Java'");
				) {
			ResultSet rs = stmt.executeQuery();
			rs.next();
			Long id = rs.getLong("A_ID");
			Assert.assertEquals(answer.getId(), id);
		} catch (Exception e) {
			LOGGER.error("Some exception occured while performing count verification", e);
		}
	}

}
