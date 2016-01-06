package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/accessDenied")
    public String index(Model model) {
        model.addAttribute("msg", "You can't access this page");
        return "welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("error", "invalid Username and Password");
        }
        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully");
        }
        return "login";
    }

}
