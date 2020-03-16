package fr.epita.quizmanager.services;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import fr.epita.quizmanager.datamodel.Question;

public class QuestionDAO {

	@Inject
	SessionFactory sf;

	public void create(Question question) {
		Session openSession = sf.openSession();
		openSession.save(question);
		openSession.close();
	}

	public void update(Question question) {
		Session openSession = sf.openSession();
		openSession.update(question);
		openSession.close();
	}

	public void delete(Question question) {
		Session openSession = sf.openSession();
		openSession.delete(question);
		openSession.close();
	}
	
	@SuppressWarnings("unchecked")
//	public List<Question> search(String title){
//		Session openSession = sf.openSession();
//		Query query = openSession.createQuery("from Question q where q.title like");
//		query.set
//		query.setString("title", "'%" + title + "%'");
//		System.out.println(query.getQueryString());
//		List<Question> questions  = (List<Question>) query.list();
//		System.out.println(Arrays.toString(questions.toArray()));
//		openSession.close();
//		
//		return questions;
//	}
	
	public Question getById(Long id) {
		Session openSession = sf.openSession();
		Question question = openSession.byId(Question.class).getReference(id);
		openSession.close();
		
		return question;
	}
	
}