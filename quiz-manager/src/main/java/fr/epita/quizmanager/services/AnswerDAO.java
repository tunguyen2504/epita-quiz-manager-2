package fr.epita.quizmanager.services;

import java.util.List;

import javax.persistence.Query;

import fr.epita.quizmanager.datamodel.Answer;

public class AnswerDAO extends GenericDAO<Answer, Long>{
	
	public List<Answer> search(Answer answer){
		
		Query searchQuery = em.createQuery("from Answer where content = :pContent");
		String content = answer.getContent();
		searchQuery.setParameter("pContent", content);
		List<Answer> resultList = searchQuery.getResultList();
		
		return resultList;
	}
	
	public Answer getById(Long id) {
		return em.find(Answer.class, id);
	}
	
}
