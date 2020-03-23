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
import fr.epita.quizmanager.services.AnswerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestAnswerDAO {

	private static final Logger LOGGER = LogManager.getLogger(TestAnswerDAO.class);
	
	@Inject
	AnswerDAO dao;
	
	@Inject
	DataSource ds;
	
	@Test
	public void testCreate() {
		//given
		Answer answer = new Answer();
		//when
		dao.create(answer);
		
		LOGGER.info("answer id {}", answer.getId());
		//then
		try (Connection connection = ds.getConnection();
		PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(1) FROM ANSWERS");
		ResultSet rs = stmt.executeQuery();) {
			rs.next();
			int count = rs.getInt(1);	
			Assert.assertEquals(1, count);
		} catch (Exception e) {
			LOGGER.error("Some exception occured while performing count verification", e);
		}
	}
	
}
