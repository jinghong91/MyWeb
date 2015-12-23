package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.data.model.User;
import web.service.IUserService;

import java.util.List;

@Controller
public class WelcomeController {
    @Autowired
    IUserService userService;

    @RequestMapping(value = "/home")
    public String welcome(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("message", "world");
        return "home";
    }
}
