package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.service.IClientService;

@Controller
public class ClientController {
    @Autowired
    private IClientService clientService;

    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public String initClients(Model model) {
        model.addAttribute("clientList", clientService.getAll());
        return "client";
    }

    @ModelAttribute("pageTitle")
    public String  initPageTitle(){
        return "pageTitle.client";
    }
}
