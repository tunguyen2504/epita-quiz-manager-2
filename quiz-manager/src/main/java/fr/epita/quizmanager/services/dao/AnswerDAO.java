package fr.epita.quizmanager.services.dao;

import java.util.Map;

import fr.epita.quizmanager.datamodel.Answer;

public class AnswerDAO extends GenericDAO<Answer, Long>{

	@Override
	public String getQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParameters(Map<String, Object> parameters, Answer question) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Class<Answer> getEntityClass() {
		return Answer.class;
	}
	
//	public List<Answer> search(Answer answer){
//		
//		Query searchQuery = em.createQuery("from Answer where content = :pContent");
//		String content = answer.getContent();
//		searchQuery.setParameter("pContent", content);
//		List<Answer> resultList = searchQuery.getResultList();
//		
//		return resultList;
//	}
//	
//	public Answer getById(Long id) {
//		return em.find(Answer.class, id);
//	}
	
	
	
}