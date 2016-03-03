package tp3_spring_REST.user.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import tp3_spring_REST.credential.model.Credential;
import tp3_spring_REST.persistence.GenericEntity;

@Entity
@Table(name = "user_")
public class User implements GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5761974528511247257L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotBlank
	private String login;

	@Column
	@NotBlank
	private String password;

	
	@Column
	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
	List<Credential> roles;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public List<Credential> getRoles() {
		return roles;
	}

	public void setRoles(List<Credential> roles) {
		this.roles = roles;
	}

	public User(Long id, String login, String password, String name) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.name = name;
	}
	
	public User() {
		super();
	}

}
