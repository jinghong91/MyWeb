package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.model.User;
import web.form.welcomeForm;
import web.service.IUserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class WelcomeController {
    @Autowired
    IUserService userService;
    @Autowired
    MessageSource messageSource;


    @RequestMapping(value = "/welcome")
    public String welcome(Model model) {
        return "welcome";
    }
















    @RequestMapping(value = "/home")
    public String aaa(Model model) {
        List<User> users = userService.getAll();
        model = initWelcomeForm(model);
        return "home";
    }


    @RequestMapping(value = "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("method", "add");
        return "new";
    }

    @RequestMapping(value = "/add")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("method", "add");
            return "new";
        }
        userService.addUser(user);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("message", "add user successfully");
        return "/home";
    }

    @RequestMapping(value = "edit-{userId}")
    public String edit(@PathVariable("userId") int userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("method", "update");
        return "new";
    }

    @RequestMapping(value = "/update")
    public String updateUeser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("method", "update");
            return "new";
        }
        userService.updateUser(user);
        initWelcomeForm(model);
        model.addAttribute("message", "add user successfully");
        return "/home";
    }

    @RequestMapping(value = "delete-{userId}")
    public String delete(@PathVariable("userId") int userId, Model model) {
        userService.deleteUserById(userId);
        initWelcomeForm(model);
        model.addAttribute("message", "delete user successfully");
        return "/home";
    }

    private Model initWelcomeForm(Model model) {
        welcomeForm form = new welcomeForm();
        form.setUserList(userService.getAll());
        model.addAttribute("welcomeForm", form);
        return model;
    }
}
