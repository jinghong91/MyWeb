package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.service.IClientService;

@Controller
@RequestMapping(value = "/clientManagement")
public class ClientManagementController {
    @Autowired
    private IClientService clientService;

    @RequestMapping()
    public String init(Model model) {
        model.addAttribute("clientList", clientService.getAll());
        return "clientManagement";
    }

    @ModelAttribute("pageTitle")
    public String  initPageTitle(){
        return "pageTitle.clientManagement";
    }
}
