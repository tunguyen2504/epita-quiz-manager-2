package fr.epita.quizmanager.services;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class GenericDAO<T, ID> {

	@PersistenceContext
	protected EntityManager em;
	
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
		em.remove(entity);
	}
	
	public abstract List<T> search(T criteria);
	public abstract T getById(ID id);
}
