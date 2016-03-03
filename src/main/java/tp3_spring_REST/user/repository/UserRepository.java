package tp3_spring_REST.user.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tp3_spring_REST.repository.AbstractJpaRepository;
import tp3_spring_REST.user.model.User;

@Repository
public class UserRepository extends AbstractJpaRepository<User>{

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
	
	@Transactional
	public Optional<User> findOneByLogin(String login) {
		System.out.println("+++++" + login);
		EntityManager em = getEntityManager();
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u "
				+ "WHERE u.login=:login", User.class);
		query.setParameter("login", login);
		return Optional.of(query.getSingleResult());
	}

}
