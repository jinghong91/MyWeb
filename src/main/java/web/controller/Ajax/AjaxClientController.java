package web.controller.Ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web.model.Address;
import web.model.Client;
import web.service.IAddressService;
import web.service.IClientService;

import java.util.List;

@RestController
@RequestMapping(value = "/ajax/client")
public class AjaxClientController {

    @Autowired
    IAddressService addressService;
    @Autowired
    IClientService clientService;

    @RequestMapping(value = "/getAddressList")
    public List<Address> getAddressListViaAjax(@RequestParam int clientId) {
        return addressService.getAddressListByClientId(clientId);
    }

    @RequestMapping(value = "/deleteAddress")
    public void deleteAddress(@RequestParam int addressId) {
        addressService.deleteAddressById(addressId);
    }

    @RequestMapping(value = "/saveOrUpdateAddress")
    public void saveOrUpdateAddress(@RequestBody Address address, @RequestParam int clientId) {
        Client client = clientService.getClientById(clientId);
        address.setClient(client);
        addressService.saveOrUpdateAddress(address);
    }

}
