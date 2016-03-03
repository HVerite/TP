package tp3_spring_REST.credential.repository;

import org.springframework.stereotype.Repository;

import tp3_spring_REST.credential.model.Credential;
import tp3_spring_REST.repository.AbstractJpaRepository;

@Repository
public class CredentialRepository extends AbstractJpaRepository<Credential>{

	@Override
	protected Class<Credential> getEntityClass() {
		// TODO Auto-generated method stub
		return Credential.class;
	}
}
