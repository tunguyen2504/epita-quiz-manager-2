package fr.epita.quizmanager.tests;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.QuestionDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestQuestionDAO {
	
	@Inject
	QuestionDAO dao;
	
	@Test
	public void testCreate() {
		Question question = new Question();
		dao.create(question);
		
		Assert.assertNotEquals(0L, question.getId().longValue());
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
	
	@Test
	public void testGetById() {
		Question question = dao.getById(1L);
		
		Assert.assertEquals(1L, question.getId().longValue());
	}
}