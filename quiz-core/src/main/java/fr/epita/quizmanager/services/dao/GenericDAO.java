package fr.epita.quizmanager.services.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;


/**
 * @author Anh Tu
 *
 * @param <T>
 * @param <ID>
 */
public abstract class GenericDAO<T, ID> {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional(value = TxType.REQUIRED) // to get a transaction if it exists, or else create a transaction
	public void create(T entity) {
		em.persist(entity);
	}

	@Transactional(value = TxType.REQUIRED)
	public void update(T entity) {
		em.merge(entity);
	}

	@Transactional(value = TxType.REQUIRED)
	public void delete(T entity) {
		entity = em.merge(entity);
		em.refresh(entity);
		em.remove(entity);
	}

	public abstract String getQuery();

	public abstract void setParameters(Map<String, Object> parameters, T criteria);

	public List<T> search(T criteria) {
		Query searchQuery = em.createQuery(getQuery());
		Map<String, Object> parameters = new LinkedHashMap<String, Object>();
		setParameters(parameters, criteria);
//		searchQuery.getParameter(); We have to check if the parameter in the query is match with the entry in the map 
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			searchQuery.setParameter(entry.getKey(), entry.getValue());
		}
		
		return searchQuery.getResultList();
	}

	public T getById(ID id) {
		return em.find(getEntityClass(), id);
	}
	
	public abstract Class<T> getEntityClass();
}
