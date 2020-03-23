package fr.epita.quizmanager.tests;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.quizmanager.datamodel.Student;
import fr.epita.quizmanager.services.StudentDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class TestStudentDAO {

	@Inject
	StudentDAO dao;
	
//	@Test
//	public void testCreate() {
//		Student student = new Student();
//		dao.create(student);
//		
//		Assert.assertNotEquals(0L, student.getId().longValue());
//	}
//	
//	@Test
//	public void testGetById() {
//		Student student = dao.getById(1L);
//		
//		Assert.assertEquals(1L, student.getId().longValue());
//	}
}
