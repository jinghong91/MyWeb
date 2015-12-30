package web.dao;

import web.model.Address;

import java.util.List;

public interface IAddressDAO {
    List<Address> getAll();

    void AddAddress(Address address);

    List<Address> getAddressListByClientId(int clientId);
}
