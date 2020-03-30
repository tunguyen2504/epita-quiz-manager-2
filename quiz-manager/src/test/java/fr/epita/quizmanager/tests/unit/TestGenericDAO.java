package fr.epita.quizmanager.tests.unit;

import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.dao.GenericDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;

public class TestGenericDAO {
	
	public void testGenericDAO() {
		QuestionDAO dao = new QuestionDAO();
		dao.create(new Question());
	}
}
