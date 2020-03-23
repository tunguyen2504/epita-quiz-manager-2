package fr.epita.quizmanager.tests;

import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.GenericDAO;
import fr.epita.quizmanager.services.QuestionDAO;

public class TestGenericDAO {
	
	public void testGenericDAO() {
		QuestionDAO dao = new QuestionDAO();
		dao.create(new Question());
	}
}
