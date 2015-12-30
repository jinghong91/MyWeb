package web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.IAddressDAO;
import web.model.Address;
import web.service.IAddressService;

import java.util.List;

@Service
@Transactional
public class AddressService implements IAddressService {
    @Autowired
    IAddressDAO addressDAO;

    @Override
    public List<Address> getAddressListByClientId(int clientId) {
        return addressDAO.getAddressListByClientId(clientId);
    }
}
