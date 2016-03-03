package tp3_spring_REST.repository;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import tp3_spring_REST.persistence.GenericEntity;

public abstract class AbstractJpaRepository <T extends GenericEntity>{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	protected Class<T> entityClass;
	
	@PostConstruct
	public void init() {
		entityClass=getEntityClass();
	}

	protected abstract Class<T> getEntityClass();
	
	protected Session getSession() {
		return entityManager.unwrap(Session.class);
	}
	
	@Transactional
	public T save(T entity) {
		if(isNew(entity)) {
			entityManager.persist(entity);
			return entity;
		} else if(!entityManager.contains(entity)) {
			return entityManager.merge(entity);
		}
		return entity;
	}
	
	@Transactional
	public T findOne(long id) {
		return entityManager.find(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<T> findAll() {
		return getSession().createCriteria(entityClass).list();
	}
	
	@Transactional
	public void delete(T entity) {
		if(!getSession().contains(entity)) {
			entityManager.remove(getSession().merge(entity));
		} else {
			entityManager.remove(entity);
		}
	}
	
	public boolean isNew(T entity) {
		return entity.getId() == null;
	}
	
}
