package web.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IAddressDAO;
import web.model.Address;

import java.util.List;

@Repository
public class AddressDAO extends AbstractDAO<Address> implements IAddressDAO {
    @Override
    @SuppressWarnings("unchecked")
    public List<Address> getAll() {
        return createEntityCriteria().list();
    }

    @Override
    public void saveOrUpdateAddress(Address address) {
        saveOrUpdate(address);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Address> getAddressListByClientId(int clientId) {
        return createEntityCriteria()
                .createAlias("client", "client")
                .add(Restrictions.eq("client.id", clientId))
                .list();
    }

    @Override
    public void deleteAddressById(int addressId) {
        delete(getByPK(addressId));
    }

    @Override
    public Address getAddressById(int addressId) {
        return (Address)createEntityCriteria()
                .createAlias("client","client").uniqueResult();
    }

}
