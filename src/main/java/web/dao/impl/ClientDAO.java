package web.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.util.type.PrimitiveWrapperHelper;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import web.dao.AbstractDAO;
import web.dao.IClientDAO;
import web.model.Client;

import java.util.List;

@Repository
public class ClientDAO extends AbstractDAO<Client> implements IClientDAO {
    @Override
    public List<Client> getAll() {
        return createEntityCriteria()
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public void addClient(Client client) {
        persist(client);
    }

    @Override
    public Client getClientById(int clientId) {
        return (Client) createEntityCriteria()
                .createAlias("addressList", "addressList", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("id", clientId))
                .uniqueResult();
    }

    @Override
    public Client getClientByName(String name) {
        return (Client) createEntityCriteria()
                .createAlias("addressList", "addressList", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

    @Override
    public void updateClient(Client client) {
        update(client);
    }
}
