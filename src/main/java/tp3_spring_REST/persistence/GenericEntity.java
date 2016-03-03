package tp3_spring_REST.persistence;

import java.io.Serializable;

public interface GenericEntity extends Serializable{
	Long getId();
	void setId(Long id);
}
