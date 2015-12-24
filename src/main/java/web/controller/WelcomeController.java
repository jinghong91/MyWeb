package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import web.data.model.User;
import web.service.IUserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class WelcomeController {
    @Autowired
    IUserService userService;
    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/home")
    public String welcome(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("message", "ghjkl");
        return "home";
    }

    @RequestMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @RequestMapping(value = "/add")
    public String addUeser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.addUser(user);
        model.addAttribute("message", "add user successfully");
        return "redirect:/home";
    }
}
