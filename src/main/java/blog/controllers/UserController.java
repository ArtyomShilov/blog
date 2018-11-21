package blog.controllers;

import blog.models.User;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/users" }, method = RequestMethod.GET)
    public ModelAndView get(Model model, Pageable pageable) {
        if (!model.containsAttribute("user")) return new ModelAndView("redirect:/users/login");

        Page<User> users = userService.getUsers(pageable);
        model.addAttribute("users", users);

        return new ModelAndView("/users/listUsers");
    }
}
