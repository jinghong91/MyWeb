package web.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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
    public void AddAddress(Address address) {
        persist(address);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Address> getAddressListByClientId(int clientId) {
        return createEntityCriteria()
                .createAlias("client", "client")
                .add(Restrictions.eq("client.id", clientId))
                .list();
    }

}
