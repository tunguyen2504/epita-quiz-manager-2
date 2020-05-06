package fr.epita.quizmanager.services.dao;

import java.util.Map;

import fr.epita.quizmanager.datamodel.Question;

public class QuestionDAO extends GenericDAO<Question, Long> {

//	public List<Question> search(Question question){
//		
////		Query searchQuery = em.createQuery("from Question where title = :pTitle");
////		String title = question.getTitle();
////		searchQuery.setParameter("pTitle", title);
////		List<Question> resultList = searchQuery.getResultList();
//		
//		return resultList;
//	}

//	public Question getById(Long id) {
//		return em.find(Question.class, id);
//	}

	@Override
	public String getQuery() {
		return "from Question where title = :pTitle";
	}

	public void setParameters(Map<String, Object> parameters, Question question) {
		parameters.put("pTitle", question.getTitle());
	}

	@Override
	public Class<Question> getEntityClass() {
		return Question.class;
	}

}