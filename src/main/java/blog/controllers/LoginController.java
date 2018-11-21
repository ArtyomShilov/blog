package blog.controllers;

import blog.forms.LoginForm;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notifyService;

    @RequestMapping(value = "/users/logout",  method=RequestMethod.POST)
    public String postLogout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @RequestMapping("/users/login")
    public String getLogin(LoginForm loginForm) {
        return "users/login";
    }

    @RequestMapping(value="/users/login", method=RequestMethod.POST)
    public String postLogin(@Valid LoginForm loginForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please correct username/password");
            return "users/login";
        }

        User user = userService.findByUsername(loginForm.getUsername());
        if (user == null){
            notifyService.addErrorMessage("Wrong username/password");
            return "/users/login";
        }

        if(!user.getPasswordHash().equals(loginForm.getPassword())) {
            notifyService.addErrorMessage("Please correct username/password");
            return "users/login";
        }

        model.addAttribute("user", user);


        notifyService.addInfoMessage("Successfully login");
        return "redirect:/";
    }


}

