package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import web.service.IMenuService;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice(basePackages = {"web.controller"})
public class GlobalControllerAdvice {
    @Autowired
    IMenuService menuService;
    @Autowired
    ServletContext context;
    @Autowired
    MessageSource messageSource;
    @ModelAttribute
    public void globalAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        model.addAttribute("global_username", name);
        model.addAttribute("global_menuList", menuService.getAll());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        String datePattern=messageSource.getMessage("global.java.dateFormat",null,"dd-MM-yyyy", LocaleContextHolder.getLocale());
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(datePattern), true));
    }



}
