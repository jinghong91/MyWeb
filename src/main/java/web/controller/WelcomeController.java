package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @ModelAttribute("pageTitle")
    public String  initPageTitle(){
        return "pageTitle.welcome";
    }
}
