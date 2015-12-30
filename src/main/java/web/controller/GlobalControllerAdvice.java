package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import web.service.IMenuService;

@ControllerAdvice(basePackages = {"web.controller"})
public class GlobalControllerAdvice {
    @Autowired
    IMenuService menuService;

    @ModelAttribute
    public void globalAttributes(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username

        model.addAttribute("global_username", name);
        model.addAttribute("global_menuList", menuService.getAll());
    }

}
