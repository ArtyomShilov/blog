package blog.controllers;
import blog.forms.RegisterForm;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
@Controller
@SessionAttributes("user")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notifyService;


    @RequestMapping(value = "/users/register", method = RequestMethod.GET)
    public String Register(RegisterForm registerForm) {
        return "users/register";
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public String RegisterPage(@Valid RegisterForm registerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Fill the form correctly");
            return "users/register";
        }


        if (null != userService.findByUsername(registerForm.getUsername())) {
            notifyService.addErrorMessage("User already exist");
            return "users/register";
        }

        User user = new User();
        user.setId(0L);
        user.setUsername(registerForm.getUsername());
        user.setPasswordHash(registerForm.getPassword());

        userService.create(user);
        notifyService.addInfoMessage("Registration is successful.");
        return "redirect:/";
}


}