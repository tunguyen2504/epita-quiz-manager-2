package fr.epita.quizmanager.tests.unit;

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

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.dao.ExamDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;

/**
 * @author Anh Tu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestExamDAO {

	private static final Logger LOGGER = LogManager.getLogger(TestQuestionDAO.class);

	@Inject
	ExamDAO examDao;

	@Inject
	QuestionDAO questionDao;

	@Inject
	DataSource ds;

	@Test
	public void testCreate() {
		Exam exam = new Exam();
		exam.setTitle("Java");
		examDao.create(exam);
		// then
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT E_ID FROM EXAMS WHERE E_TITLE = ?");
				) {
			stmt.setString(1, "Java");
			ResultSet rs = stmt.executeQuery();
			rs.next();
			Long id = rs.getLong("E_ID");
			Assert.assertEquals(exam.getId(), id);
		} catch (Exception e) {
			LOGGER.error("Some exception occured while performing count verification", e);
		}
	}

	@Test
	public void testGetQuestionsByExam() {
		Exam exam = new Exam();
		exam.setTitle("Advanced Java");
		Question question1 = new Question();
		question1.setTitle("What is Java?");
		Question question2 = new Question();
		question2.setTitle("What is Spring?");
		List<Question> questions = new ArrayList<Question>();
		questionDao.create(question1);
		questionDao.create(question2);
		questions.add(question1);
		questions.add(question2);

		exam.setQuestions(questions);
		examDao.create(exam);
//		List<Question> questionsOfExam = examDao.getQuestionsByExam(exam);

		// then
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection
						.prepareStatement("SELECT Q_ID FROM EXAM_QUESTION WHERE E_ID = ?");) {
			stmt.setLong(1, exam.getId());
			ResultSet rs = stmt.executeQuery();
			List<Question> res_questions = new ArrayList<Question>();
			while (rs.next()) {
				Question q = questionDao.getById(rs.getLong("Q_ID"));
				res_questions.add(q);
			}
			
			Assert.assertEquals(question1.getTitle(), res_questions.get(0).getTitle());
			
		} catch (Exception e) {
			LOGGER.error("Some exception occured while performing count verification", e);
		}

	}
}
