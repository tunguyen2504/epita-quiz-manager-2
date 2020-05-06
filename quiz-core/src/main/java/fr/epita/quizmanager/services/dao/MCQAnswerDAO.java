package fr.epita.quizmanager.services.dao;

import java.util.Map;

import fr.epita.quizmanager.datamodel.MCQAnswer;

public class MCQAnswerDAO extends GenericDAO<MCQAnswer, Long>{

	@Override
	public String getQuery() {
		return "from MCQAnswer where content = :pContent";
	}

	@Override
	public void setParameters(Map<String, Object> parameters, MCQAnswer mcqAnswer) {
		parameters.put("pContent", mcqAnswer.getContent());
	}
	
	@Override
	public Class<MCQAnswer> getEntityClass() {
		return MCQAnswer.class;
	}
	
//	public List<MCQAnswer> search(MCQAnswer answer){
//		
//		Query searchQuery = em.createQuery("from MCQAnswer where content = :pContent");
//		String content = answer.getContent();
//		searchQuery.setParameter("pContent", content);
//		List<MCQAnswer> resultList = searchQuery.getResultList();
//		
//		return resultList;
//	}
//	
//	public MCQAnswer getById(Long id) {
//		return em.find(MCQAnswer.class, id);
//	}
	
	
	
}
