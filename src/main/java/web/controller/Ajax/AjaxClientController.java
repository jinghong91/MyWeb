package web.controller.Ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.model.Address;
import web.service.IAddressService;

import java.util.List;

@RestController
public class AjaxClientController {

    @Autowired
    IAddressService addressService;

    @RequestMapping(value = "/ajax/client/getAddressList")
    public List<Address> getSearchResultViaAjax(@RequestParam int clientId) {
        return addressService.getAddressListByClientId(clientId);
    }

}
