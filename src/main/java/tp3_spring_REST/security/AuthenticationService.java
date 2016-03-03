package tp3_spring_REST.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import tp3_spring_REST.credential.model.Credential;
import tp3_spring_REST.user.model.User;
import tp3_spring_REST.user.repository.UserRepository;

@Component
public class AuthenticationService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) {
		Optional<User> option = userRepository.findOneByLogin(username);
		if (option.isPresent()) {
			User user = option.get();
			List<GrantedAuthority> rules = this.getUserCredentials(user);
			return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), rules);
		}
		throw new UsernameNotFoundException("username.not.found");
	}

	private List<GrantedAuthority> getUserCredentials(User user) {
		List<GrantedAuthority> rules = new ArrayList<GrantedAuthority>();
		
		List<Credential> credentials = user.getRoles();
		
		if(!CollectionUtils.isEmpty(credentials)) {
			for(Credential credential : credentials) {
				rules.add(new SimpleGrantedAuthority(credential.getCode()));
			}
		}
		
		return rules;
	}
}