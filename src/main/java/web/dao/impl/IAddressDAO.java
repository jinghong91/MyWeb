package web.dao.impl;

import web.model.Address;

import java.util.List;

public interface IAddressDAO {
    List<Address> getAddressListByClientId(int clientId);
}
