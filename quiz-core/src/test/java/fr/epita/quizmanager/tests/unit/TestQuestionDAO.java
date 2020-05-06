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

import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.dao.QuestionDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestQuestionDAO {

	private static final Logger LOGGER = LogManager.getLogger(TestQuestionDAO.class);

	@Inject
	QuestionDAO dao;

	@Inject
	DataSource ds;

	@Test
	public void testCreate() {
		//given
		Question question = new Question();
		question.setTitle("Java");
		//when
		dao.create(question);
		
		LOGGER.info("question id {}", question.getId());
		//then
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT Q_ID FROM QUESTIONS WHERE Q_TITLE = 'Java'");
				) {
			ResultSet rs = stmt.executeQuery();
			rs.next();
			Long id = rs.getLong("Q_ID");
			Assert.assertEquals(question.getId(), id);
		} catch (Exception e) {
			LOGGER.error("Some exception occured while performing count verification", e);
		}
	}

//	@Test
//	public void testUpdate() {
//		Question question = new Question();
//		dao.create(question);
//		
//		question.setTitle("What is JPA?");
//		dao.update(question);
//		
//		Question res = dao.getById(question.getId());
//		
//		Assert.assertEquals(question.getQuestion(), res.getQuestion());
//	}
//	
//	@Test
//	public void testDelete() {
//		Question question = new Question();
//		question.setTitle("What is JPA?");
//		dao.create(question);
//		List<Question> questions = dao.search("JPA");
//		System.out.println(Arrays.toString(questions.toArray()));
//		Assert.assertNotEquals(null, questions);
//	}

//	@Test
//	public void testGetById() {
//		Question question = dao.getById(1L);
//
//		Assert.assertEquals(1L, question.getId().longValue());
//	}
}
