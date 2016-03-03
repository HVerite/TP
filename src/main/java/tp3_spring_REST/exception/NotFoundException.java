package tp3_spring_REST.exception;

import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

public class NotFoundException extends NoSuchRequestHandlingMethodException {

	public NotFoundException() {
		super("Not Found", NotFoundException.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 943371637512422539L;

}
