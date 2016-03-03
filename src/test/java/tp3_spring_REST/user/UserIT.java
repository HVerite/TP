package tp3_spring_REST.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import tp3_spring_REST.IntegrationTest;
import tp3_spring_REST.user.model.User;

@Sql("classpath:test-user-data.sql")
public class UserIT extends IntegrationTest {
	private String userUrl="/api/user";
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	@WithMockUser
	public void testFindAll() throws Exception{
		this.mockMvc.perform(get(userUrl))
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	@WithMockUser
	public void testFindOne() throws Exception{
		this.mockMvc.perform(get(userUrl+"/1"))
		.andExpect(jsonPath("$.id").value(1))
		.andExpect(jsonPath("$.login").value("hugo"));
	}
	
	@Test
	@WithMockUser(authorities="MANAGE_USERS")
	public void testCreate () throws Exception {
		User u = new User(null,"login","password","name");
		u.setLogin("test");
		
		this.mockMvc.perform(post(userUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u)))
		.andExpect(status().isCreated());
		
		this.mockMvc.perform(get(userUrl))
				.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	@WithMockUser(authorities="JeanJean")
	public void testCreateNotAllowed () throws Exception {
		User u = new User(null,"login","password","name");
		u.setLogin("test");
		
		this.mockMvc.perform(post(userUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u)))
		.andExpect(status().isForbidden());
		
	}
	
	@Test
	@WithMockUser
	public void testInvalidCreate () throws Exception {
		User u = new User(null,"","password","name");
		
		this.mockMvc.perform(post(userUrl)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u)))
		.andExpect(status().isPreconditionFailed());
	}
	
	
	@Test
	@WithMockUser
	public void testUpdate () throws Exception {
		User u = new User(null,"login","password","name");
		u.setLogin("test");
		
		this.mockMvc.perform(put(userUrl+"/1")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u)))
		.andExpect(status().isOk());
		
		this.mockMvc.perform(get(userUrl+"/1"))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.login").value(u.getLogin()))
				.andExpect(jsonPath("$.password").value(passwordEncoder.encode("password")))
				.andExpect(jsonPath("$.name").value(u.getName()));
	}
	
	@Test
	@WithMockUser
	public void testInvalidUpdate () throws Exception {
		User u = new User(null,"login","","name");
		
		this.mockMvc.perform(put(userUrl+"/1")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonHelper.serialize(u)))
		.andExpect(status().isPreconditionFailed());
		
	}
	
	
	@Test
	@WithMockUser
	public void testDelete () throws Exception {
		this.mockMvc.perform(get(userUrl+"/1"))
		.andExpect(status().isOk());
		
		this.mockMvc.perform(delete(userUrl+"/1"))
		.andExpect(status().isOk());
		
		this.mockMvc.perform(get(userUrl+"/1"))
		.andExpect(status().isNotFound());
	}
}