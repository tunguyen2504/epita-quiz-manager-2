package fr.epita.quizmanager.services;

import java.util.List;

import javax.persistence.Query;

import fr.epita.quizmanager.datamodel.Question;

public class QuestionDAO extends GenericDAO<Question, Long> {
	
	public List<Question> search(Question question){
		
		Query searchQuery = em.createQuery("from Question where title = :pTitle");
		String title = question.getTitle();
		searchQuery.setParameter("pTitle", title);
		List<Question> resultList = searchQuery.getResultList();
		
		return resultList;
	}
	
	public Question getById(Long id) {
		return em.find(Question.class, id);
	}
	
}
