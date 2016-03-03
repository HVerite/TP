package tp3_spring_REST.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import tp3_spring_REST.user.model.User;
import tp3_spring_REST.user.service.UserService;

@Controller
@RequestMapping("/userForm")
@SessionAttributes("user")
public class UserViewController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView display() {
		User user = new User();
		ModelAndView mv = new ModelAndView("user");
		mv.addObject("user", user);
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid @ModelAttribute User user, BindingResult br) {
		if (br.hasErrors()) {
			return "user";
		}
		userService.save(user);
		return "update_user_ok";
	}

}
