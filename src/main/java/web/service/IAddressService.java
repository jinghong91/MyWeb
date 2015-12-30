package web.service;

import web.model.Address;

import java.util.List;

public interface IAddressService {
    List<Address> getAddressListByClientId(int clientId);


}
