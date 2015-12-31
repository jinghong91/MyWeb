package web.service;

import web.model.Address;

import java.util.List;

public interface IAddressService {
    List<Address> getAddressListByClientId(int clientId);
    void deleteAddressById(int addressId);
    Address getAddressById(int addressId);
void saveOrUpdateAddress(Address address);


}
