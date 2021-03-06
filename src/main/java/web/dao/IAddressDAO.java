package web.dao;

import web.model.Address;

import java.util.List;

public interface IAddressDAO {
    List<Address> getAll();

    void saveOrUpdateAddress(Address address);

    List<Address> getAddressListByClientId(int clientId);

    void deleteAddressById(int addressId);

    Address getAddressById(int addressId);
}
